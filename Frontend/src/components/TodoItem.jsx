function TodoItem({task, toggleTaskStatus,handleTaskEdit, handleTaskDeletion}){
    //  if(!task){
    //     return <p>No task data</p>;

    // }
    return (
    <>
        
            <div>
                <h1>
                    Task : {task.title} 
                </h1>
                <h2>
                    Description : {task.description}
                </h2>
                <h3>

                    Status : <input type="checkbox" checked={task.isCompleted} onChange={(e)=>toggleTaskStatus(task.id)}/>
                    {task.isCompleted ? 'Completed' : 'Pending'}
                </h3>
                <h4>Priority : {task.priority}                </h4>
                <h5>Due Date : {task.dueDate} </h5>
                <h5>Completed At : {task.completedAt ? new Date(task.completedAt).toISOString().split("T")[0] : ""} </h5> 
                <button onClick={()=>{handleTaskEdit(task)}}>Edit</button>
                <button onClick={()=>handleTaskDeletion(task.id)}>Delete</button>
            </div>
    </>
    )
}
export default TodoItem