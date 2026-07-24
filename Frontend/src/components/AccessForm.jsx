import todo from '../assets/brand/todo.svg'
function AccessForm({credentials, handleInput, handleSubmit, toggleAccessMode, isLoginMode}){
    return (
        // <div className="align-items-center py-4 bg-body-tertiary">
        <div className="container-fluid min-vh-100 d-flex justify-content-center align-items-center bg-body-tertiary">
            <main className=" form-signin w-100" style={{maxWidth: "400px"}}>
                <form onSubmit={handleSubmit}>

                    <img className="mb-4" src={todo} alt="" width="72" height="57" />
                    <h1 className="h3 mb-3 fw-normal">Please {isLoginMode ? "Log in" : "Sign up"}</h1>
                    <div className="form-floating">
                        <input type="text" name="username" value={credentials.username} onChange={handleInput} className="form-control" id="floatingInput" placeholder="name@example.com" required/>
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                
                
                    <div className="form-floating"> 
                        <input type="password" name="password" value={credentials.password} onChange={handleInput} className="form-control" id="floatingPassword" placeholder="Password" required/>
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
                    <div className="form-check form-switch text-start my-3" data-bs-toggle="tooltip" data-bs-placement="top"  data-bs-custom-class="custom-tooltip" data-bs-title="This top tooltip is themed via CSS variables.">
                        <input className="form-check-input" role="switch" checked={isLoginMode} onChange={toggleAccessMode} type="checkbox" value={isLoginMode ?  "sign-up?" : "log-in?"} id="checkDefault" />
                        <label className="form-check-label" htmlFor="checkDefault"> {isLoginMode ?  "Sign up?" : "Log in?"} </label>
                    </div>

                    <button className="btn btn-primary w-100 py-2" type="submit"> {isLoginMode ? "Log in" : "Sign up"} </button>
                    <p className="mt-5 mb-3 text-body-secondary">&copy; {new Date().getFullYear()}</p>

                </form>
            </main>
         </div>
        // </div>
    );

}
export default AccessForm;