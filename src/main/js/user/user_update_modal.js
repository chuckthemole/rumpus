const React = require('react');

import Modal from 'react-modal';
import { useState } from 'react';
import { Common, UPDATE_USER_PATH } from "../rumpus";
import { useFetcher } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit } from '@fortawesome/free-solid-svg-icons';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { isModalActive, modal_style, setModalActive, setModalInactive } from '@rumpushub/common-react/dist/components/modal_manager';
import { CreateLogItemRequest } from '@rumpushub/common-react/dist/components/common';
import { load_current_user } from './user_loader';

export async function loader({ params }) {
    return fetch(`/api/user/${params.userId}`);
}

function UpdateUser({ userDetails, user_email, metaData, user_id }) {

    const [username, setUsername] = useState(userDetails.username);
    const [email, setEmail] = useState(user_email);
    const [password, setPassword] = useState(userDetails.password);
    const [id] = useState(user_id);

    const fetcher = useFetcher();
    const [modalIsOpen, setIsOpen] = React.useState(false);

    function openModal() {
        if(!isModalActive()) {
            setIsOpen(true);
            setModalActive();
        }
    }

    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        // subtitle.style.color = '#f00';
    }

    function closeModal() {
        setIsOpen(false);
        setModalInactive();
    }

    async function handleSubmit(e) {
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
        closeModal();

        let currentUser = {};
        await load_current_user().then(user => {
            currentUser = user;
        });

        await fetch(UPDATE_USER_PATH, requestOptions).then(response => response.json()).then(data => {
            if(data.attributes.status == 'user updated') {
                alert('User \'' + username + '\' updated!');
                fetch('/api/log_action', CreateLogItemRequest('ADMIN_LOG', 'UPDATE user with username: [old: \'' + userDetails.username + '\', new: \'' + username + '\']', currentUser.id, currentUser.username));
            } else {
                alert('Error updating user \'' + username + '\'!');
            }
        });
    }

    return (
        <>
            <a
                onClick={openModal} className="updateUser button is-danger is-light" type="submit" value="Update"
                data-tooltip-id="user-update-button"
                data-tooltip-html={
                    "Edit user: " + username
                }
                data-tooltip-place="left"
            >
                <FontAwesomeIcon icon={faEdit} />
            </a>
            <ReactTooltip id='user-update-button' />

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={modal_style}
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