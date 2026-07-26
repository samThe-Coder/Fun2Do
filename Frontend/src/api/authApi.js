import API from "./api";

export async function logIn(credentials){
    const response = await fetch(`${API}/auth/login`,{
        method : "POST"
        ,headers :{
            "Content-Type": "application/json"
            // Authorization : `Bearer ${token}`
        }
        ,body : JSON.stringify(credentials)
    });

    if(!response.ok){
        const error = await response.json();
        //to handle multiple errors
        const message = typeof error.message === "string" ? error.message : Object.values(error.message).join(", ");
        throw new Error(`${message}`); //Login failed! 
    }

    const data = await response.json();

    // if(data.token){
    //     localStorage.setItem("token",data.token);
    // }
    
    return data;
}

export async function signUp(userData){
    const response = await fetch(`${API}/auth/signup`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    });

    if (!response.ok) {
        const error = await response.json();
        //to handle multiple errors
        const message = typeof error.message === "string" ? error.message : Object.values(error.message).join(", ");
        throw new Error(`${message}`); //Signup failed!   
    }
    const data = await response.json();
    // if(data.token){
    //     localStorage.setItem("token",data.token);
    // }
    return data;
}

export function logout(){
    localStorage.removeItem("token");
    // toast.success("Sucessfully Logged out");
    // window.location.replace("/login");
    // So that users can't click the back button to visit the protected urls
}