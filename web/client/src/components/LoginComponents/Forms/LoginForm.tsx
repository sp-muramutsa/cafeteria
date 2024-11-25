import {useContext, useState} from "react";
import PasswordField from "../Fields/PasswordField";
import EmailField from "../Fields/EmailField";
import { toast } from "react-toastify";
import {AuthContext, AuthContextType } from "../../../context/AuthContext";

const LoginForm = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const context = useContext(AuthContext) as AuthContextType;
    const { loginUser } = context;

    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(email && password) {
            await loginUser(email, password);
        } else {
            toast.error("Email and Password are required");
        }
    }

    return (
        <div className="selection:bg-indigo-500 selection:text-white">
            <div className="flex justify-center items-center">
                <div className="p-8 flex-1">
                    <div className="mx-auto overflow-hidden">
                        <div className="p-8">
                            <h1 className="text-5xl font-bold text-black-600">
                                Welcome back!
                            </h1>

                            <form className="mt-12" method="POST" onSubmit={handleLogin}>
                                <EmailField email={email} setEmail={setEmail} />
                                <PasswordField password={password} setPassword={setPassword}/>

                                <input
                                    type="submit"
                                    value="Sign in"
                                    className="mt-20 px-8 py-4 uppercase rounded-full bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-center block w-full focus:outline-none focus:ring focus:ring-offset-2 focus:ring-customLightGreen-500 focus:ring-opacity-80 cursor-pointer"
                                />
                            </form>
                            <a
                                href="#"
                                className="mt-4 block text-sm text-center font-medium text-yellow-300 hover:underline focus:outline-none focus:ring-2 focus:ring-customDarkGreen-500"
                            >
                                {" "}
                                Forgot your password?{" "}
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginForm;