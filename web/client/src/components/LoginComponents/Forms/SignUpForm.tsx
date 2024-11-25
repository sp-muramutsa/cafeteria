import {useContext, useState} from "react";
import PasswordField from "../Fields/PasswordField";
import EmailField from "../Fields/EmailField";
import NameField from "../Fields/NameField";
import McneeseIdField from "../Fields/McneeseIdField";
import DormField from "../Fields/DormField";
import {AuthContext, AuthContextType} from "../../../context/AuthContext";
import { toast } from "react-toastify";

const SignupForm = () => {
    const [email, setEmail] = useState("");
    const [mcneeseId, setMcneeseId] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [dorm, setDorm] = useState("");
    const [password, setPassword] = useState("");
    const context = useContext(AuthContext) as AuthContextType;
    const { registerUser } = context;

    const handleRegister = async ( e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(email && mcneeseId && firstName && lastName && dorm && password) {
            await registerUser(email, mcneeseId, firstName, lastName, dorm, password);
        } else {
            toast.error("Please enter all credentials");
        }
    }

    return (
        <div className="selection:bg-indigo-500 selection:text-white">
            <div className="flex justify-center items-center">
                <div className="p-8 flex-1">
                    <div className="mx-auto overflow-hidden">
                        <div className="p-8">
                            <h1 className="text-5xl font-bold text-indigo-600">
                                Create account
                            </h1>

                            <form className="mt-12" method="POST" onSubmit={handleRegister}>
                                <EmailField email={email} setEmail={setEmail} />
                                <McneeseIdField mcneeseId={mcneeseId} setMcneeseId={setMcneeseId} />
                                <NameField nameType={'firstName'} name={firstName} setName={setFirstName}/>
                                <NameField nameType={'lastName'} name={lastName} setName={setLastName}/>
                                <DormField dorm={dorm} setDorm={setDorm} />
                                <PasswordField password={password} setPassword={setPassword} />

                                <input
                                    type="submit"
                                    value="Sign up"
                                    className="mt-20 px-8 py-4 uppercase rounded-full bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-center block w-full focus:outline-none focus:ring focus:ring-offset-2 focus:ring-indigo-500 focus:ring-opacity-80 cursor-pointer"
                                />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignupForm;