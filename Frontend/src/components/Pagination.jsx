function Pagination({page,setPage,totalPages}){
    return (
        <nav>
                <ul className="pagination m-4 justify-content">

                    <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                        <button
                            className="page-link"
                            onClick={() => setPage(page - 1)}
                        >
                            Previous
                        </button>
                    </li>

                    {[...Array(totalPages)]
                    .map((_, index) => index)
                    .filter(index => index >= page - 2 && index <= page + 2)
                    .map(index => (
                        <li
                            key={index}
                            className={`page-item ${page === index ? "active" : ""}`}
                        >
                            <button
                                className="page-link"
                                onClick={() => {
                                    // console.log("Clicked page",index);
                                    setPage(index)
                                    }
                                }
                            >
                                {index + 1}
                            </button>
                        </li>
                    ))}

                    <li className={`page-item ${page === totalPages - 1 ? "disabled" : ""}`}>
                        <button
                            className="page-link"
                            onClick={() => setPage(page + 1)}
                        >
                            Next
                        </button>
                    </li>

                </ul>
            </nav>
    );
}
export default Pagination;