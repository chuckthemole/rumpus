const React = require('react');

import Modal from 'react-modal';
import { useState } from 'react';
import { Common, DELETE_USER_PATH } from "../rumpus";
import { useFetcher } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { isModalActive, modal_style, setModalActive, setModalInactive } from '@rumpushub/common-react/dist/components/modal_manager';
import { load_current_user } from './user_loader';
import { CreateLogItemRequest } from '@rumpushub/common-react/dist/components/common';

export default function UserDelete({ user_id, user_username }) {

    const [id] = useState(user_id);
    const [username] = useState(user_username);
    const [deleteUser, setDeleteUser] = useState(false);

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
        closeModal();
        if(deleteUser == true) {
            const requestOptions = {
                method: Common.POST,
                // redirect: "follow",
                entity: username,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(username)
            };

            let currentUser = {};
            await load_current_user().then(user => {
                console.log('user: ', user);
                currentUser = user;
            });
            
            await fetch(DELETE_USER_PATH, requestOptions).then(response => response.json()).then(data => {
                // console.log('data: ', data);
                if(data.attributes.status == 'user deleted') {
                    alert('User \'' + username + '\' deleted!');
                    fetch('/api/log_action', CreateLogItemRequest('ADMIN_LOG', 'DELETE user with username: ' + username, currentUser.id, currentUser.username));
                } else {
                    alert('Error deleting user \'' + username + '\'!');
                }
            });
        }
    }

    return (
        <>
            <a
                onClick={openModal} className="deleteUser button is-danger" type="submit" value="Delete"
                data-tooltip-id="user-delete-button"
                data-tooltip-html={
                    "Delete user: " + username
                }
                data-tooltip-place="left"
            >
                <FontAwesomeIcon icon={faTrashCan} />
            </a>
            <ReactTooltip id='user-delete-button' />

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={modal_style}
                contentLabel="User Delete"
            >

            <div className='modal-content'>
                <fetcher.Form reloadDocument onSubmit={handleSubmit} className="box">
                    <div className="field">
                        <span className='columns is-centered'>Confirm delete user '{username}' with id '{id}'</span>
                        <div className='buttons columns is-centered'>
                            <button onClick={e => setDeleteUser(true)} id="deleteUser" type="submit" value="DeleteUser" className="button is-danger">
                                Delete
                            </button>
                            <button onClick={e => setDeleteUser(false)} id="deleteUserCancel" type="submit" value="DeleteUserCancel" className="button is-success is-light">
                                Cancel
                            </button>
                        </div>
                    </div>
                </fetcher.Form>
            </div>
            </Modal>
        </>
    );
}