const React = require('react');
import { GET_USER_PATH } from "../rumpus";
import { Common } from "../rumpus";

export async function loader_by_id({id}) {
    const requestOptions = {
        method: Common.GET,
        headers: {
            'Accept': 'application/json',
        },
    };
    const response = await fetch(GET_USER_PATH + id, requestOptions);
    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    
    if (!response.ok) {
        const error = new Error('An error occurred while fetching user with the id: ' + id);
        // Attach extra info to the error object.
        error.info = await response.json();
        error.status = response.status;
        throw error;
    }

    return response.json();
}