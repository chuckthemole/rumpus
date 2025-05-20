import React, { useEffect } from 'react';
import Modal from 'react-modal';
import { isModalActive, modal_style, setModalActive, setModalInactive } from '@rumpushub/common-react/dist/components/modal_manager';
import User, {loader as userLoader} from './user';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye } from '@fortawesome/free-solid-svg-icons';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { common_loader } from '@rumpushub/common-react/dist/components/common_requests';
import { GET_USER_PATH } from '../rumpus';

export function loader(id) {
    return common_loader(GET_USER_PATH + id);
}

export default function UserModal({user_id}) {

    const [user, setUser] = React.useState(undefined);
    const [modalIsOpen, setIsOpen] = React.useState(false);
    const [button, setButton] = React.useState(<a aria-disabled><FontAwesomeIcon icon={faEye} /></a>);

    if(user_id === undefined) {
        console.log('user_id is undefined!');
        return {button};
    }

    let loader_response = loader(user_id);

    // set user if loader_response is not undefined and there is no error and it is not loading
    useEffect(() => {
        if(loader_response !== undefined && !loader_response.error && !loader_response.isLoading) {
            setUser(loader_response.data);
        }
    }, [loader_response]);

    // set button if user is not undefined
    useEffect(() => {
        if(user !== undefined) {
            // console.log(user);
            setButton(
            <a
                onClick={openModal}
                // to={`/user/` + user.id}
                className="viewUser button is-info is-light"
                data-tooltip-id="user-view-button"
                data-tooltip-html={
                    "View user: " + user.userDetails.username
                }
                data-tooltip-place="left"
            >
                <FontAwesomeIcon icon={faEye} />
            </a>);
        }
    }, [user]);

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

    return (
        <>
            {button}
            <ReactTooltip id='user-view-button' />

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={modal_style}
                contentLabel="User Modal"
            >
                <div className="modal-content box">
                    <User id={user_id} />
                </div>
            </Modal>
        </>
    );
}