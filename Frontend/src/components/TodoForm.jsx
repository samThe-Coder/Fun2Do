function TodoForm({task,handleInput,addTask}){
    return (
        <>
            <label>Title</label><input type="text" name="title" value={task.title} onChange={(e)=>handleInput(e)} placeholder="Enter Task Title"/>
            <label>Description</label><textarea name="description" value={task.description} onChange={(e)=>handleInput(e)} placeholder="Enter Task Description"/>
            <label>Priority</label><select name="priority" value={task.priority} onChange={(e)=>handleInput(e)}>
                <option value="medium">Medium</option>
                <option value="high">High</option>
                <option value="low">Low</option>
            </select>
            <label>Due Date</label><input type="date" name="dueDate" value={task.dueDate || ""} onChange={(e)=>handleInput(e)}/>
            <button onClick={addTask}>Add</button>
        </>
    )
}
export default TodoForm