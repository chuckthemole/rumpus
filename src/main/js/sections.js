const React = require('react');
const client = require('./client');

class Footer extends React.Component {

	constructor(props) {
		super(props);
		this.state = {cols: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/footer'}).done(response => {
			this.setState({cols: response.entity.columns});
		});
	}

	render() {
		return (<ColumnList columnList={this.state.cols}/>)
	}
}

class ColumnList extends React.Component {
	render() {
		const columns = this.props.columnList.map(column =>
			<Column key={column.title} column={column}/>
		);
		return (
            <div className="columns">
                {columns}
            </div>
		)
	}
}

class Column extends React.Component {
	render() {
        const items = this.props.column.items.map(item =>
            <Item key={item} item={item}/>
        );
		return (
			<div className="column">
				<div>{this.props.column.title}</div>
				{items}
			</div>
		)
	}
}

class Item extends React.Component {
    render() {
        return (
            <div>
                <span>{this.props.item}</span>
            </div>
        )
    }
}

export default Footer;