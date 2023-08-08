const React = require('react');
import Quill from 'quill/core';
import Toolbar from "quill/modules/toolbar";
import Snow from "quill/themes/snow";
import Bold from "quill/formats/bold";
import Italic from "quill/formats/italic";
import Header from "quill/formats/header";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

// function Register() {
//     Quill.register({
//             'modules/toolbar': Toolbar,
//             'themes/snow': Snow,
//             'formats/bold': Bold,
//             'formats/italic': Italic,
//             'formats/header': Header
//     });
// }

export default function RumpusQuill() {
    // Register();
    const [value, setValue] = React.useState('');

    let quill = React.createElement(

        // type
        'div',

        // props
        {
            style: {
                background: "white"
            }
        },

        // children
        <ReactQuill
            theme='snow'
            value={value}
            onChange={setValue}
            placeholder='Compose an epic...'
            // modules={
            //     toolbar=[
            //         [{ header: [1, 2, false] }],
            //         ['bold', 'italic']
            //     ]
            // }
        />
    );

    return (
        <>
            {quill}
            {/* {fragment}
            {new Quill(editor_name, {
                modules: {
                    toolbar: [
                        [{ header: [1, 2, false] }],
                        ['bold', 'italic']
                    ]
                },  
                placeholder: 'Compose an epic...',
                theme: 'snow'  // or 'bubble'
            })} */}
        </>
    );
}