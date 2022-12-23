import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"

function CustomerProfile(){
    const [uname,setUname]=useState(sessionStorage.getItem("uname"))
    const id=sessionStorage.getItem("id")
    const token = sessionStorage.getItem("accessToken")
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    const [user,setUser]=useState({
        "id":sessionStorage.getItem("id"),
        "name":"",
        "city":"",
        "email":"",
        "pwd":"",
        "phone":"",
        "gender":""
    })

    

    useEffect(()=>{
        axios.get("http://localhost:8080/api/customers/"+id)
        .then(resp=>{
            console.log(resp.data.data)
            setUser(resp.data.data)
        })
    },[])
 
    const handleInput=(e)=>{
        setUser({...user,[e.target.name]:e.target.value})
    }

    const handleSubmit=(e)=>{
        e.preventDefault() 
        axios.put("http://localhost:8080/api/customers/"+id,user)
        .then(resp=>{
            alert("Profile updated successfully")
            setUname(user.name)
        })     
    }

    return (
        <div className="container">
            <h4 className="p-2">Welcome {uname}</h4>            
            <form onSubmit={handleSubmit}>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Customer Name</label>
                        <div className="col-sm-8">
                            <input type="text" name="name" value={user.name} onChange={handleInput} className="form-control" />                        
                        </div>
                        
                    </div>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">City</label>
                        <div className="col-sm-8">
                            <input type="text" name="city" value={user.city} onChange={handleInput} className="form-control" />                        
                        </div>                        
                    </div>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Gender</label>
                        <div className="col-sm-8">
                            <select disabled required name="gender" value={user.gender} onChange={handleInput} className="form-control">
                                <option value="">Select Gender</option>
                                <option>Male</option>     
                                <option>Female</option>     
                            </select>                       
                        </div>                        
                    </div>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Email</label>
                        <div className="col-sm-8">
                            <input type="text" readOnly name="email" value={user.email} onChange={handleInput} className="form-control" />                        
                        </div>
                        
                    </div>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Phone</label>
                        <div className="col-sm-8">
                            <input type="text" maxLength="10" name="phone" value={user.phone} onChange={handleInput} className="form-control" />                        
                        </div>
                        
                    </div>
                    <div className="form-group form-row">
                        <label className="col-sm-4 form-control-label">Password</label>
                        <div className="col-sm-8">
                            <input type="password" name="pwd"  onChange={handleInput} className="form-control" />                        
                        </div>
                    </div>                    
                    <button className="btn btn-primary float-right">Update Profile</button>
                    </form>                           
        </div>
    )
}

export default CustomerProfile;
