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
					<tr>
						<th>Name</th>
						{/* <th>Last Name</th>
						<th>Description</th> */}
					</tr>
					{users}
				</tbody>
			</table>
		)
	}
}

class User extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.user.name}</td>
				{/* <td>{this.props.user.lastName}</td>
				<td>{this.props.user.description}</td> */}
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)