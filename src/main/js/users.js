const React = require('react');
import useSWR from 'swr';

// const fetcher = (...args) => fetch(...args).then((res) => res.json());

const fetcher = async url => {
    const res = await fetch(url)

    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!res.ok) {
        const error = new Error('An error occurred while fetching the data.')
        // Attach extra info to the error object.
        error.info = await res.json()
        error.status = res.status
        throw error
    }

    return res.json()
}

function Users() {
    const { data, error } = useSWR(
        "/api/users",
        fetcher
    );

    console.log(data);
    console.log(error);

    if (error) return <div className='container m-6'><div className='notification is-primary'><p>An error occurred</p></div></div>;
    if (!data) return <div className='container m-6'><div className='notification is-primary'><p>Loading...</p></div></div>;
    if (data.error == 'Forbidden') return <div className='container m-6'><div className='notification is-primary'><p>User is not authorized to view users</p></div></div>;

    return (
        <div className='container m-6'>
            <div className='notification is-primary'>
                <table className="table is-hoverable is-fullwidth">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th><abbr title="User Name">User</abbr></th>
                            <th>Email</th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Authorizations">Auth</abbr></th>
                            <th>ID</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>#</th>
                            <th><abbr title="User Name">User</abbr></th>
                            <th>Email</th>
                            <th><abbr title="Password">Pass</abbr></th>
                            <th><abbr title="User Authorizations">Auth</abbr></th>
                            <th>ID</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        {data.map(({ userDetails, email, auth, id, index }) => (
                            <tr key={userDetails.username}>
                                <th>{index}</th>
                                <td>{userDetails.username}</td>
                                <td>{email}</td>
                                <td>{userDetails.password}</td>
                                <td>{auth}</td>
                                <td>{id}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Users;