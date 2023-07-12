const React = require('react');

import { useState } from 'react';
import { Common, UPDATE_USER_PATH } from "../rumpus";

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {};
        user[Common.USERNAME] = username;
        user[Common.PASSWORD] = password;
        onLogin(user);
        const loginModal = document.getElementsByClassName('login')[0];
        loginModal.classList.remove('is-active');
        loginModal.classList.remove('is-clipped');
        clearInput();
    }

    function onLogin(user) {
        const requestOptions = {
            method: 'POST',
            redirect: "follow",
            // entity: user,
            // headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        };
        fetch('/login', requestOptions)
            // .then(response => response.json());
            // .then(data => this.setState({ postId: data.id }));
	}

    function clearInput() {
        this.setState({username: ''});
        this.setState({password: ''});
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
                <label htmlFor="" className="label">Password</label>
                <div className="control has-icons-left">
                    <input type="password" placeholder="*******" className="input" value={password} onChange={e => setPassword(e.target.value)} required />
                    <span className="icon is-small is-left">
                        <i className="fa fa-lock"></i>
                    </span>
                </div>
            </div>
            <div className="field">
                <button id="loginSubmit" type="submit" value="Login" className="button is-success">
                    Submit
                </button>
            </div>
        </form>
    );
}