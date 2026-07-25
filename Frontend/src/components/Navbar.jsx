import todo from '../assets/brand/todo.svg'
import { logout } from '../api/authApi';
function Navbar(){
    return (
        <nav className="navbar bg-body-tertiary">
            <div className="container-fluid">
                <a className="navbar-brand">
                    <img src={todo} className="mb-1" src={todo} alt="" width="72" height="47" alt="" />
                </a>
                <form className="d-flex" role="search" onSubmit={logout}>
                    {/* <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search"/> */}
                    <button className="btn btn-danger" type="submit">Log Out</button>
                </form>
            </div>
        </nav>
    )
}

export default Navbar;