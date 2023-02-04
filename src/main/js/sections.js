const React = require('react');
const client = require('./client');

class Footer extends React.Component {

	constructor(props) {
		super(props);
		this.state = {columns: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/footer'}).done(response => {
            console.log(response.entity);
			this.setState(response);
		});
        console.log(this.state);
	}

	render() {
        <div></div>
		// return (<ColumnList columns={this.state.columns}/>)
	}
}

class ColumnList extends React.Component {
	render() {
		const columns = this.props.columns.map(column =>
			<Column key={column.title} column={column}/>
		);
		return (
            <div class="columns">
                {columns}
            </div>
			// <table>
			// 	<tbody>
			// 		<div class="columns">
			// 			<div class="column">Name</div>
			// 			<div class="column">Name2</div>
			// 			<div class="column">Name3</div>
			// 		</div>
			// 		{columns}
			// 	</tbody>
			// </table>
		)
	}
}

class Column extends React.Component {
	render() {
        // const items = this.props.column.map(item =>
        //     <Item item=/>
        // );
		return (
			<div class="column">
				<div class="column">{this.props.column.title}</div>
				<div class="column">{this.props.column.title}</div>
				<div class="column">{this.props.column.title}</div>
			</div>
		)
	}
}

export default Footer;