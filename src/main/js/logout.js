import * as React from 'react';
import { Link } from 'react-router-dom';

export async function loader({ params }) {
    return fetch(`/logout`);
}

export default function Logout() {
    return (
        <div id='logout' className="is-flex is-justify-content-center is-align-items-center">
            <div className="has-text-centered">
                <h1 className="is-size-1 has-text-weight-bold has-text-primary">Success logging out</h1>
                <Link to={`/`} className="button is-primary">Go Home</Link>
            </div>
        </div>
    );
}