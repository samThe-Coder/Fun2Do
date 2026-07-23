function TodoForm({task,handleInput,addTask,editTaskId,handleTaskUpdate,handleTaskUpdateCancel}){
    return (
        <form>
            
            <label>Title</label><input type="text" name="title" value={task.title} onChange={(e)=>handleInput(e)} placeholder="Enter Task Title"/>
            <label>Description</label><textarea name="description" value={task.description} onChange={(e)=>handleInput(e)} placeholder="Enter Task Description"/>
            <label>Priority</label><select name="priority" value={task.priority} onChange={(e)=>handleInput(e)}>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
                <option value="LOW">Low</option>
            </select>
            <label>Due Date</label><input type="date" name="dueDate" value={task.dueDate || ""} onChange={(e)=>handleInput(e)}/>
            {
                editTaskId ?  
                    <>
                    <button onClick={()=>handleTaskUpdate()}>Update</button>  
                    <button onClick={()=>handleTaskUpdateCancel()}>Cancel</button>
                    </>
                : <button disabled={!task.title.trim()} onClick={addTask}>Add</button> 
            }
            
        </form>
    )
}
export default TodoForm