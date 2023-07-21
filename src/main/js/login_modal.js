import React, { useState } from 'react';
import Modal from 'react-modal';
import { Common } from './rumpus';

export default function LoginModal() {

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
    const [password, setPassword] = useState('');

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

    function clearInput() {
        setUsername(Common.EMPTY);
        setPassword(Common.EMPTY);
    }

    return (
        <>
            <a onClick={openModal} className="loginBtn button is-light">Login</a>

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={customStyles}
                contentLabel="Example Modal"
            >
                <div className="modal-content">
                    <form action="/login" method="post" className='box'>
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
                            <label htmlFor="" className="label">Password</label>
                            <div className="control has-icons-left">
                                <input name='password' type="password" placeholder="*******" className="input" value={password} onChange={e => setPassword(e.target.value)} required />
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
                </div>
            </Modal>
        </>
    );
}