const React = require('react');
import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

function UserTable() {
    const { data, error } = useSWR(
        "/view/user_table",
        fetcher
    );

    console.log(data);
    if (error) return <p>An error occurred</p>;
    if (!data) return <p>Loading</p>;
   
    // return (
    //     <div className="columns">
    //         {data}
    //     </div>
    // )
}

export default UserTable;