const React = require('react');
const client = require('./client');

class Users extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/users'}).done(response => {
			this.setState({users: response.entity});
		});
        console.log(this.state);
	}

	render() {
		return (
			<UserList users={this.state.users}/>
		)
	}
}

class UserList extends React.Component {
	render() {
		const users = this.props.users.map(user =>
			<User key={user.name} user={user}/>
		);
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
				<div className="column">{this.props.user.name}</div>
				<div className="column">{this.props.user.name}</div>
				<div className="column">{this.props.user.name}</div>
			</div>
		)
	}
}

export default Users;