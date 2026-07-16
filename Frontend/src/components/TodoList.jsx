import TodoItem from "./TodoItem";
function TodoList({taskList, toggleTaskStatus, handleTaskEdit, handleTaskDeletion}){
    return (
        <ul>
            {
                taskList.map((task)=> 
                    <li key={task.id}>
                        <TodoItem task={task} toggleTaskStatus={toggleTaskStatus} handleTaskEdit={handleTaskEdit} handleTaskDeletion={handleTaskDeletion} />
                    </li>
                )
            }
        </ul>
    )
}
export default TodoList