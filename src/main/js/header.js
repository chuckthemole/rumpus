const React = require('react');
import useSWR from 'swr';
import { Form, Link } from 'react-router-dom';
import Modal from 'react-modal';
import UserIcon from './user/user_icon';
import { getCurrentUser, getCurrentUserAuthorities, getUserById, isCurrentUserAuthenticated } from './rumpus';
import SignupModal from './signup_modal';

// const fetcher = (...args) => fetch(...args).then((res) => res.json());

// bind modal to app element https://reactcommunity.org/react-modal/accessibility/
Modal.setAppElement('#root');

const customStyles = {
    content: {
        // top: '50%',
        // left: '50%',
        // right: 'auto',
        // bottom: 'auto',
        // marginRight: '-50%',
        transform: 'translate(0%, 70%)',
    },
};

export default function Header() {

    let is_user_authenticated = isCurrentUserAuthenticated();
    let authorities = getCurrentUserAuthorities();

    // buttons and icons
    let user_icon = '';
    let admin = '';
    let signout = '';
    let login = '';
    let signup = '';

    if(!is_user_authenticated.isAuthenticated && !is_user_authenticated.isLoading) {
        login = <a onClick={openLoginModal} className="loginBtn button is-success is-light">Log in</a>;
        // signup = <a onClick={openSignupModal} className="signupBtn button is-success is-light">Sign up</a>;
        signup = <SignupModal />;
    } else if(is_user_authenticated.isAuthenticated) {
        user_icon = <UserIcon />;
        if(authorities.includes('ROLE_ADMIN')) {
            admin = <Link to={`/admin`} className="adminBtn button is-info"><strong>Admin</strong></Link>;
        }
        signout = <form method="post" action="/logout"><button className="logoutBtn button is-danger" type="submit" value="Sign Out">Sign Out</button></form>;
    }

    let subtitle;
    const [loginModalIsOpen, setLoginModalIsOpen] = React.useState(false);
    // const [signupModalIsOpen, setSignupModalIsOpen] = React.useState(false);

    function openLoginModal() {
        setLoginModalIsOpen(true);
    }

    function afterOpenLoginModal() {
        // references are now sync'd and can be accessed.
        subtitle.style.color = '#f00';
    }

    function closeLoginModal() {
        setLoginModalIsOpen(false);
    }

    // function openSignupModal() {
    //     setSignupModalIsOpen(true);
    // }

    // function afterOpenSignupModal() {
    //     // references are now sync'd and can be accessed.
    //     subtitle.style.color = '#f00';
    // }

    // function closeSignupModal() {
    //     setSignupModalIsOpen(false);
    // }
   
    return (
        <nav className="navbar" role="navigation" aria-label="main navigation">
            <div className="navbar-brand">
                <Link to={`/`} className="navbar-item">
                    <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28" />
                </Link>
        
                <a role="button" className="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                </a>
            </div>
        
            <div id="navbarBasicExample" className="navbar-menu">
                <div className="navbar-start">
                    <Link to={`/`} className="navbar-item">
                        Home
                    </Link>
        
                    <a className="navbar-item">
                        Documentation
                    </a>

                    <div className="navbar-item has-dropdown is-hoverable">
                        <a className="navbar-link">
                            More
                        </a>
        
                        <div className="navbar-dropdown">
                            <a className="navbar-item">
                                About
                            </a>
                            <a className="navbar-item">
                                Jobs
                            </a>
                            <a className="navbar-item">
                                Contact
                            </a>
                            <hr className="navbar-divider" />
                            <a className="navbar-item">
                                Report an issue
                            </a>
                        </div>
                    </div>
                </div>
        
                <div className="navbar-end">
                    {user_icon}
                    <div className="navbar-item">
                        <div className="buttons">
                            {admin}
                            {signup}
                            {login}
                            {signout}
                        </div>
                    </div>
                </div>
            </div>

            <Modal
                isOpen={loginModalIsOpen}
                onAfterOpen={afterOpenLoginModal}
                onRequestClose={closeLoginModal}
                className='modal-content'
                style={customStyles}
                contentLabel="Example Modal">
                    
                <h2 ref={(_subtitle) => (subtitle = _subtitle)}>Hello</h2>

                <div className="modal-content">
                    <form action="/login" method="post" className='box'>
                        <div className='field'>
                            <label htmlFor='' className='label'>Username</label>
                            <div className='control has-icons-left'>
                                <input name='username' type='username' placeholder='e.g. coolguy' className='input' required />
                                <span className='icon is-small is-left'>
                                    <i className='fa fa-en  velope'></i>
                                </span>
                            </div>
                        </div>
                        <div className='field'>
                            <label htmlFor='' className='label'>Password</label>
                            <div className='control has-icons-left'>
                                <input name='password' type='password' placeholder='*******' className='input' required />
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
                </div>
                <button className="modal-close is-large" aria-label="close"></button>

            </Modal>

            {/* <Modal
                isOpen={signupModalIsOpen}
                onAfterOpen={afterOpenSignupModal}
                onRequestClose={closeSignupModal}
                // style={customStyles}
                contentLabel="Example Modal">
                
                <div>todo</div>
            </Modal> */}
        </nav>
    )
}