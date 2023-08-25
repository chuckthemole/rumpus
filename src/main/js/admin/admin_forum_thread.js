const React = require('react');
import { faHeart, faReply, faRetweet } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
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

    // look here for bulma front end help https://bulma.io/documentation/layout/media-object/
    return (
        <>
            <div>
                {/* {thread.map(( post, index ) => (
                    <div key={index}>
                        <span>{index}</span>
                        <span>{post.userId}</span>
                        <span>{post.body}</span>
                    </div>
                ))} */}

                {thread.map(( post, index ) => (
                    <article key={index} className="media m-4">
                        <figure className="media-left">
                            <p className="image is-64x64">
                            <img src="https://bulma.io/images/placeholders/128x128.png" />
                            </p>
                        </figure>
                        <div className="media-content">
                            <div className="content">
                                <p>
                                    <strong>#{index}</strong>
                                    <br />
                                    <strong>{post.userId}</strong> <small>@TODOusertag</small> <small>TODO: post time</small>
                                    <br />
                                    <div dangerouslySetInnerHTML={{ __html: post.body }} />
                                </p>
                            </div>
                            <nav className="level is-mobile">
                                <div className="level-left">
                                    <a className="level-item">
                                    {/* <span className="icon is-small"><i className="fas fa-reply"></i></span> */}
                                    <span className="icon is-small"><FontAwesomeIcon icon={faReply} color='blue' /></span>
                                    </a>
                                    <a className="level-item">
                                    {/* <span className="icon is-small"><i className="fas fa-retweet"></i></span> */}
                                    <span className="icon is-small"><FontAwesomeIcon icon={faRetweet} color='blue' /></span>
                                    </a>
                                    <a className="level-item">
                                    {/* <span className="icon is-small"><i className="fas fa-heart"></i></span> */}
                                    <span className="icon is-small"><FontAwesomeIcon icon={faHeart} color='red' /></span>
                                    </a>
                                </div>
                            </nav>
                        </div>
                        <div className="media-right">
                            <button className="delete"></button>
                        </div>
                    </article>
                ))}
            </div>
        </>
    )
}