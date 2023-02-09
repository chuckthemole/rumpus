const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {username: '', password: ''};

        this.handleUsername = this.handleUsername.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUsername(event) {
        this.setState({username: event.target.value});
    }
    handlePassword(event) {
        this.setState({password: event.target.value});
    }
  
    handleSubmit(event) {
      alert('A name was submitted: ' + this.state.username + ' ' + this.state.password);
      event.preventDefault();
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
                    <label htmlFor="" className="label">Password</label>
                    <div className="control has-icons-left">
                        <input type="password" placeholder="*******" className="input" value={this.state.password} onChange={this.handlePassword} required />
                        <span className="icon is-small is-left">
                            <i className="fa fa-lock"></i>
                        </span>
                    </div>
                </div>
                <div className="field">
                    <button type="submit" value="Login" className="button is-success">
                        Submit
                    </button>
                </div>
            </form>

        );
    }
}
  
export default Login;