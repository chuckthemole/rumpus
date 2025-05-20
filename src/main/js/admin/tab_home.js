const React = require('react');
const ReactDOM = require('react-dom/client');

import RumpusQuill from '@rumpushub/common-react/dist/components/rumpus_quill';
import { load_current_user } from '../user/user_loader';
import AdminForumThread from './admin_forum_thread';

export default function AdminHome() {

    const editor_ref = React.useRef(null);
    const [value, setValue] = React.useState('');
    const [quill, setQuill] = React.useState(<RumpusQuill value={value} setValue={setValue} editor_ref={editor_ref} />);

    async function handleSubmit(e) {
        e.preventDefault();
        const forumPost = {};
        const user = await load_current_user();
        forumPost['userId'] = user.username;
        forumPost['body'] = value;
        editor_ref.current.getEditor().setContents(''); // clear the editor
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
            <AdminForumThread />
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