const React = require('react');
// const ReactDOM = require('react-dom/client');

import { useLoaderData, Link, useFetcher } from 'react-router-dom';

export async function loader() {

    const response = await fetch('/api/admin/forum_posts');
    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!response.ok) {
        const error = new Error('An error occurred while fetching admin forum posts');
        // Attach extra info to the error object.
        error.info = await response.json();
        error.status = response.status;
        throw error;
    }
    if(response.redirected == true) { // catching this and returning null as to not get console error
        return null;
    }

    return response.json();
}

export default function AdminForumThread() {

    const [thread, setThread] = React.useState([]); // forum post thread

    React.useEffect(() => { // TODO: this calls the api a lot. figure out a resolution.
        loader().then((response) => {
            // console.log(response);
            setThread(response);
        });
    }, [thread]); // TODO: can i use effect when button pressed?

    return (
        <>
            <div>
                {thread.map(( post, index ) => (
                    <div key={index}>
                        <span>{index}</span>
                        <span>{post.userId}</span>
                        <span>{post.body}</span>
                    </div>
                ))}
            </div>
        </>
    )
}