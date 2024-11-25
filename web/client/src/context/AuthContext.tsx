import React, { createContext, useState, useEffect, ReactNode } from 'react';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import axios from 'axios';

// Tokens interface
interface AuthTokens {
    accessToken: string;
    refreshToken: string;
}

// AuthResponse interface
interface AuthResponse {
    tokens: AuthTokens;
    status: number;
}

// User interface
interface User {
    email: string;
    mcneeseId: string;
    firstname: string;
    lastname: string;
    dorm: string;
}

// AuthContext interface
export interface AuthContextType {
    authTokens: AuthTokens | null;
    user: User | null;
    verifiedEmail: string;
    loginUser: (email: string, password: string) => Promise<void>;
    registerUser: (email: string, mcneeseId: string, firstname: string, lastname: string, dorm: string, password: string) => Promise<void>;
    verifyEmail: (token: string) => void;
    logoutUser: () => void;
    setVerifiedEmail: React.Dispatch<React.SetStateAction<string>>;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// base URL for API
const baseUrl = "http://localhost:8080/api/v1/auth";

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const navigate = useNavigate();

    const [authTokens, setAuthTokens] = useState<AuthTokens | null>(() =>
        localStorage.getItem('authTokens')
            ? JSON.parse(localStorage.getItem('authTokens')!)
            : null
    );
    const [loading, setLoading] = useState<boolean>(false);
    const [user, setUser] = useState<User | null>(() =>
        authTokens && authTokens.accessToken ? jwtDecode(authTokens.accessToken) : null
    );
    const [verifiedEmail, setVerifiedEmail] = useState<string>('');

    // Initial loading state
    useEffect(() => {
        if (!authTokens || !authTokens.accessToken) {
            setUser(null);
            localStorage.removeItem('authTokens');
            setLoading(false);
        } else {
            try {
                const decoded = jwtDecode<JwtPayload & User>(authTokens.accessToken);
                setUser(decoded);
                setLoading(false);
            } catch (error) {
                setUser(null);
                setLoading(false);
                localStorage.removeItem('authTokens');
                toast.error("Invalid token detected, please log in again.");
            }
        }
    }, [authTokens]);

    const handleAuthentication = (tokens: AuthTokens) => {
        if (tokens && tokens.accessToken) {
            setAuthTokens(tokens);
            setUser(jwtDecode(tokens.accessToken));
            localStorage.setItem('authTokens', JSON.stringify(tokens));
        }
    };

    const loginUser = async (email: string, password: string) => {
        try {
            const response = await axios.post<AuthResponse>(`${baseUrl}/authenticate`, { email, password });
            if (response.status === 200) {
                handleAuthentication(response.data.tokens);
                toast.success("Logged in successfully");
                navigate('/');
            } else {
                toast.error("Invalid username and/or password");
            }
        } catch (error) {
            console.log("Server error: ", error);
            toast.error("An unexpected error has occurred");
        }
    };

    const registerUser = async (
        email: string,
        mcneeseId: string,
        firstname: string,
        lastname: string,
        dorm: string,
        password: string
    ) => {
        try {
            const response = await axios.post<AuthResponse>(`${baseUrl}/register`, {
                email, mcneeseId, firstname, lastname, dorm, password
            });
            if (response.status === 200) {
                handleAuthentication(response.data.tokens);
                toast.success("Registered successfully");
                navigate('/register-confirmation');

            } else {
                toast.error("Invalid registration credentials");
            }
        } catch (error) {
            console.log("Server error: ", error);
            toast.error("An unexpected error has occurred");
        }
    };

    const verifyEmail = async (emailVerificationToken: string) => {
        try {
            const { status } = await axios.get(`${baseUrl}/verify`, {
                params: { token: emailVerificationToken },
            });
            if (status === 200) {
                setVerifiedEmail(emailVerificationToken);
                navigate("/");
            } else {
                navigate("/login");
                console.error("Error during email verification");
            }
        } catch (error) {
            console.log("Server error: ", error);
            toast.error("An unexpected error occurred while verifying email");
            navigate("/login");
        }
    };

    const logoutUser = () => {
        setAuthTokens(null);
        setUser(null);
        localStorage.removeItem('authTokens');
        toast.success("Logged out successfully");
        navigate("/");
    };

    const contextData = {
        authTokens,
        user,
        loginUser,
        registerUser,
        logoutUser,
        verifyEmail,
        verifiedEmail,
        setVerifiedEmail
    };

    return (
        <AuthContext.Provider value={contextData}>
            {!loading && children}
        </AuthContext.Provider>
    );
};
