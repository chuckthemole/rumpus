const React = require('react');
const ReactDOM = require('react-dom/client');

import RumpusQuill, {getQuillContents} from '../common/rumpus-quill';
import { load_current_user } from '../user/user_loader';

export default function AdminHome() {

    const [quill, setQuill] = React.useState(<RumpusQuill />);

    async function handleSubmit(e) {
        e.preventDefault();
        const forumPost = {};
        const user = await load_current_user();
        forumPost['userId'] = user.username;
        forumPost['body'] = getQuillContents();
        await onCreate(forumPost);
    }

    async function onCreate(forumPost) {
        const requestOptions = {
            method: 'POST',
            redirect: "follow",
            entity: forumPost,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(forumPost)
        };
        return fetch('/api/admin/forum_post', requestOptions);
	}

    return (
        <>
            <div><span>Admin Page</span></div>

            <form method='post' onSubmit={handleSubmit} className="box">
                <div className='field'>{quill}</div>
                <div className="field">
                    <button id="adminForumPostSubmit" type="submit" value="ForumPost" className="button is-success">
                        Submit
                    </button>
                </div>
            </form>
        </>
    )
}