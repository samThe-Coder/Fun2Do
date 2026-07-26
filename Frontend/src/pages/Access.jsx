import { useState } from "react";
import AccessForm from "../components/AccessForm";
import { logIn,signUp } from "../api/authApi";
import { Navigate, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';

function Access(){

    const token = localStorage.getItem("token");
    if(token){
        return <Navigate to="/" replace />;
    }

    const emptyCredentials = {
        username : "",
        password : ""
    };
    const [credentials, setCredentials] = useState(emptyCredentials);

    // const [error,setError] = useState("");
    const [isLoginMode,setIsLoginMode] = useState(true);
    const navigate = useNavigate();
    function handleInput(e){
        const {name,value} = e.target;
        setCredentials((prev) => ({
            ...prev,
            [name] : value
        }));
    }

    async function handleSubmit(e){
        // setError("");
        e.preventDefault();
        try{
            let response;
            if(isLoginMode){
                response = await logIn(credentials);
            }else{
                response = await signUp(credentials);
                setCredentials(emptyCredentials);
                setIsLoginMode(true);
            }

            if(response.token){
                localStorage.setItem("token",response.token);
                toast.success(response.message,{ toastId : `${isLoginMode ? 'login' : 'signup'}-success` });
                navigate("/",{replace : true});
            }else{
                toast.warning(response.message,{ toastId : `${isLoginMode ? 'login' : 'signup'}-warning` });
            }
            
        }catch(error){
            toast.error(error.message,{ toastId : `${isLoginMode ? 'login' : 'signup'}-exception` });
        }
        
    }

    function toggleAccessMode(){
            toast.info(`Switched to ${isLoginMode? 'Sign up' : 'Log in'} Mode`,{ toastId : `${isLoginMode ? 'login' : 'signup'}-switching` });
            setIsLoginMode((prev) => !prev);
    }

    return (
        <>
            <AccessForm credentials={credentials} handleInput={handleInput} handleSubmit={handleSubmit} toggleAccessMode={toggleAccessMode} isLoginMode={isLoginMode}/>
        </>
    );
}
export default Access;