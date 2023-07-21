const React = require('react');

import Modal from 'react-modal';
import { useState } from 'react';
import { Common, UPDATE_USER_PATH } from "../rumpus";
import { useFetcher } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit } from '@fortawesome/free-solid-svg-icons';
import { loader_by_id } from './user_loader';

export async function loader({ params }) {
    return fetch(`/api/user/${params.userId}`);
}

function UpdateUser({ user_id }) {

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
    const [id] = useState(user_id);

    const fetcher = useFetcher();
    const [modalIsOpen, setIsOpen] = React.useState(false);

    function onOpenModal() {
        // load user and open modal
        loader_by_id({id}).then((user) => {
            setUsername(user.username);
            setEmail(user.email);
            setPassword(user.password);
        });
        openModal();
    }

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
        fetch(UPDATE_USER_PATH, requestOptions);
    }

    return (
        <>
            <a onClick={onOpenModal} className="updateUser button is-danger is-light" type="submit" value="Update"><FontAwesomeIcon icon={faEdit} /></a>

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={customStyles}
                contentLabel="Example Modal"
            >

            <div className='modal-content'>
                <fetcher.Form reloadDocument onSubmit={handleSubmit} className="box">
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
                </fetcher.Form>
            </div>
            </Modal>
        </>
    );
}
export default UpdateUser;