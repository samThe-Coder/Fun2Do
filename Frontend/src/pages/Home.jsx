import { useState } from "react"
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
    const [task,setTask]=useState(emptyTask);
    const [editTaskId,setEditTaskId]=useState(null);
    const [taskList,setTaskList]=useState([]);
    

    function handleInput(e){
        const {name,value} = e.target;
        setTask(prev =>({...prev,
            [name] : value
        }));
    }
    function toggleTaskStatus(e,id){

        const newTaskList = taskList.map((element) => {
            if(element.id === id){
                return {
                    ...element,
                    isCompleted : e.target.checked,
                    completedAt : e.target.checked ? new Date().toISOString() : null
                }
            }else
                return element;
        } );
        
        setTaskList(newTaskList);
    }
    function addTask(){
        if(task.title.trim() !== "" ){ //allow valid strings only
            const invalidCharacters = /[`'\"<>\\$%(&)]/;

            if(!invalidCharacters.test(task.title)){ //\\W+ restricting special characters
                //Create a new object with uuid
                const newTask = {
                    ...task,
                    id: task.id ? task.id : uuidv4(), //later to use this in database
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
    function handleTaskUpdate(taskObj){
        // const updatedTask = taskList.map((element) => {
        //     if(element.id === id){
        //         return {
        //             element
        //         }
        //     }
        // }
        // )
        console.log(taskObj)
        setTaskList(prev=> [...prev,taskObj]);
        setTask(emptyTask);
    
    }
    function handleTaskEdit(taskObj){
        setEditTaskId(taskObj.id);
        setTask(taskObj);
    }
    return(
        <>
            <TodoForm task={task} handleInput={handleInput} addTask={addTask} editTaskId={editTaskId} handleTaskUpdate={handleTaskUpdate}/>
            <TodoList taskList={taskList} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} />
            
        </>
    )
}
export default Home