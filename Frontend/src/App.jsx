import { BrowserRouter, Routes, Route,Navigate } from 'react-router-dom'
import { useState } from 'react'
import Home from './pages/Home'
import Access from './pages/Access'
import ProtectedRoutes from "./routes/ProtectedRoutes"
function App() {
  

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element = {<Access/>}/>
        <Route path="/" element={ <ProtectedRoutes> <Home/> </ProtectedRoutes>} />
        <Route path='*' element={ <Navigate to="/" replace/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
