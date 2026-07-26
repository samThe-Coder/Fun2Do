function TodoFilter({priority, setPriority, status, setStatus, search, setSearch}) {
    return (
        <div className="container mt-3">

            <div className="row">

                <div className="col-md-4">
                    <input
                        className="form-control"
                        placeholder="Search todos..."
                        value={search}
                        onChange={(e)=>setSearch(e.target.value)}
                    />
                </div>


                <div className="col-md-4">
                    <select
                        className="form-select"
                        value={priority}
                        onChange={(e)=>setPriority(e.target.value)}
                    >
                        <option value="">All Priority</option>
                        <option value="HIGH">High</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="LOW">Low</option>
                    </select>
                </div>


                <div className="col-md-4">
                    <select
                        className="form-select"
                        value={status === null ? "" : String(status) }
                        onChange={(e)=> {
                            const value = e.target.value;
                            console.log(e.target.value);
                            setStatus( value === "" ? null : value === "true");
                            
                        }
                    }
                    >
                        <option value="">All Status</option>
                        <option value="true">Completed</option>
                        <option value="false">Pending</option>
                    </select>
                </div>

            </div>

        </div>
    );
}
export default TodoFilter;