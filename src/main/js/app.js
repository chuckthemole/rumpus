const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';

function Login() {
    var modal = document.getElementsByClassName("login")[0];
    var btn = document.getElementsByClassName("loginBtn")[0];
    var span = document.getElementsByClassName("close")[0]; // closes modal

    // user clicks the button, open modal login
    btn.onclick = function() {
        modal.classList.remove("is-hidden");
    }

    // user clicks on x, close the modal 
    span.onclick = function() {
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
}

// * * * Main * * *
App();