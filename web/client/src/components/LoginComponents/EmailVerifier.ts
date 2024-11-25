import React, {useContext, useEffect} from 'react';
import {AuthContext, AuthContextType} from "../../context/AuthContext";
import { useSearchParams } from "react-router-dom";
import { toast } from "react-toastify";

const EmailVerifier: React.FC = () => {
    const [searchParams] = useSearchParams();
    const context = useContext(AuthContext) as AuthContextType;
    const { verifyEmail } = context;

    useEffect(() => {
        const token = searchParams.get("token");
        if(!token){
            toast.error("Invalid verification link");
            return;
        }

        verifyEmail(token);
    }, [searchParams, verifyEmail]);

    return null;
};

export default EmailVerifier;