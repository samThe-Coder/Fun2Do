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
        throw new Error(error.message || "Login failed!");
    }

    const data = await response.json();

    if(data.token){
        localStorage.setItem("token",data.token);
    }
    
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
        throw new Error("Signup failed!");
    }

    return await response.json();
}