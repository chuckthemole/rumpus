const React = require('react');
const client = require('./client');

class Users extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/users'}).done(response => {
            console.log(response.entity);
			this.setState({users: response.entity});
		});
	}

	render() {
		return (
			<UserList users={this.state.users}/>
		)
	}
}

// Look into changing to function instead of Component    https://beta.reactjs.org/reference/react/Component#alternatives
// import { useState } from 'react';

// export default function Users() {

//     const [name, setName] = useState({users: []});

//     function handleNameChange(e) {
//         setName(e.target.value);
//     }

//     useEffect(() => {
//         client({method: 'GET', path: '/api/users'}).done(response => {
// 			this.setState({users: response.entity});
// 		});
//     })
// }

class UserList extends React.Component {
	render() {
		const users = this.props.users.map(user =>
			<User key={user.userName} user={user}/>
		);
        console.log(this.props.users);
		return (
            <div>
                <div className="columns">
                    <div className="column">Name</div>
                    <div className="column">Name2</div>
                    <div className="column">Name3</div>
                </div>
                {users}
            </div>
		)
	}
}

class User extends React.Component {
	render() {
		return (
			<div className="columns">
				<div className="column">{this.props.user.userName}</div>
				<div className="column">{this.props.user.userName}</div>
				<div className="column">{this.props.user.userName}</div>
			</div>
		)
	}
}

export default Users;