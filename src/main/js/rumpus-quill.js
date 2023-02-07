// const React = require('react');
// const ReactDOM = require('react-dom/client');
// const client = require('./client');

import Quill from 'quill/core';
import Toolbar from 'quill/modules/toolbar';
import Snow from 'quill/themes/snow';
import Bold from 'quill/formats/bold';
import Italic from 'quill/formats/italic';
import Header from 'quill/formats/header';


// Quill.register({
//     'modules/toolbar': Toolbar,
//     'themes/snow': Snow,
//     'formats/bold': Bold,
//     'formats/italic': Italic,
//     'formats/header': Header
// });

// class RumpusQuill extends React.Component {

// 	constructor(props) {
// 		super(props);

//         let Inline = Quill.import('blots/inline');
//         let Block = Quill.import('blots/block');
//         let BlockEmbed = Quill.import('blots/block/embed');

//         class BoldBlot extends Inline { }
//         BoldBlot.blotName = 'bold';
//         BoldBlot.tagName = 'strong';

//         class ItalicBlot extends Inline { }
//         ItalicBlot.blotName = 'italic';
//         ItalicBlot.tagName = 'em';

//         class LinkBlot extends Inline {
//         static create(url) {
//             let node = super.create();
//             node.setAttribute('href', url);
//             node.setAttribute('target', '_blank');
//             return node;
//         }
        
//         static formats(node) {
//             return node.getAttribute('href');
//         }
//         }
//         LinkBlot.blotName = 'link';
//         LinkBlot.tagName = 'a';

//         class BlockquoteBlot extends Block { }
//         BlockquoteBlot.blotName = 'blockquote';
//         BlockquoteBlot.tagName = 'blockquote';

//         class HeaderBlot extends Block {
//         static formats(node) {
//             return HeaderBlot.tagName.indexOf(node.tagName) + 1;
//         }
//         }
//         HeaderBlot.blotName = 'header';
//         HeaderBlot.tagName = ['H1', 'H2'];

//         class DividerBlot extends BlockEmbed { }
//         DividerBlot.blotName = 'divider';
//         DividerBlot.tagName = 'hr';

//         class ImageBlot extends BlockEmbed {
//         static create(value) {
//             let node = super.create();
//             node.setAttribute('alt', value.alt);
//             node.setAttribute('src', value.url);
//             return node;
//         }
        
//         static value(node) {
//             return {
//             alt: node.getAttribute('alt'),
//             url: node.getAttribute('src')
//             };
//         }
//         }
//         ImageBlot.blotName = 'image';
//         ImageBlot.tagName = 'img';

//         class VideoBlot extends BlockEmbed {
//         static create(url) {
//             let node = super.create();
//             node.setAttribute('src', url);
//             node.setAttribute('frameborder', '0');
//             node.setAttribute('allowfullscreen', true);
//             return node;
//         }
        
//         static formats(node) {
//             let format = {};
//             if (node.hasAttribute('height')) {
//             format.height = node.getAttribute('height');
//             }
//             if (node.hasAttribute('width')) {
//             format.width = node.getAttribute('width');
//             }
//             return format;
//         }
        
//         static value(node) {
//             return node.getAttribute('src');
//         }
        
//         format(name, value) {
//             if (name === 'height' || name === 'width') {
//             if (value) {
//                 this.domNode.setAttribute(name, value);
//             } else {
//                 this.domNode.removeAttribute(name, value);
//             }
//             } else {
//             super.format(name, value);
//             }
//         }
//         }
//         VideoBlot.blotName = 'video';
//         VideoBlot.tagName = 'iframe';

//         class TweetBlot extends BlockEmbed {
//         static create(id) {
//             let node = super.create();
//             node.dataset.id = id;
//             twttr.widgets.createTweet(id, node);
//             return node;
//         }
        
//         static value(domNode) {
//             return domNode.dataset.id;
//         }
//         }
//         TweetBlot.blotName = 'tweet';
//         TweetBlot.tagName = 'div';
//         TweetBlot.className = 'tweet';

//         Quill.register(BoldBlot);
//         Quill.register(ItalicBlot);
//         Quill.register(LinkBlot);
//         Quill.register(BlockquoteBlot);
//         Quill.register(HeaderBlot);
//         Quill.register(DividerBlot);
//         Quill.register(ImageBlot);
//         Quill.register(TweetBlot);
//         Quill.register(VideoBlot);

//         let quill = new Quill('#editor-container');
//         quill.addContainer($("#tooltip-controls").get(0));
//         quill.addContainer($("#sidebar-controls").get(0));
//         quill.on(Quill.events.EDITOR_CHANGE, function(eventType, range) {
//         if (eventType !== Quill.events.SELECTION_CHANGE) return;
//         if (range == null) return;
//         if (range.length === 0) {
//             $('#tooltip-controls').hide();
//             let [block, offset] = quill.scroll.descendant(Block, range.index);
//             if (block != null && block.domNode.firstChild instanceof HTMLBRElement) {
//             let lineBounds = quill.getBounds(range);
//             $('#sidebar-controls').removeClass('active').show().css({
//                 left: lineBounds.left - 50,
//                 top: lineBounds.top - 2
//             });
//             } else {
//             $('#tooltip-controls, #sidebar-controls').hide();
//             $('#sidebar-controls').removeClass('active');
//             }
//         } else {
//             $('#sidebar-controls, #sidebar-controls').hide();
//             $('#sidebar-controls').removeClass('active');
//             let rangeBounds = quill.getBounds(range);
//             $('#tooltip-controls').show().css({
//             left: rangeBounds.left + rangeBounds.width/2 - $('#tooltip-controls').outerWidth()/2,
//             top: rangeBounds.bottom + 10
//             });
//         }
//         });

//         $('#bold-button').click(function() {
//         quill.format('bold', true);
//         });

//         $('#italic-button').click(function() {
//         let active = $(this).hasClass('active');
//         quill.format('italic', true);
//         });

//         $('#link-button').click(function() {
//         let value = prompt('Enter link URL');
//         quill.format('link', value);
//         });

//         $('#blockquote-button').click(function() {
//         console.log('blockquote');
//         quill.format('blockquote', true);
//         });

//         $('#header-1-button').click(function() {
//         quill.format('header', 1);
//         });

//         $('#header-2-button').click(function() {
//         quill.format('header', 2);
//         });

//         $('#divider-button').click(function() {
//         let range = quill.getSelection(true);
//         quill.insertEmbed(range.index, 'divider', true, Quill.sources.USER);
//         quill.setSelection(range.index + 1, Quill.sources.SILENT);
//         $('#sidebar-controls').hide();
//         });

//         $('#image-button').click(function() {
//         let range = quill.getSelection(true);
//         quill.insertEmbed(range.index, 'image', {
//             alt: 'Quill Cloud',
//             url: 'https://quilljs.com/0.20/assets/images/cloud.png'
//         }, Quill.sources.USER);
//         quill.setSelection(range.index + 1, Quill.sources.SILENT);
//         $('#sidebar-controls').hide();
//         });

//         $('#video-button').click(function() {
//         let range = quill.getSelection(true);
//         let url = 'https://www.youtube.com/embed/QHH3iSeDBLo?showinfo=0';
//         quill.insertEmbed(range.index, 'video', url, Quill.sources.USER);
//         quill.formatText(range.index + 1, 1, { height: '170', width: '400' });
//         quill.setSelection(range.index + 1, Quill.sources.SILENT);
//         $('#sidebar-controls').hide();
//         });

//         $('#tweet-button').click(function() {
//         let range = quill.getSelection(true);
//         let id = '464454167226904576';
//         quill.insertEmbed(range.index, 'tweet', id, Quill.sources.USER);
//         quill.setSelection(range.index + 1, Quill.sources.SILENT);
//         $('#sidebar-controls').hide();
//         });

//         $('#show-controls').click(function() {
//         $('#sidebar-controls').toggleClass('active');
//         quill.focus();
//         });

// 		this.state = quill;
// 	}

// 	render() {
// 		return (
//             <div>
//                 <div id="tooltip-controls">
//                     <button id="bold-button"><i class="fa fa-bold"></i></button>
//                     <button id="italic-button"><i class="fa fa-italic"></i></button>
//                     <button id="link-button"><i class="fa fa-link"></i></button>
//                     <button id="blockquote-button"><i class="fa fa-quote-right"></i></button>
//                     <button id="header-1-button"><i class="fa fa-header"><sub>1</sub></i></button>
//                     <button id="header-2-button"><i class="fa fa-header"><sub>2</sub></i></button>
//                 </div>
//                 <div id="sidebar-controls">
//                     <button id="show-controls"><i class="fa fa-plus"></i></button>
//                     <span class="controls">
//                         <button id="image-button"><i class="fa fa-camera"></i></button>
//                         <button id="video-button"><i class="fa fa-play"></i></button>
//                         <button id="tweet-button"><i class="fa fa-twitter"></i></button>
//                         <button id="divider-button"><i class="fa fa-minus"></i></button>
//                     </span>
//                 </div>
//                 <div id="editor-container">
//                 <p>Tell your story...</p>
//                 </div>
//             </div>
//         )
// 	}
// }

function Populate(editorName, init) {
    const quill_editors = document.getElementsByClassName(editorName);
    var editorCount = 0;
    Array.prototype.forEach.call(quill_editors, function(element) {
        const fragment = document.createDocumentFragment();
        const div = document.createElement('div');
        div.id = "editor" + editorCount;
        fragment.appendChild(div);
        element.appendChild(fragment);
        init(editorCount);
        editorCount++;
    });
}

function Register() {
    Quill.register({
        'modules/toolbar': Toolbar,
        'themes/snow': Snow,
        'formats/bold': Bold,
        'formats/italic': Italic,
        'formats/header': Header
    });
}

function Init(index) {
    var quill = new Quill('#editor' + index, {
        modules: {
            toolbar: [
                [{ header: [1, 2, false] }],
                ['bold', 'italic']
            ]
        },  
        placeholder: 'Compose an epic...',
        theme: 'snow'  // or 'bubble'
    });
}

export default function RumpusQuill() {
    Register();
    Populate("quill-editor", Init);
    // const quill_editors = document.getElementsByClassName('quill-editor');
    // var editorCount = 0;
    // Array.prototype.forEach.call(quill_editors, function(element) {
    //     const fragment = document.createDocumentFragment();
    //     const div = document.createElement('div');
    //     div.id = "editor" + editorCount;
    //     fragment.appendChild(div);
    //     element.appendChild(fragment);
    //     Init(editorCount);
    //     editorCount++;
    // });
}

// Main
RumpusQuill();