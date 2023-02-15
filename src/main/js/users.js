const React = require('react');
import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

function Users() {
    const { data, error } = useSWR(
        "/api/users",
        fetcher
    );

    if (error) return <p>An error occurred</p>;
    if (!data) return <p>Loading</p>;

    console.log(data);
   
    return (
        <table className="table is-hoverable is-fullwidth m-6">
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
                {data.map(({userName, email, password, auth, id, index}) => (
                    <tr key={userName}>
                        <th>{index}</th>
                        <td>{userName}</td>
                        <td>{email}</td>
                        <td>{password}</td>
                        <td>{auth}</td>
                        <td>{id}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

export default Users;