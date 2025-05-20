const React = require('react');
const ReactDOM = require('react-dom/client');

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Common, DELETE_USER_PATH, GET_USERS_PATH, CREATE_USER_PATH } from "../rumpus";
import UpdateUser from './user_update_modal';
import UserDelete from './user_delete_modal';
import { ConvertEpochToDate } from '@rumpushub/common-react/dist/components/common';
import { useLoaderData, Link, useFetcher } from 'react-router-dom';
import SignupModal from '@rumpushub/common-react/dist/components/signup_modal';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { Dropdown, get_selected } from '@rumpushub/common-react';
import UserModal from './user_modal';

// sort flags
const sort_by_username = '/username';
const sort_by_email = '/email';
const sort_by_id = '/id';
// keeps track of the current sort
let current_sort;

export async function loader() {
    // default to sort by username if not defined
    const response = current_sort !== undefined ? await fetch(GET_USERS_PATH + current_sort) : await fetch(GET_USERS_PATH + sort_by_username);
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
    const [user_index, setUserIndex] = React.useState(0);
    const fetcher = useFetcher();
    
    React.useEffect(() => { // TODO: this calls the api a lot. figure out a resolution.
        loader().then((response) => {
            setUsers(response);
        });
    }, [users]); // TODO: can i use effect when button pressed?

    if (!users) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )

    const search_filter = [
        {title: 'username', link: 'www.cool.com'},
        {title: 'email', link: 'www.manohman.com'}, 
        {title: 'id', link: 'www.link.com'},
        {title: 'search all fields', link: 'www.all.com'}
    ]

    return (
        <>
            <div className='m-6'>
                <Dropdown className='columns' title={'Search filter'} dropdown_items={search_filter} />
                <div className='columns'><input className="column is-one-third input" type="text" placeholder={get_selected()}></input></div>
            </div>
            <div className='table-container'>
                <table className="table is-hoverable is-fullwidth is-bordered m-6">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_username;}}
                                    data-tooltip-id="sort-by-username"
                                    data-tooltip-html={
                                        "Sort by username"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">User</abbr>
                                </a>
                                <ReactTooltip id='sort-by-username' />
                            </th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_email;}}
                                    data-tooltip-id="sort-by-email"
                                    data-tooltip-html={
                                        "Sort by email"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">Email</abbr>
                                </a>
                                <ReactTooltip id='sort-by-email' />
                            </th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Authorizations">Birth</abbr></th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_id;}}
                                    data-tooltip-id="sort-by-id"
                                    data-tooltip-html={
                                        "Sort by id"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">ID</abbr>
                                </a>
                                <ReactTooltip id='sort-by-id' />

                            </th>
                            <th>View</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>#</th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_username;}}
                                    data-tooltip-id="sort-by-username"
                                    data-tooltip-html={
                                        "Sort by username"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">User</abbr>
                                </a>
                                <ReactTooltip id='sort-by-username' />
                            </th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_email;}}
                                    data-tooltip-id="sort-by-email"
                                    data-tooltip-html={
                                        "Sort by email"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">Email</abbr>
                                </a>
                                <ReactTooltip id='sort-by-email' />
                            </th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Creation Date/Time">Birth</abbr></th>
                            <th>
                                <a
                                    onClick={() => {current_sort = sort_by_id;}}
                                    data-tooltip-id="sort-by-id"
                                    data-tooltip-html={
                                        "Sort by id"
                                    }
                                    data-tooltip-place="bottom"
                                >
                                    <abbr title="User Name">ID</abbr>
                                </a>
                                <ReactTooltip id='sort-by-id' />

                            </th>
                            <th>View</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        {users.map(( user, index ) => (
                            <tr key={user.userDetails.username}>
                                <th>{index + 1}</th>
                                <td>{user.userDetails.username}</td>
                                <td>{user.email}</td>
                                <td>{user.userDetails.password}</td>
                                <td title={ConvertEpochToDate(user.metaData.creationTime).toString()}>{ConvertEpochToDate(user.metaData.creationTime).toDateString()}</td>
                                <td>{user.id}</td>
                                <td>
                                    {/* <Link
                                        to={`/user/` + user.id}
                                        className="viewUser button is-info is-light"
                                        data-tooltip-id="user-view-button"
                                        data-tooltip-html={
                                            "View user: " + user.userDetails.username
                                        }
                                        data-tooltip-place="left"
                                    >
                                        <FontAwesomeIcon icon={faEye} />
                                    </Link>
                                    <ReactTooltip id='user-view-button' /> */}
                                    <UserModal user_id={user.id} />
                                </td>
                                <td>
                                    <UserDelete user_username={user.userDetails.username} user_id={user.id}/>
                                </td>
                                <td>
                                    <UpdateUser user_id={user.id} userDetails={user.userDetails} user_email={user.email} metaData={user.metaData}/>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className='container m-4'>
                <SignupModal btn={<span><FontAwesomeIcon icon={faPlus} />&nbsp;&nbsp;Add new user</span>} create_user_path={CREATE_USER_PATH}/>
            </div>

        </>
    )
}