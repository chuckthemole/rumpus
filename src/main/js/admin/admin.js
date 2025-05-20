const React = require('react');
const ReactDOM = require('react-dom/client');

import AdminHome from './tab_home';
import Users from '../user/users';
import Log from '@rumpushub/common-react/dist/components/log';
import Servers from './servers';

export default function Admin() {

    const is_active = 'is-active';

    const [homeActive, setHome] = React.useState(true);
    const [usersActive, setUsers] = React.useState(false);
    const [logsActive, setLogs] = React.useState(false);
    const [serversActive, setServers] = React.useState(false);

    const [activeWindow, setActiveWindow] = React.useState(<AdminHome />);

    function clear() {
        setHome(false);
        setUsers(false);
        setLogs(false);
        setServers(false);
    }

    return (
        <>
            <div className="tabs is-boxed">
                <ul>
                    <li className={`adminTab ${homeActive && is_active}`}>
                        <a onClick={ ()=> { clear(); setHome(true); setActiveWindow(<AdminHome />); } }>
                            <span>Admin Home</span>
                        </a>
                    </li>
                    <li className={`usersTab ${usersActive && is_active}`}>
                        <a onClick={ ()=> { clear(); setUsers(true); setActiveWindow(<Users />); } }>
                            <span>Users</span>
                        </a>
                    </li>
                    <li className={`logsTab ${logsActive && is_active}`}>
                        <a onClick={ ()=> { clear(); setLogs(true); setActiveWindow(<Log log_identifier={'ADMIN_LOG'}/>); } }>
                            <span>Logs</span>
                        </a>
                    </li>
                    <li className={`serversTab ${serversActive && is_active}`}>
                        <a onClick={ ()=> { clear(); setServers(true); setActiveWindow(<Servers />); } }>
                            <span>Servers</span>
                        </a>
                    </li>
                </ul>
            </div>

            <div className='is-centered has-background-light box'>{activeWindow}</div>
        </>
    )
}