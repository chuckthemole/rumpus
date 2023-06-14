const React = require('react');

import { useState } from 'react';
import { Common, UPDATE_USER_PATH } from "../rumpus";

function UpdateUser({ user }) {

    // TODO see if we can work this with one object
    // const [user, setUser] = useState({
    //     originalUserName: userToUpdate.username,
    //     username: userToUpdate.username,
    //     email: userToUpdate.email,
    //     password: userToUpdate.password
    // });

    console.log(user);

    const [username, setUsername] = useState(user.username);
    const [email, setEmail] = useState(user.email);
    const [password, setPassword] = useState(user.password);
    const [id] = useState(user.id);

    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedUser = {};
        updatedUser[Common.USERNAME] = username;
        updatedUser[Common.PASSWORD] = password;
        updatedUser[Common.EMAIL] = email;
        updatedUser[Common.ID] = id;

        const requestOptions = {
            method: Common.POST,
            redirect: "follow",
            entity: updatedUser,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedUser)
        };
        return fetch(UPDATE_USER_PATH, requestOptions).then(window.location.reload());
    }

    return (
        <form onSubmit={handleSubmit} className="box">
            <div className="field">
                <label htmlFor="" className="label">Username</label>
                <div className="control has-icons-left">
                    <input type="username" placeholder="e.g. coolguy" className="input" value={username} onChange={e => setUsername(e.target.value)} required />
                        <span className="icon is-small is-left">
                            <i className="fa fa-envelope"></i>
                        </span>
                </div>
            </div>
            <div className="field">
                <label htmlFor="" className="label">Email</label>
                <div className="control has-icons-left">
                    <input type="email" placeholder="e.g. bobsmith@gmail.com" className="input" value={email} onChange={e => setEmail(e.target.value)} required />
                        <span className="icon is-small is-left">
                            <i className="fa fa-envelope"></i>
                        </span>
                </div>
            </div>
            <div className="field">
                <label htmlFor="" className="label">Password</label>
                <div className="control has-icons-left">
                    <input type="password" placeholder="*******" className="input" value={password} onChange={e => setPassword(e.target.value)} required />
                    <span className="icon is-small is-left">
                        <i className="fa fa-lock"></i>
                    </span>
                </div>
            </div>
            <div className="field">
                <button id="signupSubmit" type="submit" value="UpdateUser" className="button is-success">
                    Submit
                </button>
            </div>
        </form>
    );
}
export default UpdateUser;