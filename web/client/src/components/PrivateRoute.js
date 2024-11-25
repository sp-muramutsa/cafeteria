import { Navigate} from "react-router-dom"
import {useContext} from "react"
import {AuthContext} from "../context/AuthContext"

const PrivateRoute = ({children}) => {
    const context = useContext(AuthContext);
    let { user } = context;
    return user ? children : <Navigate to="/login" />;

}

export default PrivateRoute;