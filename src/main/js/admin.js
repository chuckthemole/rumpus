import Users from './user/users';

const React = require('react');
const ReactDOM = require('react-dom/client');

export default function Admin() {

    const [homeActive, setHome] = React.useState(true);
    const [usersActive, setUsers] = React.useState(false);
    const [temp1Active, setTemp1] = React.useState(false);
    const [temp2Active, setTemp2] = React.useState(false);

    const [activeWindow, setActiveWindow] = React.useState(<div><span>Admin Page</span></div>);

    function clear() {
        setHome(false);
        setUsers(false);
        setTemp1(false);
        setTemp2(false);
    }

    return (
        <>
            <div className="tabs is-boxed">
                <ul>
                    <li className={`adminTab ${homeActive && 'is-active'}`}>
                        <a onClick={ ()=> { clear(); setHome(true); setActiveWindow(<div><span>Admin Page</span></div>); } }>
                            <span>Admin Home</span>
                        </a>
                    </li>
                    <li className={`usersTab ${usersActive && 'is-active'}`}>
                        <a onClick={ ()=> { clear(); setUsers(true); setActiveWindow(<Users />); } }>
                            <span>Users</span>
                        </a>
                    </li>
                    <li className={`temp1Tab ${temp1Active && 'is-active'}`}>
                        <a onClick={ ()=> { clear(); setTemp1(true); setActiveWindow(<div><span>Temp1</span></div>); } }>
                            <span>I Need</span>
                        </a>
                    </li>
                    <li className={`temp2Tab ${temp2Active && 'is-active'}`}>
                        <a onClick={ ()=> { clear(); setTemp2(true); setActiveWindow(<div><span>Temp2</span></div>); } }>
                            <span>More Tabs</span>
                        </a>
                    </li>
                </ul>
            </div>

            {activeWindow}
        </>
    )
}