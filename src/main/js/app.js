const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';

function Login() {
    var modal = document.getElementsByClassName("login")[0];
    var button = document.getElementsByClassName("loginBtn")[0];
    var close = document.getElementsByClassName("loginClose")[0];
    ModalCommon(button, modal, close);
}

function Signup() {
    var modal = document.getElementsByClassName("signup")[0];
    var button = document.getElementsByClassName("signupBtn")[0];
    var close = document.getElementsByClassName("signupClose")[0];
    ModalCommon(button, modal, close);
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
}

// * * * Main * * *
App();