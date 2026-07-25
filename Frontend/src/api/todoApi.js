import { toast } from "react-toastify";
import API from "./api";
import { logout } from "./authApi";

function getAuthHeaders(){
    const token = localStorage.getItem("token");
    const headers = {
        "Content-Type" : "application/json",
    }
    if(token){
        headers.Authorization = `Bearer ${token}`;
    }
    return headers;
}

function handleUnauthorized(response){
    //Handles JWT token expiration logic
    if (response.status === 401 || response.status === 403) {
        toast.error("Session Expired!",{toastId : "session-expired"})
        logout();
        window.location.replace("/login");
        // So that users can't click the back button to visit the protected urls
        throw new Error("Unauthorized!");
    }
}
export async function getTodos({page=0,size=5,sort="createdAt,desc",priority,status,search}={}){
    const params = new URLSearchParams({
        page,
        size,
        sort
    });
    if(priority){
        params.append("priority",priority);
    }
    if(status){
        params.append("isCompleted",status);
    }
    if(search){
        params.append("keyword",search);
    }
    console.log(params.toString());
    const response = await fetch(
        `${API}/todos?${params.toString()}` // ${page}&size=${size}&sort=${sort}
        ,{
            headers :getAuthHeaders()
        }
    );

    handleUnauthorized(response);

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to fetch todos!")
    }

    return await response.json();
}

export async function getTodo(id) {
    const response = await fetch(
        `${API}/todos/${id}`
        ,{
            headers : getAuthHeaders()
        }
    );

    handleUnauthorized(response);

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to fetch todo!")
    }

    return await response.json();
}

export async function createTodo(todo){
    const response = await fetch(
        `${API}/todos`
        ,{
            method : "POST"
            ,headers : getAuthHeaders()
            ,body : JSON.stringify(todo)
        }
    );

    handleUnauthorized(response);

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to create todo!")
    }

    return await response.json();
}

export async function updateTodo(id,todo){
    const response = await fetch(
        `${API}/todos/${id}`
        ,{
            method : "PATCH"
            ,headers : getAuthHeaders()
            ,body : JSON.stringify(todo)
        }
    );

    handleUnauthorized(response);

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to update todo");
        
    }
    return await response.json();
}

export async function deleteTodo(id){
    const response = await fetch(
        `${API}/todos/${id}`
        ,{
            method : "DELETE"
            ,headers : getAuthHeaders()
        }
    );

    handleUnauthorized(response);

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to delete todo!")
    }

    return true;
}