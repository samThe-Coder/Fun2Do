import { useState,useEffect } from "react"
import {v4 as uuidv4 } from 'uuid'
import TodoForm from '../components/TodoForm'
import TodoList from '../components/TodoList'

function Home(){
    const emptyTask = {
        id: "",
        title: "",
        description: "",
        priority: "Medium",
        dueDate: "",
        isCompleted: false,
        createdAt: null,
        completedAt: ""
    };
    const STORAGE_KEY = "Fun2Do_tasks";
    const [task,setTask]=useState(emptyTask);
    const [editTaskId,setEditTaskId]=useState(null);
    //using local storage to persist data locally
    const [taskList,setTaskList]=useState(() =>{
        const savedTasks = localStorage.getItem(STORAGE_KEY);
        return savedTasks ? JSON.parse(savedTasks) : []
    });
    
    useEffect(()=>{
        localStorage.setItem(STORAGE_KEY,JSON.stringify(taskList));
    },[taskList]);


    function handleInput(e){
        const {name,value} = e.target;
        setTask(prev =>({...prev,
            [name] : value
        }));
    }
    function toggleTaskStatus(id){
        
        setTaskList((prevTasks) =>
        prevTasks.map((todo) => {
            if (todo.id !== id) {
                return todo;
            }

            const isCompleted = !todo.isCompleted;

            return {
                ...todo,
                isCompleted,
                completedAt: isCompleted ? new Date().toISOString() : null,
            };
        })
    );
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
    function addTask(){
        if(task.title.trim() !== "" ){ //allow valid strings only
            const invalidCharacters = /[`'\"<>\\$%(&)]/;

            if(!invalidCharacters.test(task.title)){ //\\W+ restricting special characters
                //Create a new object with uuid
                const newTask = {
                    ...task,
                    id: uuidv4(), //later to use this in database
                    createdAt: new Date().toISOString()
                }

                //setTask(newTask); //creates problem due to useState's asynchronous behaviour
                setTaskList(prev => [...prev,newTask]); //adding the new task to the existing list
                
                setTask(emptyTask); //resetting the input field
                
            }else{
                alert("Special characters are not allowed!");
            }
        }
    }
    function handleTaskDeletion(id){
        const newTaskList = taskList.filter(prev => prev.id !== id);
        setTaskList(newTaskList);
    }
    function handleTaskUpdate(){
        setTaskList((prevTaskList) => {
            return prevTaskList.map((todo)=>{
                if(todo.id !== task.id){
                    return todo;
                }else{
                    return task;
                }
            })
        })
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
    
    }
    function handleTaskUpdateCancel(){
        setTask(emptyTask);
        setEditTaskId(null);
    }
    function handleTaskEdit(taskObj){
        setEditTaskId(taskObj.id);
        setTask(taskObj);
    }
    return(
        <>
            <TodoForm task={task} handleInput={handleInput} addTask={addTask} editTaskId={editTaskId} handleTaskUpdate={handleTaskUpdate} handleTaskUpdateCancel={handleTaskUpdateCancel}/>
            <TodoList taskList={taskList} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} />
            
        </>
    )
}
export default Home