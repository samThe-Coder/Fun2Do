import { useEffect, useRef } from "react";
import { Modal } from "bootstrap";
import TodoForm from "../TodoForm";
function TodoModal({showModal, setShowModal, task,handleInput,addTask,editTaskId,handleTaskUpdate,handleTaskUpdateCancel}){

    const modalRef = useRef(null);
    const modalInstance = useRef(null);


    useEffect(() => {
        modalInstance.current = new Modal(modalRef.current);

        return () => {
            modalInstance.current.dispose();
        };
    }, []);


    useEffect(() => {
        if (!modalInstance.current) return;

        if (showModal) {
            modalInstance.current.show();
        } else {
            modalInstance.current.hide();
        }
    }, [showModal]);


    function closeModal() {
        setShowModal(false);
        handleTaskUpdateCancel();
    }


    return (
        <div
            className="modal fade"
            ref={modalRef}
            tabIndex="-1"
            aria-labelledby="todoModalLabel"
            aria-hidden="true"
        >

            <div className="modal-dialog">

                <div className="modal-content">


                    <div className="modal-header">

                        <h5 
                            className="modal-title"
                            id="todoModalLabel"
                        >
                            {editTaskId ? "Edit Todo" : "Add Todo"}
                        </h5>


                        <button
                            type="button"
                            className="btn-close"
                            onClick={closeModal}
                        />

                    </div>


                    <div className="modal-body">

                        <TodoForm
                            task={task}
                            handleInput={handleInput}
                            addTask={addTask}
                            editTaskId={editTaskId}
                            handleTaskUpdate={handleTaskUpdate}
                            handleTaskUpdateCancel={closeModal}
                        />

                    </div>

                </div>

            </div>

        </div>
    );

}

export default TodoModal;