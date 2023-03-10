const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';
import Signup from './signup';
import Login from './login';

function InitModals() {
    // Add modal here Map[name, modal]
    // modal array: index 0 is modal, index 1 is button
    const modals = new Map();
    modals.set("signup", [
        document.getElementsByClassName("signup")[0],
        document.getElementsByClassName("signupBtn")[0]]
    );
    modals.set("login", [
        document.getElementsByClassName("login")[0],
        document.getElementsByClassName("loginBtn")[0]]
    );

    // For each modal, add click event
    // on button click add is-active, on x click remove is-active
    for(let value of modals.values()) {
        value[1].onclick = function() {
            value[0].classList.add("is-active");
            value[0].classList.add("is-clipped");
        }
        value[0].getElementsByClassName("modal-close")[0].onclick = function() {
            value[0].classList.remove("is-active");
            value[0].classList.remove("is-clipped");
        }
    }
    // If click outside the modal, close modal
    window.onclick = function(event) {
        var backgrounds = document.getElementsByClassName("modal-background");
        Array.prototype.forEach.call(backgrounds, function(background) { // get all modal backgrounds
            if(event.target == background) {
                var modals = document.getElementsByClassName("modal");
                Array.prototype.forEach.call(modals, function(modal) { // remove is-active from all modals
                    modal.classList.remove("is-active");
                    modal.classList.remove("is-clipped");
                });
            }
        });
    }
}

export default function App() {
    const users = ReactDOM.createRoot(document.getElementById('users'));
    users.render(<Users />);

    const footer = ReactDOM.createRoot(document.getElementById('footer'));
    footer.render(<Footer />);

    const signup = ReactDOM.createRoot(document.getElementById('signup-form'));
    signup.render(<Signup />);

    const login = ReactDOM.createRoot(document.getElementById('login-form'));
    login.render(<Login />);

    InitModals();
}

// * * * Main * * *
App();