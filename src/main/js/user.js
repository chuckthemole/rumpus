const React = require('react');
const client = require('./client');

class User extends React.Component {
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

export default User;