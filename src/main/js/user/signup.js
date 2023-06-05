const React = require('react');

import { Common, CREATE_USER_PATH } from "../rumpus";

class Signup extends React.Component {
    constructor(props) {
        super(props);
        this.state = {username: Common.EMPTY, email: Common.EMPTY, password: Common.EMPTY, id: Common.EMPTY};

        this.handleUsername = this.handleUsername.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onCreate = this.onCreate.bind(this);
        // this.onLogin = this.onLogin.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    handleUsername(event) {
        this.setState({username: event.target.value});
    }
    handleEmail(event) {
        this.setState({email: event.target.value});
    }
    handlePassword(event) {
        this.setState({password: event.target.value});
    }
  
    async handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.username + ' ' + this.state.email + ' ' + this.state.password);
        event.preventDefault();

        const newUser = {};
        newUser[Common.USERNAME] = this.state.username;
        newUser[Common.PASSWORD] = this.state.password;
        newUser[Common.EMAIL] = this.state.email;
        const fetched = await this.onCreate(newUser);
        // const fetchedLogin = await this.onLogin(newUser);
        console.log(fetched);

        const signupModal = document.getElementsByClassName("signup")[0];
        signupModal.classList.remove("is-active");
        signupModal.classList.remove("is-clipped");
        this.clearInput();
    }

    async onCreate(newUser) {
        const requestOptions = {
            method: Common.POST,
            redirect: "follow",
            entity: newUser,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        };
        // return fetch('/api/user', requestOptions)
        return fetch(CREATE_USER_PATH, requestOptions)
            .then(response => {
                // response.json()
                console.log(response);
                console.log(response.json());
            })
            .then(data => {
                // this.setState({ postId: data.id })
                console.log(data);
                // const loginFetched = this.onLogin(newUser);
                // console.log(loginFetched);
            })
            .then(() => { // reload window
                window.location.reload();
            });
	}

    // async onLogin(user) {
    //     const requestOptions = {
    //         method: 'POST',
    //         redirect: "follow",
    //         entity: user,
    //         headers: { 'Content-Type': 'application/json' },
    //         body: JSON.stringify(user)
    //     };
    //     return fetch('/user_login', requestOptions)
    //         .then(() => {
    //             window.location.reload();
    //         });
    //         // .then(response => response.json());
    //         // .then(data => this.setState({ postId: data.id }));
	// }

    clearInput() {
        this.setState({username: Common.EMPTY});
        this.setState({email: Common.EMPTY});
        this.setState({password: Common.EMPTY});
    }
  
    render() {
        return (

            <form onSubmit={this.handleSubmit} className="box">
                <div className="field">
                    <label htmlFor="" className="label">Username</label>
                    <div className="control has-icons-left">
                        <input type="username" placeholder="e.g. coolguy" className="input" value={this.state.username} onChange={this.handleUsername} required />
                            <span className="icon is-small is-left">
                                <i className="fa fa-envelope"></i>
                            </span>
                    </div>
                </div>
                <div className="field">
                    <label htmlFor="" className="label">Email</label>
                    <div className="control has-icons-left">
                        <input type="email" placeholder="e.g. bobsmith@gmail.com" className="input" value={this.state.email} onChange={this.handleEmail} required />
                            <span className="icon is-small is-left">
                                <i className="fa fa-envelope"></i>
                            </span>
                    </div>
                </div>
                <div className="field">
                    <label htmlFor="" className="label">Password</label>
                    <div className="control has-icons-left">
                        <input type="password" placeholder="*******" className="input" value={this.state.password} onChange={this.handlePassword} required />
                        <span className="icon is-small is-left">
                            <i className="fa fa-lock"></i>
                        </span>
                    </div>
                </div>
                {/* <!-- <div className="field">
                    <label htmlFor="" className="label">Confirm Password</label>
                    <div className="control has-icons-left">
                        <input type="confirm_password" placeholder="*******" className="input" required>
                        <span className="icon is-small is-left">
                            <i className="fa fa-lock"></i>
                        </span>
                    </div>
                </div> --> */}
                <div className="field">
                    <button id="signupSubmit" type="submit" value="Signup" className="button is-success">
                        Submit
                    </button>
                </div>
            </form>

        );
    }
}
  
export default Signup;