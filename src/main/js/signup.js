const React = require('react');

const root = '/api';

class Signup extends React.Component {
    constructor(props) {
        super(props);
        this.state = {username: '', email: '', password: ''};

        this.handleUsername = this.handleUsername.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onCreate = this.onCreate.bind(this);
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
  
    handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.username + ' ' + this.state.email + ' ' + this.state.password);
        event.preventDefault();

        const newUser = {};
        newUser["userName"] = this.state.username;
        newUser["email"] = this.state.email;
        newUser["password"] = this.state.password;
        this.onCreate(newUser);

        const signupModal = document.getElementsByClassName("signup")[0];
        signupModal.classList.remove("is-active");
        signupModal.classList.remove("is-clipped");
        this.clearInput();
    }

    onCreate(newUser) {
        const requestOptions = {
            method: 'POST',
            entity: newUser,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        };
        fetch('/api/user', requestOptions)
            .then(response => response.json());
            // .then(data => this.setState({ postId: data.id }));
	}

    clearInput() {
        this.setState({username: ''});
        this.setState({email: ''});
        this.setState({password: ''});
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