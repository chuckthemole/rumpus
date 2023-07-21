const React = require('react');
const ReactDOM = require('react-dom/client');

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Common, DELETE_USER_PATH, GET_USERS_PATH, GET_USER_PATH } from "../rumpus";
import UpdateUser from './user_update_modal';
import UserDelete from './user_delete_modal';
import { ConvertEpochToDate } from '../../../../../common/src/main/js/common';
import { useLoaderData, Link, useFetcher } from 'react-router-dom';
import SignupModal from '../signup_modal';

export async function loader() {
    const response = await fetch(GET_USERS_PATH);
    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!response.ok) {
        const error = new Error('An error occurred while fetching users');
        // Attach extra info to the error object.
        error.info = await response.json();
        error.status = response.status;
        throw error;
    }
    if(response.redirected == true) { // catching this and returning null as to not get console error
        return null;
    }

    return response.json();
}

export async function delete_user(username) {
    const requestOptions = {
        method: Common.POST,
        // redirect: "follow",
        entity: username,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(username)
    };
    
    return await fetch(DELETE_USER_PATH, requestOptions);
}

export default function Users() {

    const [users, setUsers] = React.useState(useLoaderData());
    const fetcher = useFetcher();
    
    React.useEffect(() => { // TODO: this calls the api a lot. figure out a resolution.
        loader().then((response) => {
            setUsers(response);
        });
    }, [users]);

    if (!users) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )
    
    const handleDeleteUserSubmit = (username) => (e) => {
        e.preventDefault();
        console.log('Delete Form submitted');

        const requestOptions = {
            method: Common.POST,
            // redirect: "follow",
            entity: username,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(username)
        };
        
        return fetch(DELETE_USER_PATH, requestOptions);
        // let history = useHistory();
        // history.push("/admin");
            // .then(window.location.reload());
            // .then(response => {
            //     // response.json()
            //     console.log(response);
            //     console.log(response.json());
            // })
            // .then(users => {
            //     // this.setState({ postId: users.id })
            //     console.log(users);
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
                                    <Link to={`/user/` + id} className="viewUser button is-info is-light"><FontAwesomeIcon icon={faEye} /></Link>
                                </td>
                                <td>
                                    <UserDelete user_username={userDetails.username} user_id={id}/>
                                </td>
                                <td>
                                    <UpdateUser user_id={id}/>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className='container m-4'>
                    {/* <form onSubmit={handleAddUser}>
                        <button className="m-4 addUser button is-primary" type="submit" value="Add"><FontAwesomeIcon icon={faPlus} />&nbsp;&nbsp;Add new user</button>
                    </form> */}
                    <SignupModal btn={<span><FontAwesomeIcon icon={faPlus} />&nbsp;&nbsp;Add new user</span>}/>
                </div>

        </div>
    )
}