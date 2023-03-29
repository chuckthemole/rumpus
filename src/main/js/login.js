const React = require('react');

const root = '/api';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {username: '', password: ''};

        this.handleUsername = this.handleUsername.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onLogin = this.onLogin.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    handleUsername(event) {
        this.setState({username: event.target.value});
    }
    handlePassword(event) {
        this.setState({password: event.target.value});
    }
  
    handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.username + ' ' + this.state.password);
        event.preventDefault();

        const user = {};
        user['username'] = this.state.username;
        user['password'] = this.state.password;
        this.onLogin(user);

        const loginModal = document.getElementsByClassName('login')[0];
        loginModal.classList.remove('is-active');
        loginModal.classList.remove('is-clipped');
        this.clearInput();
    }

    onLogin(user) {
        const requestOptions = {
            method: 'POST',
            redirect: "follow",
            // entity: user,
            // headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        };
        fetch('/login', requestOptions)
            // .then(response => response.json());
            // .then(data => this.setState({ postId: data.id }));
	}

    clearInput() {
        this.setState({username: ''});
        this.setState({password: ''});
    }
  
    render() {
        return (

            <form onSubmit={this.handleSubmit} className='box'>
                <div className='field'>
                    <label htmlFor='' className='label'>Username</label>
                    <div className='control has-icons-left'>
                        <input name='username' type='username' placeholder='e.g. coolguy' className='input' value={this.state.username} onChange={this.handleUsername} required />
                            <span className='icon is-small is-left'>
                                <i className='fa fa-envelope'></i>
                            </span>
                    </div>
                </div>
                <div className='field'>
                    <label htmlFor='' className='label'>Password</label>
                    <div className='control has-icons-left'>
                        <input name='password' type='password' placeholder='*******' className='input' value={this.state.password} onChange={this.handlePassword} required />
                        <span className='icon is-small is-left'>
                            <i className='fa fa-lock'></i>
                        </span>
                    </div>
                </div>
                <div className='field'>
                    <button id='loginSubmit' type='submit' value='Login' className='button is-success'>
                        Submit
                    </button>
                </div>
            </form>

        );
    }
}
  
export default Login;