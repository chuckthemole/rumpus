const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';

function InitModals() {
    const modals = new Map();
    modals.set("login", [
        document.getElementsByClassName("login")[0], 
        document.getElementsByClassName("loginBtn")[0]]
    );
    modals.set("signup", [
        document.getElementsByClassName("signup")[0],
        document.getElementsByClassName("signupBtn")[0]]
    );

    for (let value of modals.values()) {
        var modal = value[0];
        var button = value[1];
        var background = modal.getElementsByClassName("modal-background")[0];
        var close = modal.getElementsByClassName("modal-close")[0];

        button.onclick = function() {
            modal.classList.add("is-active");
            modal.classList.add("is-clipped");
        }
        window.onclick = function(event) {
            if(event.target == background) {
                modal.classList.remove("is-active");
                modal.classList.remove("is-clipped");
            }
        }
        close.onclick = function() {
            modal.classList.remove("is-active");
            modal.classList.remove("is-clipped");
        }
    }
}

export default function App() {
    const users = ReactDOM.createRoot(document.getElementById('users'));
    users.render(<Users />);

    const footer = ReactDOM.createRoot(document.getElementById('footer'));
    footer.render(<Footer />);

    InitModals();
}

// * * * Main * * *
App();