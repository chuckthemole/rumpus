const React = require('react');
import { Link } from 'react-router-dom';
import Modal from 'react-modal';
import UserIcon from './user/user_icon';
import { getCurrentUserAuthorities, isCurrentUserAuthenticated } from './rumpus';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';

// bind modal to app element https://reactcommunity.org/react-modal/accessibility/
// Modal.setAppElement('#root'); // TODO: can move this to app.js or index.js maybe, to be more central?

export default function Header() {

    // buttons and icons, empty by default.
    let user_icon = '';
    let admin = '';
    let signout = '';
    let login = '';
    let signup = '';

    // set button and icon visibility depending on user's authentication status and/or state (ie logged in or logged out)
    let is_user_authenticated = isCurrentUserAuthenticated();
    let authorities = getCurrentUserAuthorities();
    if(!is_user_authenticated.isAuthenticated && !is_user_authenticated.isLoading) {
        login = <LoginModal />;
        signup = <SignupModal />;
    } else if(is_user_authenticated.isAuthenticated) {
        user_icon = <UserIcon />;
        if(authorities.includes('ROLE_ADMIN')) {
            admin = <Link to={`/admin`} className="adminBtn button is-info"><strong>Admin</strong></Link>;
        }
        signout = <Logout />;
    }
   
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
        </nav>
    )
}