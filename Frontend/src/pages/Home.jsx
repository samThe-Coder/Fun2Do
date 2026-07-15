import { useState } from "react"
import {v4 as uuidv4 } from 'uuid'

function Home(){
    const [task,setTask]=useState({id:"",title:"",isCompleted:false});
    const [taskList,setTaskList]=useState([]);
    function handleInput(e){
        setTask({...task,title : e.target.value});
    }
    function addTask(){
        if(task.title.trim() !== "" ){ //allow valid strings only
            if(!task.title.match("[`'\"<>\\$%(&)]")){ //\\W+ restricting special characters
                //Create a new object with uuid
                const newTask = {
                    id: uuidv4(), //later to use this in database
                    title:task.title,
                    isCompleted:task.isCompleted
                }
                //setTask(newTask); //creates problem due to useState's asynchronous behaviour
                setTaskList(prev => [...prev,newTask]); //adding the new task to the existing list
                setTask({id:"",title:"",isCompleted:false}); //resetting the input field
            }else{
                alert("Special characters are not allowed!");
            }
        }
    }
    return(
        <>
            <input type="text" value={task.title} onChange={(e)=>handleInput(e)}/>
            <button onClick={addTask}>Add</button>

            <ul>
                {
                    taskList.map((element)=> 
                        <li key={element.id}>
                            
                            <h2>
                                Task : {element.title}
                            </h2>
                            <h3>
                                Status : {element.isCompleted ? 'Completed' : 'Pending'}
                            </h3>
                        </li>
                    )}
            </ul>
        </>
    )
}
export default Home