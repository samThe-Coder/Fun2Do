function TodoForm({task,handleInput,addTask,editTaskId,handleTaskUpdate,handleTaskUpdateCancel}){
    function handleSubmit(e) {
        e.preventDefault();

        if (editTaskId) {
            handleTaskUpdate();
        } else {
            addTask();
        }
    }


    return (
        <form onSubmit={handleSubmit}>

            {/* Title */}
            <div className="mb-3">
                <label className="form-label">
                    Title
                </label>

                <input
                    type="text"
                    className="form-control"
                    name="title"
                    value={task.title}
                    onChange={handleInput}
                    placeholder="Enter task title"
                    required
                />
            </div>


            {/* Description */}
            <div className="mb-3">

                <label className="form-label">
                    Description
                </label>

                <textarea
                    className="form-control"
                    name="description"
                    value={task.description}
                    onChange={handleInput}
                    rows="3"
                    placeholder="Enter description"
                />

            </div>


            {/* Priority */}
            <div className="mb-3">

                <label className="form-label">
                    Priority
                </label>

                <select
                    className="form-select"
                    name="priority"
                    value={task.priority}
                    onChange={handleInput}
                >
                    <option value="LOW">
                        Low
                    </option>

                    <option value="MEDIUM">
                        Medium
                    </option>

                    <option value="HIGH">
                        High
                    </option>

                </select>

            </div>


            {/* Due Date */}
            <div className="mb-3">

                <label className="form-label">
                    Due Date
                </label>

                <input
                    type="date"
                    className="form-control"
                    name="dueDate"
                    value={task.dueDate || ""}
                    onChange={handleInput}
                />

            </div>


            {/* Buttons */}
            <div className="d-flex justify-content-end gap-2">

                <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={handleTaskUpdateCancel}
                >
                    Cancel
                </button>


                <button
                    type="submit"
                    className="btn btn-primary"
                >
                    {editTaskId ? "Update Todo" : "Add Todo"}
                </button>

            </div>

        </form>
    );
    
    // return (
    //     <>
            
    //         <label>Title</label><input type="text" name="title" value={task.title} onChange={(e)=>handleInput(e)} placeholder="Enter Task Title"/>
    //         <label>Description</label><textarea name="description" value={task.description} onChange={(e)=>handleInput(e)} placeholder="Enter Task Description"/>
    //         <label>Priority</label><select name="priority" value={task.priority} onChange={(e)=>handleInput(e)}>
    //             <option value="MEDIUM">Medium</option>
    //             <option value="HIGH">High</option>
    //             <option value="LOW">Low</option>
    //         </select>
    //         <label>Due Date</label><input type="date" name="dueDate" value={task.dueDate || ""} onChange={(e)=>handleInput(e)}/>
    //         {
    //             editTaskId ?  
    //                 <>
    //                 <button onClick={()=>handleTaskUpdate()}>Update</button>  
    //                 <button onClick={()=>handleTaskUpdateCancel()}>Cancel</button>
    //                 </>
    //             : <button disabled={!task.title.trim()} onClick={addTask}>Add</button> 
    //         }
            
    //     </>
    // )
}
export default TodoForm