import { useState } from "react"
import {v4 as uuidv4 } from 'uuid'

function Home(){
    const [task,setTask]=useState({
        id: "",
        title: "",
        description: "",
        priority: "Medium",
        isCompleted: false,
        createdAt: null,
        completedAt: null
    });
    const [taskList,setTaskList]=useState([]);
    function handleInput(e){
        setTask({...task,title : e.target.value});
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
                    id: uuidv4(), //later to use this in database
                    createdAt: new Date().toISOString()
                }

                //setTask(newTask); //creates problem due to useState's asynchronous behaviour
                setTaskList(prev => [...prev,newTask]); //adding the new task to the existing list
                
                setTask({
                    id: "",
                    title: "",
                    description: "",
                    priority: "Medium",
                    isCompleted: false,
                    createdAt: null,
                    completedAt: null
                }); //resetting the input field
                
            }else{
                alert("Special characters are not allowed!");
            }
        }
    }
    function handleTaskDeletion(id){
        const newTaskList = taskList.filter(prev => prev.id !== id);
        setTaskList(newTaskList);
    }
    return(
        <>
            <input type="text" value={task.title} onChange={(e)=>handleInput(e)}/>
            <button onClick={addTask}>Add</button>

            <ul>
                {
                    taskList.map((element)=> 
                        <li key={element.id}>
                            <div>
                                <h2>
                                    Task : {element.title}
                                </h2>
                                <h3>

                                    Status : <input type="checkbox"  onChange={(e)=>toggleTaskStatus(e,element.id)}/>{element.isCompleted ? 'Completed' : 'Pending'}
                                </h3>
                                <button onClick={()=>handleTaskDeletion(element.id)}>Delete</button>
                            </div>
                        </li>
                    )}
            </ul>
        </>
    )
}
export default Home