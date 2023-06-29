const React = require('react');
const ReactDOM = require('react-dom/client');

import useSWR from 'swr';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit, faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons'
import { Common, CREATE_USER_PATH, DELETE_USER_PATH, GET_USERS_PATH, GET_USER_PATH, TEMPLATE_GET_USER_PATH } from "../rumpus";
import UpdateUser from './update';
import { ConvertEpochToDate } from '../../../../../common/src/main/js/common';

// const fetcher = (...args) => fetch(...args).then((res) => res.json());

const fetcher = async url => {
    const res = await fetch(url)

    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!res.ok) {
        const error = new Error('An error occurred while fetching the data.')
        // Attach extra info to the error object.
        error.info = await res.json()
        error.status = res.status
        throw error
    }

    return res.json()
}

function Users() {
    
    const { data, error } = useSWR(
        GET_USERS_PATH,
        fetcher
    );

    console.log(data);
    console.log(error);

    if (error) return <div className='container m-6'><div className='notification is-primary'><p>An error occurred</p></div></div>;

    if (!data) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )
    // <div className='container m-6'><div className='notification is-primary'><p>Loading...</p></div></div>;

    if (data.error == 'Forbidden') return <div className='container m-6'><div className='notification is-primary'><p>User is not authorized to view users</p></div></div>;

    // data is good, store into variable
    const users = data;


    // const { currentUser, currentUserError } = useSWR(
    //     '/api/current_username',
    //     fetcher
    // );
    // console.log(currentUser);

    const handleViewUserSubmit = (id) => (e) => {
        e.preventDefault();
        console.log('View Form submitted');
        return fetch(TEMPLATE_GET_USER_PATH + id);
    }
    
    const handleDeleteUserSubmit = (username) => (e) => {
        e.preventDefault();
        console.log('Delete Form submitted');

        const requestOptions = {
            method: Common.POST,
            redirect: "follow",
            entity: username,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(username)
        };
        return fetch(DELETE_USER_PATH, requestOptions).then(window.location.reload());
            // .then(response => {
            //     // response.json()
            //     console.log(response);
            //     console.log(response.json());
            // })
            // .then(data => {
            //     // this.setState({ postId: data.id })
            //     console.log(data);
            //     // const loginFetched = this.onLogin(userName);
            //     // console.log(loginFetched);
            // })
            // .then(() => { // reload window
            //     window.location.reload();
            // });
    }

    const handleUpdateUser = (id) => (e) => {
        e.preventDefault();

        const requestOptions = {
            method: Common.GET,
            headers: {
                'Accept': 'application/json',
            },
        };
        const user = fetch(GET_USER_PATH + id, requestOptions)
            .then(response => response.json())
            .then(response => {
                console.log(response);
                const update_user = document.getElementById('update_user-form');
                if (typeof(update_user) != 'undefined' && update_user != null) {
                    const reactDOMUpdateUser = ReactDOM.createRoot(update_user); // getting warnings. change to root.render()
                    reactDOMUpdateUser.render(<UpdateUser user={response}/>);
                } else {
                    console.log("Error with update_user");
                }

                const update = document.getElementsByClassName('update_user')[0];
                update !== undefined ? Common.ActivateModalNoButton(update) : console.log("Error: could not find element with class 'update'");
            });
            
        // console.log(user);
        // console.log(user.email);
        // console.log(user.username);
        // console.log(user.password);

        // const update = document.getElementsByClassName('update_user')[0];
        // update !== undefined ? Common.ActivateModalNoButton(update) : console.log("Error: could not find element with class 'update'");
    }

    const handleAddUser = (e) => {
        e.preventDefault();
        const signup = document.getElementsByClassName("signup")[0];
        signup !== undefined ? Common.ActivateModalNoButton(signup) : console.log("Error: could not find element with class 'signup'");
    }

    return (
        <div className='content m-6'>

                <table className="table is-hoverable is-fullwidth is-bordered m-6">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th><abbr title="User Name">User</abbr></th>
                            <th>Email</th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Authorizations">Birth</abbr></th>
                            <th>ID</th>
                            <th>View</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>#</th>
                            <th><abbr title="User Name">User</abbr></th>
                            <th>Email</th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Creation Date/Time">Birth</abbr></th>
                            <th>ID</th>
                            <th>View</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        {users.map(({ userDetails, email, metaData, id, index }) => (
                            <tr key={userDetails.username}>
                                <th>{index}</th>
                                <td>{userDetails.username}</td>
                                <td>{email}</td>
                                <td>{userDetails.password}</td>
                                <td title={ConvertEpochToDate(metaData.creationTime).toString()}>{ConvertEpochToDate(metaData.creationTime).toDateString()}</td>
                                <td>{id}</td>
                                <td>
                                    <form onSubmit={handleViewUserSubmit(id)}>
                                        <button className="viewUser button is-info is-light" type="submit" value="View"><FontAwesomeIcon icon={faEye} /></button>
                                    </form>
                                </td>
                                <td>
                                    <form onSubmit={handleDeleteUserSubmit(userDetails.username)}>
                                        <button className="deleteUser button is-danger" type="submit" value="Delete"><FontAwesomeIcon icon={faTrashCan} /></button>
                                    </form>
                                </td>
                                <td>
                                    <form onSubmit={handleUpdateUser(id)}>
                                        <button className="updateUser button is-danger is-light" type="submit" value="Update"><FontAwesomeIcon icon={faEdit} /></button>
                                    </form>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className='container m-4'>
                    <form onSubmit={handleAddUser}>
                        <button className="m-4 addUser button is-primary" type="submit" value="Add"><FontAwesomeIcon icon={faPlus} />&nbsp;&nbsp;Add new user</button>
                    </form>
                </div>

        </div>
    )
}

export default Users;