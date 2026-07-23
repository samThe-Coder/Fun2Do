import { useState } from "react";
import AccessForm from "../components/AccessForm";
import { logIn,signUp } from "../api/authApi";

function Access(){

    const emptyCredentials = {
        "username" : "",
        "password" : ""
    };
    const [credentials, setCredentials] = useState(emptyCredentials);

    const [error,setError] = useState("");
    const [isLoginMode,setIsLoginMode] = useState(true);

    function handleInput(e){
        const {name,value} = e.target;
        setCredentials((prev) => ({
            ...prev,
            [name] : value
        }));
    }

    async function handleSubmit(e){
        setError("");
        e.preventDefault();
        if(isLoginMode){
            try{
                const response = await logIn(credentials);

            }catch(error){
                setError(error.message);
            }
            
        }else{
            try{
                const response = await signUp(credentials);
                setCredentials(emptyCredentials);
                setIsLoginMode(true);
            }catch(error){
                setError(error.message);
            }
        }

    }

    function toggleAccessMode(){
            setIsLoginMode((prev) => !prev);
    }

    return (
        <>
            <AccessForm credentials={credentials} handleInput={handleInput} handleSubmit={handleSubmit} toggleAccessMode={toggleAccessMode} isLoginMode={isLoginMode}/>
        </>
    );
}
export default Access;