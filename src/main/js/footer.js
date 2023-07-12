const React = require('react');
import useSWR from 'swr';

// - - - Alternate fetcher, keeping here for now just for reference - - - chuck 2023/7/12

// const fetcher = async url => {
//     const res = await fetch(url)

//     // If the status code is not in the range 200-299,
//     // we still try to parse and throw it.
//     if (!res.ok) {
//         const error = new Error('An error occurred while fetching the data.')
//         // Attach extra info to the error object.
//         error.info = await res.json()
//         error.status = res.status
//         throw error
//     }

//     return res.json()
// }

const fetcher = (...args) => fetch(...args).then((res) => res.json());

function Footer() {
    const { data, error } = useSWR(
        "/view/footer",
        fetcher
    );

    if (error) return <div className='container m-6'><div className='notification is-primary'><p>An error occurred</p></div></div>;
    // if (error) return <p>An error occurred</p>;
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