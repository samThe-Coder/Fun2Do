function AccessForm({credentials, handleInput, handleSubmit, toggleAccessMode, isLoginMode}){
    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="username" value={credentials.username} onChange={handleInput} placeholder="Enter Username" required/>
            <input type="password" name="password" value={credentials.password} onChange={handleInput} placeholder="Enter Password" required/>
            Login <input type="checkbox" checked={isLoginMode} onChange={toggleAccessMode}/> Signup
            <button type="submit">{isLoginMode ? "Login" : "Signup"}</button>
        </form>
    );

}
export default AccessForm;