import { BrowserRouter, Routes, Route,Navigate } from 'react-router-dom'
import { useState } from 'react'
import Home from './pages/Home'
import Access from './pages/Access'
import ProtectedRoutes from "./routes/ProtectedRoutes"
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function App() {
  

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element = {<Access/>}/>
        <Route path="/" element={ <ProtectedRoutes> <Home/> </ProtectedRoutes>} />
        <Route path='*' element={ <Navigate to="/" replace/>} />
      </Routes>
      <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop closeOnClick pauseOnHover theme="colored" />
    </BrowserRouter>
  )
}

export default App
