import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import HomePage from './pages/HomePage';
import MainLayout from './layouts/MainLayout/MainLayout';
import EmailVerifier from './components/LoginComponents/EmailVerifier';
import RegisterConfirmationPage from "./pages/RegisterConfirmationPage";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProtectedPage from "./pages/ProtectedPage";
import PrivateRoute from "./components/PrivateRoute";

function App() {
    return (
        <Router>
            <ToastContainer />
            <AuthProvider>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register-confirmation" element={<RegisterConfirmationPage />} />
                    <Route path="/verify" element={<EmailVerifier />} />
                    <Route element={<MainLayout />}>
                        <Route path="/" element={<HomePage />}/>
                    </Route>
                    <Route element={<PrivateRoute><MainLayout /></PrivateRoute>}>
                        <Route path="/test-protected-page" element={<ProtectedPage />} />
                    </Route>
                </Routes>
            </AuthProvider>
        </Router>
    );
}

export default App;
