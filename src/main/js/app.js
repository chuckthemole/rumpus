const React = require('react');
const ReactDOM = require('react-dom/client');
const client = require('./client');

import Users from './users';
import Footer from './sections';

const users = ReactDOM.createRoot(document.getElementById('users'));
users.render(<Users />);

const footer = ReactDOM.createRoot(document.getElementById('footer'));
footer.render(<Footer />);