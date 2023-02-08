const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';

const modals = new Map();
modals.set("login", document.getElementsByClassName("login")[0]);
modals.set("signup", document.getElementsByClassName("signup")[0]);

function Login() {
    var modal = document.getElementsByClassName("login")[0];
    var button = document.getElementsByClassName("loginBtn")[0];
    var close = document.getElementsByClassName("loginClose")[0];
    ModalCommon(button, modal, close);
}

function Signup() {
    var modal = document.getElementsByClassName("signup")[0];
    var button = document.getElementsByClassName("signupBtn")[0];

    button.onclick = function() {
        modal.classList.add("is-active");
        modal.classList.add("is-clipped");
    }
}

function ModalBackgroundOnClick() {
    var backgrounds = document.getElementsByClassName("modal-background");
    Array.prototype.forEach.call(backgrounds, function(background) {
        window.onclick = function(event) {
            if (event.target == background) {
                for (let modal of modals.values()) {
                    modal.classList.remove("is-active");
                    modal.classList.remove("is-clipped");
                }
            }
        }
    });
}
function ModalCloseOnClick() {
    var closers = document.getElementsByClassName("modal-close");
    Array.prototype.forEach.call(closers, function(close) {
        close.onclick = function() {
            for (let modal of modals.values()) {
                modal.classList.remove("is-active");
                modal.classList.remove("is-clipped");
            }
        }
    });
}

function ModalCommon(button, modal, close) {
    // user clicks the button, open modal login
    button.onclick = function() {
        modal.classList.remove("is-hidden");
    }

    // user clicks on x, close the modal 
    close.onclick = function() {
        modal.classList.add("is-hidden");
    }

    // user clicks anywhere outside, close it 
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.classList.add("is-hidden");
        }
    }
}

export default function App() {
    const users = ReactDOM.createRoot(document.getElementById('users'));
    users.render(<Users />);

    const footer = ReactDOM.createRoot(document.getElementById('footer'));
    footer.render(<Footer />);

    Login();
    Signup();
    ModalBackgroundOnClick();
    ModalCloseOnClick();
}

// * * * Main * * *
App();