const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/users'}).done(response => {
			this.setState({users: response.entity});
            console.log(this.state);
		});
	}

	render() {
		return (
			<UserList users={this.state.users}/>
		)
	}
}

class UserList extends React.Component{
	render() {
		const users = this.props.users.map(user =>
			<User key={user.name} user={user}/>
		);
		return (
			<table>
				<tbody>
					<div class="columns">
						<div class="column">Name</div>
						<div class="column">Name2</div>
						<div class="column">Name3</div>
					</div>
					{users}
				</tbody>
			</table>
		)
	}
}

class User extends React.Component{
	render() {
		return (
			<div class="columns">
				<div class="column">{this.props.user.name}</div>
				<div class="column">{this.props.user.name}</div>
				<div class="column">{this.props.user.name}</div>
			</div>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)