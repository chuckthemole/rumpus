const React = require('react');

import { useState } from 'react';
import { START_SERVER_PATH } from '../rumpus';

export async function StartServer(server) {
    await fetch(START_SERVER_PATH + server).then(response => response.json()).then(data => {
        console.log('data: ', data);
        // if(data.attributes.status == 'server started') {
        //     alert('Server \'' + server + '\' started!');
        // } else {
        //     alert('Error starting server \'' + server + '\'!');
        // }
    });
}

export default function ServerAction({ server, action, disableButton }) {

    const [button, setButton] = useState(
        action == 'start_server' ? <button onClick={e => handleStartServerSubmit} className="button is-success is-small">Start</button> :
        action == 'stop_server' ? <button onClick={e => handleStopServerSubmit} className="button is-danger is-small">Stop</button> :
        action == 'restart_server' ? <button onClick={e => handleRestartServerSubmit} className="button is-warning is-small">Restart</button> :
        <button className="button is-small">{action}</button>
    );

    async function handleStartServerSubmit(e) {
        e.preventDefault();
        console.log('testing testing 123');
        console.log('server: ', server);
    }

    async function handleStopServerSubmit(e) {
        e.preventDefault();
        console.log('testing testing 123');
        console.log('server: ', server);
    }

    async function handleRestartServerSubmit(e) {
        e.preventDefault();
        console.log('testing testing 123');
        console.log('server: ', server);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        console.log('testing testing 123');
        console.log('server: ', server);
        // if(deleteUser == true) {
        //     const requestOptions = {
        //         method: Common.POST,
        //         // redirect: "follow",
        //         entity: username,
        //         headers: { 'Content-Type': 'application/json' },
        //         body: JSON.stringify(username)
        //     };

        //     let currentUser = {};
        //     await load_current_user().then(user => {
        //         console.log('user: ', user);
        //         currentUser = user;
        //     });
            
        //     await fetch(DELETE_USER_PATH, requestOptions).then(response => response.json()).then(data => {
        //         // console.log('data: ', data);
        //         if(data.attributes.status == 'user deleted') {
        //             alert('User \'' + username + '\' deleted!');
        //             fetch('/api/log_action', CreateLogItemRequest('ADMIN_LOG', 'DELETE user with username: ' + username, currentUser.id, currentUser.username));
        //         } else {
        //             alert('Error deleting user \'' + username + '\'!');
        //         }
        //     });
        // }
    }

    return (
        <>
            {button}
        </>
    );
}