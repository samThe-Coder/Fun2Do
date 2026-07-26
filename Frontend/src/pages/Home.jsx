import { useState,useEffect } from "react"
import TodoModal from '../components/modal/TodoModal'
import TodoList from '../components/TodoList'
import { getTodos,createTodo,updateTodo,deleteTodo } from "../api/todoApi"
import { useNavigate } from "react-router-dom"
import { logout } from "../api/authApi"
import { toast } from "react-toastify"
import Navbar from "../components/Navbar"
import TertiaryButton from "../components/button/TertiaryButton"
import Pagination from "../components/Pagination"
import TodoFilter from "../components/TodoFilter"


function Home(){
    const emptyTask = {
        id: "",
        title: "",
        description: "",
        priority: "MEDIUM",
        dueDate: "",
        isCompleted: false,
        completedAt: ""
    };
    // const STORAGE_KEY = "Fun2Do_tasks";
    const [task,setTask]=useState(emptyTask);
    const [editTaskId,setEditTaskId]=useState(null);
    //using local storage to persist data locally
    // const [taskList,setTaskList]=useState(() =>{
    //     const savedTasks = localStorage.getItem(STORAGE_KEY);
    //     return savedTasks ? JSON.parse(savedTasks) : []
    // });
    const [taskList,setTaskList]=useState([]);
    // useEffect(()=>{
    //     localStorage.setItem(STORAGE_KEY,JSON.stringify(taskList));
    // },[taskList]);
    const [loading, setLoading] = useState(true);
    // const [error, setError] = useState("");

    const [showModal,setShowModal] = useState(false);

    const[page,setPage] = useState(0);
    const[size,setSize] = useState(5);
    const[totalPages,setTotalPages] = useState(0);

    const[status,setStatus] = useState(false);
    const[search,setSearch] = useState("");
    const[debouncedSearch, setDebouncedSearch] = useState("");
    const[priority,setPriority] = useState("");
    const[sortBy,setSortBy] = useState("createdAt,desc");

    const navigate = useNavigate();
    async function fetchTodos(pageNo = 0) {
        try{
            const response = await getTodos(
                {    
                    page : pageNo
                    ,size : size
                    ,sort : sortBy
                    ,priority : priority
                    ,status : status
                    ,search : debouncedSearch
                });
            setTaskList(response.content);
            // setPage(response.number);
            setTotalPages(response.totalPages);
            setSize(response.size);
        }catch(error){
            // setError(error);
            toast.error(error.message,{ toastId : "todo-fetch-exception"});
        }finally{
            setLoading(false);
        }
    }

    useEffect(()=>{
        const timer = setTimeout(()=>{
            setDebouncedSearch(search);
        },500); // wait 500 ms after user stops typing
        return ()=> clearTimeout(timer);
    },[search]);

    useEffect(() =>{
        setPage(0);
    },[priority, status, debouncedSearch]);

    useEffect(()=>{
        // console.log("Current page:", page);
        fetchTodos(page);
    },[page, priority, status, debouncedSearch]);

    useEffect(()=>{
        //get the unsaved/drafted todo back to the modal
        const draft = localStorage.getItem("todoDraft");
        if(draft){
            setTask(JSON.parse(draft));
            setShowModal(true);
        }
    },[]);

    useEffect(()=>{
        //draft the unsaved todo
        if(task.title){
            localStorage.setItem("todoDraft",JSON.stringify(task));
        }else{
            localStorage.removeItem("todoDraft");
        }
    },[task])

    function handleInput(e){
        const {name,value} = e.target;
        setTask(prev =>({...prev,
            [name] : value
        }));
    }

    async function toggleTaskStatus(id){
        // setError("");
        const todo = taskList.find(todo => todo.id === id);
        if(!todo){
            toast.error("Todo not found!",{ toastId : "todo-not-found"});
            return;
        }
        const update = {
            isCompleted : !todo.isCompleted
        }
        try{
            const updatedTask = await updateTodo(id,update);
            toast.success(updatedTask.message || "Todo Updated",{ toastId : "todo-update"});
            setTaskList((prevTaskList)=> prevTaskList.map(task => task.id === id ? updatedTask : task));
        }catch(error){
            // setError(error);
            toast.error(error.message ,{ toastId : "todo-update-exception"});
        }
    //     setTaskList((prevTasks) =>
    //         prevTasks.map((todo) => {
    //         if (todo.id !== id) {
    //             return todo;
    //         }

    //     const isCompleted = !todo.isCompleted;

    //         return {
    //             ...todo,
    //             isCompleted,
    //             completedAt: isCompleted ? new Date().toISOString() : null,
    //         };
    //     })
    // );       
        // const newTaskList = taskList.map((element) => {
        //     if(element.id === id){
        //         const isCompleted = !element.isCompleted;
        //         return {
        //             ...element,
        //             isCompleted : isCompleted,
        //             completedAt : isCompleted? new Date().toISOString() : null
        //         }
        //     }else
        //         return element;
        // } );
        
        // setTaskList(newTaskList);
    }
    async function addTask(){
        // setError("");
        try{
            if(task.title.trim() !== "" ){ //allow valid strings only
                const invalidCharacters = /[`'\"<>\\$%(&)]/;

                if(!invalidCharacters.test(task.title)){ //\\W+ restricting special characters
                    // //Create a new object with uuid
                    // const newTask = {
                    //     ...task,
                    //     id: uuidv4(), //later to use this in database
                    //     createdAt: new Date().toISOString()
                    // }

                    // //setTask(newTask); //creates problem due to useState's asynchronous behaviour
                    // setTaskList(prev => [...prev,newTask]); //adding the new task to the existing list
                    const savedTask = await createTodo(task);
                    // setTaskList(prev => [savedTask,...prev]);
                    setTask(emptyTask); //resetting the input field
                    toast.success(savedTask.message || "Todo Created",{ toastId : "todo-create"});
                    await fetchTodos(page);
                    
                }else{
                    // setError("Special characters are not allowed!");
                    toast.warning("Special Characters are not allowed!",{ toastId : "todo-create-warning"});
                }
            }
        }catch(error){
            // setError(error);
            toast.error(error.message ,{ toastId : "todo-create-exception"});
        }finally{
            setEditTaskId(null);
            setShowModal(false);
        }
    }
    async function handleTaskDeletion(id){
        // setError("");
        try{
            await deleteTodo(id);
            // setTaskList((prevTaskList) => 
            //     prevTaskList.filter((todo) => 
            //         todo.id !== id
            //     )
            // );
            toast.info("Todo Deleted",{ toastId : "todo-delete"});
            // setEditTaskId(null);
            await fetchTodos(page);
            
        }catch(error){
            // setError(error);
            toast.error(error.message,{ toastId : "todo-delete-exception"});
        }
        // const newTaskList = taskList.filter(prev => prev.id !== id);
        // setTaskList(newTaskList);
        finally{
            setEditTaskId(null);
            setShowModal(false);
        }
    }
    async function handleTaskUpdate(){ //updated task. Replace the old one completely
        // setError("");
        try{
            const updatedTask = await updateTodo(task.id,task)
            // setTaskList((prevTaskList) => 
            //     prevTaskList.map((todo) => 
            //         todo.id !== task.id ? todo : updatedTask
            //     )   
            // );
            toast.success(updatedTask.message || "Todo Updated",{ toastId : "todo-update"});
            await fetchTodos(page);
            // setTaskList((prevTaskList) => {
            //     return prevTaskList.map((todo)=>{
            //         if(todo.id !== task.id){
            //             return todo;
            //         }else{
            //             return task;
            //         }
            //     })
            // })
            // const updatedTask = taskList.map((element) => {
            //     if(element.id !== id){
            //         return element;
            //     }else{
            //         return task; //currently input value
            //     }
            // })
            // setTaskList(updatedTask);
            // console.log(task);
        }catch(error){
            // setError(error);
            toast.error(error.message ,{ toastId : "todo-update-exception"});
        }finally{
            setEditTaskId(null);
            setShowModal(false);
        }
    }
    function handleTaskUpdateCancel(){
        console.log("cancel clicked");
        setTask(emptyTask);
        setEditTaskId(null);
        // setShowModal(false);
    }
    function handleTaskEdit(taskObj){
        // console.log(taskObj);
        setEditTaskId(taskObj.id);
        // setTask(taskObj);// may cause accidental object reference issue
        setTask({...taskObj});
        setShowModal(true);
    }

    function handleLogOut(){
        toast.success("Sucessfully Logged out");
        logout();
        navigate("/login", {replace : true});
        // So that users can't click the back button to visit the protected urls
        setEditTaskId(null);
        setShowModal(false);
    }
    function handleFloatingPlusButton(){
        setTask(emptyTask);
        setEditTaskId(null);
        setShowModal(true);
    }
    return(
        <>
            <Navbar/>
            <TodoFilter priority={priority} setPriority = {setPriority} status = {status} setStatus = {setStatus} search = {search} setSearch = {setSearch}/>
            <TodoList taskList={taskList} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} page={page} setPage = {setPage} totalPages = {totalPages}/>
            <TodoModal showModal={showModal} setShowModal={setShowModal} task={task} handleInput={handleInput} addTask={addTask} editTaskId={editTaskId} handleTaskUpdate={handleTaskUpdate} handleTaskUpdateCancel={handleTaskUpdateCancel}/>
            <TertiaryButton handleFloatingPlusButton={handleFloatingPlusButton}/>
        </>
    )
}
export default Home