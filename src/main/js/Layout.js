const React = require('react');

import Header from './header';
import Footer from './footer';

export default function Layout(props) {
        return(
            <>
                <Header />
                { props.children }
                <div className='columns is-centered'><div className='column is-half'><Footer /></div></div>
            </>
        );
}