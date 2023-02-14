const React = require('react');
const client = require('./client');
import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

function Footer() {
    const { data, error } = useSWR(
        "http://localhost:8081/api/footer",
        fetcher
    );

    if (error) return <p>An error occurred</p>;
    if (!data) return <p>Loading</p>;
   
    return (
        <div className="columns">
            {data.columns.map(({items, title}) => (
                <FooterColumn key={title} title={title} column={items}/>
            ))}
        </div>
    )
}

export default Footer;

class FooterColumn extends React.Component {
	render() {
        const items = this.props.column.map(item =>
            <FooterItem key={item} item={item}/>
        );
		return (
			<div className="column">
				<div>{this.props.title}</div>
				{items}
			</div>
		)
	}
}

class FooterItem extends React.Component {
    render() {
        return (
            <div>
                <span>{this.props.item}</span>
            </div>
        )
    }
}

// export default Footer;