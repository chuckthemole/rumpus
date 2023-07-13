import React, { useState } from 'react';
import Modal from 'react-modal';
import { Form, useFetcher } from 'react-router-dom';
import { CREATE_USER_PATH, Common } from './rumpus';

export default function SignupModal() {

    const customStyles = {
        content: {
            // top: '50%',
            // left: '50%',
            // right: 'auto',
            // bottom: 'auto',
            // marginRight: '-50%',
            transform: 'translate(0%, 70%)',
        },
    };

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const fetcher = useFetcher();
    const [modalIsOpen, setIsOpen] = React.useState(false);

    function openModal() {
        setIsOpen(true);
    }

    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        // subtitle.style.color = '#f00';
    }

    function closeModal() {
        setIsOpen(false);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        const newUser = {};
        newUser[Common.USERNAME] = username;
        newUser[Common.PASSWORD] = password;
        newUser[Common.EMAIL] = email;
        const fetched = await onCreate(newUser);
        clearInput();
    }

    async function onCreate(newUser) {
        const requestOptions = {
            method: Common.POST,
            redirect: "follow",
            entity: newUser,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        };
        // return fetch('/api/user', requestOptions)
        return fetch(CREATE_USER_PATH, requestOptions)
            .then(response => {
                // response.json()
                console.log(response);
                console.log(response.json());
            })
            .then(data => {
                console.log(data);
            })
            .then(() => { // reload window
                window.location.reload();
            });
	}

    function clearInput() {
        setEmail(Common.EMPTY);
        setUsername(Common.EMPTY);
        setPassword(Common.EMPTY);
    }

    return (
        <>
            <a onClick={openModal} className="signupBtn button is-light">Sign up</a>

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={customStyles}
                contentLabel="Example Modal"
            >
                <div className="modal-content">
                    <fetcher.Form reloadDocument method='post' onSubmit={handleSubmit} className="box">
                        <div className="field">
                            <label htmlFor="" className="label">Username</label>
                            <div className="control has-icons-left">
                                <input name='username' type="username" placeholder="e.g. coolguy" className="input" value={username} onChange={e => setUsername(e.target.value)} required />
                                <span className="icon is-small is-left">
                                    <i className="fa fa-envelope"></i>
                                </span>
                            </div>
                        </div>
                        <div className="field">
                            <label htmlFor="" className="label">Email</label>
                            <div className="control has-icons-left">
                                <input name='email' type="email" placeholder="e.g. bobsmith@gmail.com" className="input" value={email} onChange={e => setEmail(e.target.value)} required />
                                <span className="icon is-small is-left">
                                    <i className="fa fa-envelope"></i>
                                </span>
                            </div>
                        </div>
                        <div className="field">
                            <label htmlFor="" className="label">Password</label>
                            <div className="control has-icons-left">
                                <input name='password' type="password" placeholder="*******" className="input" value={password} onChange={e => setPassword(e.target.value)} required />
                                <span className="icon is-small is-left">
                                    <i className="fa fa-lock"></i>
                                </span>
                            </div>
                        </div>
                        {/* <!-- <div className="field">
                            <label htmlFor="" className="label">Confirm Password</label>
                            <div className="control has-icons-left">
                                <input type="confirm_password" placeholder="*******" className="input" required>
                                <span className="icon is-small is-left">
                                    <i className="fa fa-lock"></i>
                                </span>
                            </div>
                        </div> --> */}
                        <div className="field">
                            <button id="signupSubmit" type="submit" value="Signup" className="button is-success">
                                Submit
                            </button>
                        </div>
                    </fetcher.Form>
                </div>
            </Modal>
        </>
    );
}