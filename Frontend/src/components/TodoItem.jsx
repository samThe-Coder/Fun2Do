function TodoItem({task, handleInput, toggleTaskStatus, handleTaskDeletion, handleTaskUpdate}){
     if(!task){
        return <p>No task data</p>;
    }
    return (
    <>
        <div>
            <h1>
                Task : <input type="text" name="title" value={task.title} onChange={(e)=>handleInput(e)}/>
            </h1>
            <h2>
                Description : <textarea name="description" value={task.description} onChange={(e)=>handleInput(e)}/>
            </h2>
            <h3>

                Status : <input type="checkbox" checked={task.isCompleted} onChange={(e)=>toggleTaskStatus(e,task.id)}/>{task.isCompleted ? 'Completed' : 'Pending'}
            </h3>
            <h4>Priority : 
                <select name="priority" value={task.priority} onChange={(e)=>handleInput(e)}>
                    <option value="medium">Medium</option>
                    <option value="high">High</option>
                    <option value="low">Low</option>
                </select>
            </h4>
            <h5>Due Date : <input type="date" name="dueDate" value={task.dueDate} onChange={(e)=>handleInput(e)}/></h5>
            <h5>Completed At : <input type="date" name="completedAt" value={task.completedAt} onChange={(e)=>handleInput(e)}/></h5>
            <button onClick={()=>handleTaskUpdate(task.id)}>Edit</button>
            <button onClick={()=>handleTaskDeletion(task.id)}>Delete</button>
        </div>
    </>
    )
}
export default TodoItem