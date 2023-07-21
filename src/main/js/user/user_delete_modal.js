const React = require('react');

import Modal from 'react-modal';
import { useState } from 'react';
import { Common, DELETE_USER_PATH } from "../rumpus";
import { useFetcher } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';

export default function UserDelete({ user_id, user_username }) {

    const customStyles = {
        content: {
            // top: '50%',
            // left: '50%',
            // right: 'auto',
            // bottom: 'auto',
            // marginRight: '-50%',
            transform: 'translate(40%, 70%)',
        },
    };

    const [id] = useState(user_id);
    const [username] = useState(user_username);
    const [deleteUser, setDeleteUser] = useState(false);

    const fetcher = useFetcher();
    const [modalIsOpen, setIsOpen] = React.useState(false);

    function onOpenModal() {
        // TODO call user delete
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

    async function handleSubmit(e) {
        e.preventDefault();
        if(deleteUser == true) {
            const requestOptions = {
                method: Common.POST,
                // redirect: "follow",
                entity: username,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(username)
            };
            
            return await fetch(DELETE_USER_PATH, requestOptions);
        }
        console.log('Canceling delete user `' + username + ' ` with id `' + id + '`');
        closeModal();
    }

    return (
        <>
            <a onClick={onOpenModal} className="deleteUser button is-danger" type="submit" value="Delete"><FontAwesomeIcon icon={faTrashCan} /></a>

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={customStyles}
                contentLabel="User Delete"
            >

            <div className='modal-content'>
                <fetcher.Form reloadDocument onSubmit={handleSubmit} className="box">
                    <div className="field">
                        <span>Confirm delete user '{username}' with id '{id}'</span>
                        <div className='buttons'>
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