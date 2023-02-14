const React = require('react');
import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

function Footer() {
    const { data, error } = useSWR(
        "/api/footer",
        fetcher
    );

    if (error) return <p>An error occurred</p>;
    if (!data) return <p>Loading</p>;
   
    return (
        <div className="columns">
            {data.columns.map(({items, title}) => (
                <div className="column" key={title}>
                    <div>{title}</div>
                    {items.map(item => 
                        <div key={item}>
                            <span>{item}</span>
                        </div>
                    )}
			    </div>
            ))}
        </div>
    )
}

export default Footer;