import * as React from 'react';
import { Outlet } from 'react-router-dom';

import Footer from './common/footer';
import Header from './common/header';
import { CREATE_USER_PATH, getCurrentUserAuthorities, isCurrentUserAuthenticated } from './rumpus';

export default function App() {

    return (
        <>
            <Header header_path={'/view/header'} />
            <div className='columns is-centered'>
                <div className='column'></div>
                <div className='column is-three-fifths'>
                    <Outlet />
                </div>
                <div className='column'></div>
            </div>
            <Footer footer_path={"/view/footer"} />
        </>
    )
}