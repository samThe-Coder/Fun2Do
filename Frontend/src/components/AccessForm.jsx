import todo from '../assets/brand/todo.svg'
function AccessForm({credentials, handleInput, handleSubmit, toggleAccessMode, isLoginMode}){
    return (
        // <div className="align-items-center py-4 bg-body-tertiary">
        <div className="container-fluid min-vh-100 d-flex justify-content-center align-items-center bg-body-tertiary">
            <main className=" form-signin w-100" style={{maxWidth: "400px"}}>
                <form onSubmit={handleSubmit}>

                    <img className="mb-4" src={todo} alt="" width="72" height="57" />
                    {/* <h1 className="h3 mb-3 fw-normal">Please {isLoginMode ? "Log in" : "Sign up"}</h1> */}
                    <h2 className="text-center mb-3">Please {isLoginMode ? "Log In" : "Sign Up"} </h2>
                    <div className="form-floating mb-2">
                        <input type="text" name="username" value={credentials.username} onChange={handleInput} className="form-control" id="floatingInput" placeholder="name@example.com" required/>
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                
                
                    <div className="form-floating mb-4"> 
                        <input type="password" name="password" value={credentials.password} onChange={handleInput} className="form-control" id="floatingPassword" placeholder="Password" required/>
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
                    {/* <div className="form-check form-switch text-start my-3" >
                        <input className="form-check-input" role="switch" checked={isLoginMode} onChange={toggleAccessMode} type="checkbox" value={isLoginMode ?  "sign-up?" : "log-in?"} id="checkDefault" />
                        <label className="form-check-label" htmlFor="checkDefault"> {isLoginMode ?  "Sign up?" : "Log in?"} </label>
                    </div> */}
                    

                    {/* <button className="btn btn-primary w-100 py-2" type="submit"> {isLoginMode ? "Log in" : "Sign up"} </button> */}

                    <div className='mb-5'>
                        <button type="submit" className="btn btn-primary w-100 mb-3">
                            {isLoginMode ? "Log In" : "Sign Up"}
                        </button>

                        <p className="text-center mb-0">
                        {isLoginMode ? "Don't have an account?" : "Already have an account?"}{" "}
                        <button
                            type="button"
                            className="btn btn-link p-0"
                            onClick={toggleAccessMode}
                        >
                            {isLoginMode ? "Sign Up" : "Log In"}
                        </button>
                        </p>
                    </div>
                    <p className="mt-5 mb-3 text-body-secondary">&copy; 2025 - {new Date().getFullYear()} Sampad Das</p>

                </form>
            </main>
         </div>
        // </div>
    );

}
export default AccessForm;