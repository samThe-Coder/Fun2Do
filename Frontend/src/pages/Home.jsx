    import { useState,useEffect } from "react"
    import TodoForm from '../components/TodoForm'
    import TodoList from '../components/TodoList'
    import { getTodos,createTodo,updateTodo,deleteTodo } from "../api/todoApi"

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
        const [error, setError] = useState("");

        async function fetchTodos() {
            try{
                const page = await getTodos();
                setTaskList(page.content);
            }catch(error){
                setError(error);
            }finally{
                setLoading(false);
            }
        }

        useEffect(()=>{
            fetchTodos();
        },[]);

        function handleInput(e){
            const {name,value} = e.target;
            setTask(prev =>({...prev,
                [name] : value
            }));
        }

        async function toggleTaskStatus(id){
            setError("");
            const todo = taskList.find(todo => todo.id === id);
            if(!todo){
                return;
            }
            const update = {
                isCompleted : !todo.isCompleted
            }
            try{
                const updatedTask = await updateTodo(id,update);
                setTaskList((prevTaskList)=> prevTaskList.map(task => task.id === id ? updatedTask : task));
            }catch(error){
                setError(error);
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
            setError("");
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
                        setTaskList(prev => [savedTask,...prev]);
                        setTask(emptyTask); //resetting the input field
                        
                    }else{
                        setError("Special characters are not allowed!");
                    }
                }
            }catch(error){
                setError(error);
            }
        }
        async function handleTaskDeletion(id){
            setError("");
            try{
                await deleteTodo(id);
                setTaskList((prevTaskList) => 
                    prevTaskList.filter((todo) => 
                        todo.id !== id
                    )
                );
            }catch(error){
                setError(error);
            }
            // const newTaskList = taskList.filter(prev => prev.id !== id);
            // setTaskList(newTaskList);
        }
        async function handleTaskUpdate(){ //updated task. Replace the old one completely
            setError("");
            try{
                const updatedTask = await updateTodo(task.id,task)
                setTaskList((prevTaskList) => 
                    prevTaskList.map((todo) => 
                        todo.id !== task.id ? todo : updatedTask
                    )   
                );
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
                setTask(emptyTask);
                setEditTaskId(null);
                // console.log(task);
            }catch(error){
                setError(error);
            }
        
        }
        function handleTaskUpdateCancel(){
            setTask(emptyTask);
            setEditTaskId(null);
        }
        function handleTaskEdit(taskObj){
            console.log(taskObj);
            setEditTaskId(taskObj.id);
            // setTask(taskObj);// may cause accidental object reference issue
            setTask({...taskObj});
        }
        return(
            <>
                <TodoForm task={task} handleInput={handleInput} addTask={addTask} editTaskId={editTaskId} handleTaskUpdate={handleTaskUpdate} handleTaskUpdateCancel={handleTaskUpdateCancel}/>
                <TodoList taskList={taskList} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} />
                
            </>
        )
    }
    export default Home