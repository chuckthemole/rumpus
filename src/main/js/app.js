const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './footer';
import Signup from './signup';

function InitModals() {
    const modals = new Map();
    // modals.set("login", [
    //     document.getElementsByClassName("login")[0], 
    //     document.getElementsByClassName("loginBtn")[0]]
    // );
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

function SignupForm() { // Not using right now
    // Add JQuery below here
    $(document).ready(function () {
        // Sign up form
        $("#signup-form").submit(function (event) {
    
            //stop submit the form event. Do this manually using ajax post function
            event.preventDefault();
    
            var signupForm = {}
            signupForm["username"] = $("#username").val();
            signupForm["password"] = $("#email").val();
            signupForm["password"] = $("#password").val();
    
            $("#btn-signup").prop("disabled", true);
            
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/signup",
                data: JSON.stringify(signupForm),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
    
                    var json = "<h4>Ajax Response</h4><pre>"
                        + JSON.stringify(data, null, 4) + "</pre>";
                    $('#feedback').html(json);
    
                    console.log("SUCCESS : ", data);
                    $("#btn-signup").prop("disabled", false);
    
                },
                error: function (e) {
    
                    var json = "<h4>Ajax Response Error</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#feedback').html(json);
    
                    console.log("ERROR : ", e);
                    $("#btn-signup").prop("disabled", false);
    
                }
            });
            
        });
    });
}

export default function App() {
    const users = ReactDOM.createRoot(document.getElementById('users'));
    users.render(<Users />);

    const footer = ReactDOM.createRoot(document.getElementById('footer'));
    footer.render(<Footer />);

    const signup = ReactDOM.createRoot(document.getElementById('signup-form'));
    signup.render(<Signup />);

    InitModals();
}

// * * * Main * * *
App();