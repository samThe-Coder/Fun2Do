import API from "./api";

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
export async function getTodos(page=0,size=10,sort="createdAt,desc"){
    
    const response = await fetch(
        `${API}/todos?page=${page}&size=${size}&sort=${sort}`
        ,{
            headers :getAuthHeaders()
        }
    );
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

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.message || "Failed to delete todo!")
    }

    return true;
}