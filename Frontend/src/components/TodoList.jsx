import TodoItem from "./TodoItem";
function TodoList({taskList,handleInput, toggleTaskStatus, handleTaskDeletion, handleTaskUpdate}){
    return (
        <ul>
            {
                taskList.map((task)=> 
                    <li key={task.id}>
                        <TodoItem task={task} handleInput={handleInput} toggleTaskStatus={toggleTaskStatus} handleTaskDeletion={handleTaskDeletion} handleTaskUpdate={handleTaskUpdate}/>
                    </li>
                )
            }
        </ul>
    )
}
export default TodoList