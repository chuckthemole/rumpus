const React = require('react');
const ReactDOM = require('react-dom/client');

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Common, DELETE_USER_PATH, GET_USERS_PATH, CREATE_USER_PATH, GET_SERVERS_PATH, START_SERVER_PATH, STOP_SERVER_PATH } from "../rumpus";
import { useLoaderData, Link, useFetcher } from 'react-router-dom';
import ServerAction, { StartServer } from './server_action';

// sort flags
const sort_by_username = '/username';
const sort_by_email = '/email';
const sort_by_id = '/id';
// keeps track of the current sort
let current_sort;

export async function loader() {
    const response = await fetch(GET_SERVERS_PATH);
    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!response.ok) {
        const error = new Error('An error occurred while fetching servers.');
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

export async function delete_server(server_name) {

}

export default function Servers() {

    const [servers, setServers] = React.useState(useLoaderData());
    console.log(servers);
    
    React.useEffect(() => { // TODO: this calls the api a lot. figure out a resolution.
        loader().then((response) => {
            setServers(response);
        });
    }, [servers]); // TODO: can i use effect when button pressed?

    if (!servers) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )

    return (
        <>
            <div className='table-container'>
                <table className="table is-hoverable is-fullwidth is-bordered m-6">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>
                                Server
                            </th>
                            <th>
                                Start
                            </th>
                            <th>
                                Stop
                            </th>
                            <th>
                                Running
                            </th>
                            <th>
                                Host
                            </th>
                            <th>
                                Port
                            </th>
                            <th>
                                View
                            </th>
                            <th>
                                Delete
                            </th>
                            <th>
                                Update
                            </th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>#</th>
                            <th>
                                Server
                            </th>
                            <th>
                                Start
                            </th>
                            <th>
                                Stop
                            </th>
                            <th>
                                Running
                            </th>
                            <th>
                                Host
                            </th>
                            <th>
                                Port
                            </th>
                            <th>
                                View
                            </th>
                            <th>
                                Delete
                            </th>
                            <th>
                                Update
                            </th>
                        </tr>
                    </tfoot>
                    <tbody>
                        {servers.map(( server, index ) => (
                            <tr key={server.serverName}>
                                <th>{index + 1}</th>
                                <td>{server.serverName}</td>
                                <td>{server.isRunning ? <button className="button is-success is-small" disabled>Start</button> : <button onClick={() => fetch(START_SERVER_PATH + server.serverName)} className="button is-success is-small">Start</button>}</td>
                                <td>{!server.isRunning ? <button className="button is-danger is-small" disabled>Stop</button> : <button onClick={() => fetch(STOP_SERVER_PATH + server.serverName)} className="button is-danger is-small">Stop</button>}</td>   
                                {/* <td>
                                    {server.isRunning ? <ServerAction disabled server={server} action='start_server' /> : <ServerAction disabled server={server} action='start_server' />}
                                </td>
                                <td>
                                    {server.isRunning ? <ServerAction server={server} action='stop_server' /> : <ServerAction server={server} action='stop_server' />}
                                </td> */}
                                <td>{server.isRunning ? "TRUE" : "FALSE"}</td>
                                <td>{server.hostIp}</td>
                                <td>{server.port}</td>
                                {/* <td title={ConvertEpochToDate(user.metaData.creationTime).toString()}>{ConvertEpochToDate(user.metaData.creationTime).toDateString()}</td> */}
                                <td>
                                    todo
                                </td>
                                <td>
                                    todo
                                </td>
                                <td>
                                    todo
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className='container m-4'>
                todo
            </div>

        </>
    )
}