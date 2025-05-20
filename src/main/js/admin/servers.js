import React, { useEffect, useState } from 'react';
const ReactDOM = require('react-dom/client');

import useSWR from 'swr';
import { common_fetcher } from '@rumpushub/common-react/dist/components/common_requests';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Common, DELETE_USER_PATH, GET_USERS_PATH, CREATE_USER_PATH, GET_SERVERS_PATH, START_SERVER_PATH, STOP_SERVER_PATH } from "../rumpus";
import { useLoaderData, Link, useFetcher } from 'react-router-dom';
import ServerAction, { StartServer } from './server_action';
import { RenderFromSWR } from '@rumpushub/common-react/dist/components/error_page';

export async function delete_server(server_name) {
}

/**
 * This component is responsible for displaying servers. TODO: not really using too much right now. Look into this more later. -chuck 2024/1/22
 * @returns the servers page in table view
 */
export default function Servers() {
    
    const [servers, setServers] = useState(useLoaderData());
    const [render_content, setRenderContent] = useState(<></>);
    const {data, error} = useSWR(
        GET_SERVERS_PATH,
        common_fetcher
    );
    
    // check if data is loaded then set servers
    useEffect(() => {
        if(data !== undefined && data !== null && data !== '') {
            // console.log(data);
            setServers(data);
        }
    }, [data]);

    // check if servers are set then set render_content
    useEffect(() => {
        if(servers !== undefined && servers !== null && servers !== '') {
            setRenderContent(
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
                                    <tr key={index}>
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
                </>
            )
        }
    }, [servers]);

    return RenderFromSWR(data, error, render_content, 'An error occurred getting servers.');
}