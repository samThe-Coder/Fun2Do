function TertiaryButton({handleFloatingPlusButton}){
    return (
        <button
            className="btn btn-primary rounded-circle position-fixed bottom-0 end-0 m-4 shadow"
            style={{ width: "60px", height: "60px" }}
            // data-bs-toggle="modal"
            // data-bs-target="#todoModal"
            onClick={()=>handleFloatingPlusButton()}
        >
            <i className="bi bi-plus-lg"></i>
        </button>
    );
}
export default TertiaryButton;