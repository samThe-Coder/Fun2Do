import TodoItem from "./TodoItem";
function TodoList({taskList, toggleTaskStatus, handleTaskEdit, handleTaskDeletion}){
    
    const getPriorityBadge = (priority) => {
        switch (priority) {
            case "HIGH":
                return "bg-danger";
            case "MEDIUM":
                return "bg-warning text-dark";
            case "LOW":
                return "bg-success";
            default:
                return "bg-secondary";
        }
    };

    return (
        <div className="container mt-4">
            <ul className="list-group shadow-sm">

                {taskList.map((todo) => (
                    <li
                        key={todo.id}
                        className="list-group-item d-flex justify-content-between align-items-center"
                    >

                        {/* Left Section */}
                        <div className="d-flex align-items-start">

                            <input
                                className="form-check-input mt-2 me-3 border border-secondary"
                                type="checkbox"
                                checked={todo.isCompleted}
                                onChange={() => toggleTaskStatus(todo.id)}
                            />

                            <div>

                                <h5
                                    className={`mb-1 ${
                                        todo.isCompleted
                                            ? "text-decoration-line-through text-muted"
                                            : ""
                                    }`}
                                >
                                    {todo.title}
                                </h5>

                                <p className="mb-2 text-muted">
                                    {todo.description || "No description"}
                                </p>

                                <span
                                    className={`badge me-2 ${getPriorityBadge(
                                        todo.priority
                                    )}`}
                                >
                                    {todo.priority}
                                </span>

                                <span
                                    className={`badge me-2 ${
                                        todo.isCompleted
                                            ? "bg-success"
                                            : "bg-secondary"
                                    }`}
                                >
                                    {todo.isCompleted
                                        ? "Completed"
                                        : "Pending"}
                                </span>

                                {todo.dueDate && (
                                    <span className="badge bg-info text-dark">
                                        <i className="bi bi-calendar-event me-1"></i>
                                        {new Date(todo.dueDate).toLocaleDateString()}
                                    </span>
                                )}

                            </div>
                        </div>

                        {/* Right Section */}
                        <div>

                            <button
                                className="btn btn-outline-primary btn-sm me-2"
                                onClick={() => handleTaskEdit(todo)}
                            >
                                <i className="bi bi-pencil"></i>
                            </button>

                            <button
                                className="btn btn-outline-danger btn-sm"
                                onClick={() => handleTaskDeletion(todo.id)}
                            >
                                <i className="bi bi-trash"></i>
                            </button>

                        </div>

                    </li>
                ))}
            </ul>
        </div>
    );
}



        {/* <ul>
            {
                taskList.map((task)=> 
                    <li key={task.id}>
                        <TodoItem task={task} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} />
                    </li>
                )
            }
        </ul> */}

export default TodoList