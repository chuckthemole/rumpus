const React = require('react');
const ReactDOM = require('react-dom/client');

import AdminHome from './tab_home';
import Users from '../user/users';

export default function Admin() {

    const is_active = 'is-active';

    const [homeActive, setHome] = React.useState(true);
    const [usersActive, setUsers] = React.useState(false);
    const [temp1Active, setTemp1] = React.useState(false);
    const [temp2Active, setTemp2] = React.useState(false);

    const [activeWindow, setActiveWindow] = React.useState(<AdminHome />);

    function clear() {
        setHome(false);
        setUsers(false);
        setTemp1(false);
        setTemp2(false);
    }

    return (
        <>
            <div className='columns'>
                <div className='column is-four-fifths'>
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
                            <li className={`temp1Tab ${temp1Active && is_active}`}>
                                <a onClick={ ()=> { clear(); setTemp1(true); setActiveWindow(<div><span>Temp1</span></div>); } }>
                                    <span>I Need</span>
                                </a>
                            </li>
                            <li className={`temp2Tab ${temp2Active && is_active}`}>
                                <a onClick={ ()=> { clear(); setTemp2(true); setActiveWindow(<div><span>Temp2</span></div>); } }>
                                    <span>More Tabs</span>
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div className='is-centered has-background-light box'>{activeWindow}</div>
                </div>
            </div>
        </>
    )
}