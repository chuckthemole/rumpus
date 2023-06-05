const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './user/users';
import Footer from './footer';
import UserTable from './user/user_table';
import Signup from './user/signup';
import UpdateUser from './user/update';
import Login from './user/login';
import ErrorBoundary from './error';

import { Common, modals } from "./rumpus";

function InitModals() {
    modals.forEach((modal) => {
        Common.ActivateModal(modal.container, modal.button);
    });
}

// Not using now, may user later. Instead the logout is done by a form in header.html
function Logout() {
    var logoutBtn = document.getElementsByClassName("logoutBtn")[0];
    if (logoutBtn !== undefined) {
        console.log("Logging out...");
        logoutBtn.onclick = function () {
            console.log("Really logging out...");
            const requestOptions = {
                method: 'POST',
            };
            return fetch('/logout', requestOptions)
                .then(() => {
                    window.location.reload();
                })
        }
    } else {
        console.log("can't find logout button!");
    }
}

export default function App() {
    const users = document.getElementById('users');
    if (typeof(users) != 'undefined' && users != null) { // veriry users id exists in DOM
        const reactDOMUsers = ReactDOM.createRoot(users);
        reactDOMUsers.render(
            <ErrorBoundary fallback={<p>Something went wrong</p>}>
                <Users />
            </ErrorBoundary>
        );
    }

    const footer = document.getElementById('footer');
    if (typeof(footer) != 'undefined' && footer != null) { // veriry users id exists in DOM
        const reactDOMFooter = ReactDOM.createRoot(footer);
        reactDOMFooter.render(<Footer />);
    }

    const signup = document.getElementById('signup-form');
    if (typeof(signup) != 'undefined' && signup != null) { // verify users id exists in DOM
        const reactDOMSignup = ReactDOM.createRoot(signup);
        reactDOMSignup.render(<Signup />);
    }

    // const update_user = document.getElementById('update_user-form');
    // if (typeof(update_user) != 'undefined' && update_user != null) {
    //     const reactDOMUpdateUser = ReactDOM.createRoot(update_user);
    //     reactDOMUpdateUser.render(<UpdateUser />);
    // } else {
    //     console.log("Error with update_user");
    // }

    const user_table = document.getElementById('user_table');
    if (typeof(user_table) != 'undefined' && user_table != null) { // veriry users id exists in DOM
        const reactDOMUserTable = ReactDOM.createRoot(user_table);
        reactDOMUserTable.render(<UserTable />)
    }

    // const login = ReactDOM.createRoot(document.getElementById('login-form'));
    // login.render(<Login />);

    InitModals();
    // Logout(); not using right now, see above Logout().
}

// * * * Main * * *
App();