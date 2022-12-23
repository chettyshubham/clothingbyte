import axios from "axios";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import loginvalidation from "../loginvalidation"

function CustomerLogin(){

    const dispatch=useDispatch()

    const [user,setUser]=useState({
        "email":"",
        "password":""
    })
    const [errors,setErrors]=useState({})
    const [submitted,setSubmitted]=useState(false)
    const history=useHistory()

    const handleInput=(e)=>{
        setUser({...user,[e.target.name]:e.target.value})
    }

    const handleSubmit=e=>{
        e.preventDefault()
        setErrors(loginvalidation(user))   
        setSubmitted(true) 
    }

    useEffect(()=>{
        console.log(errors)
        if(Object.keys(errors).length===0 && submitted){
            console.log(user)
            axios.post("http://localhost:8080/api/customers/signin",user)
            .then(resp=>{
                let result=resp.data.data;
                console.log(resp.data.data)
                sessionStorage.setItem("email",result.userObject.email)
                sessionStorage.setItem("uname",result.userObject.name)
                sessionStorage.setItem("role","customer")
                sessionStorage.setItem("accessToken",result.jwt)
                sessionStorage.setItem("id",result.userObject.id)  
                dispatch({type:'IsLoggedIn'})
                history.push("/home")
            })
            .catch(error=>{
                console.log("Error",error);
                alert("Invalid username or password")
            })            
        }
    },[errors])


    return (
<div className="container">
            <div className="row">
                <div className="col-sm-6 mx-auto">
                    <h4 className="text-center p-2">
                        Customer Login Form
                    </h4>
                    <form onSubmit={handleSubmit}>                 
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Email</label>
                        <div className="col-sm-8">
                            <input type="text" name="email" value={user.email} onChange={handleInput} className="form-control" />
                            {errors.email && <small className="text-danger float-right">{errors.email}</small>}
                        </div>
                        
                    </div>                    
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Password</label>
                        <div className="col-sm-8">
                            <input type="password" name="password" value={user.password} onChange={handleInput} className="form-control" />
                            {errors.password && <small className="text-danger float-right">{errors.password}</small>}
                        </div>
                    </div>                    
                    <button className="btn btn-primary float-right">Login Now</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CustomerLogin;