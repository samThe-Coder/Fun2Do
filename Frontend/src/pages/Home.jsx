import { useState } from "react"

function Home(){
    const [task,setTask]=useState({title:""});
    const [taskList,setTaskList]=useState([]);
    function handleInput(e){
        setTask({...task,title : e.target.value});
    }
    function addTask(){
        setTaskList(prev => [...prev,task]);
        setTask({title:""});
    }
    return(
        <>
            <input type="text" value={task.title} onChange={(e)=>handleInput(e)}/>
            <button onClick={()=>addTask()}>Add</button>

            <ul>
                {
                    taskList.map((element,index)=> 
                        <li key={index}>
                            <h2>
                                {element.title}
                            </h2>
                            <h3>
                                {element.isCompleted}
                            </h3>
                        </li>
                    )}
            </ul>
        </>
    )
}
export default Home