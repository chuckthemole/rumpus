import * as React from 'react';
import { Outlet } from 'react-router-dom';

import Footer from './common/footer';
import Header from './common/header';
import Section from './common/section';
import { CREATE_USER_PATH, getCurrentUserAuthorities, isCurrentUserAuthenticated } from './rumpus';
import RumpusQuillForm from './common/rumpus_quill_form';
import RumpusQuill from './common/rumpus_quill';

export default function App() {

    return (
        <>
            <Header header_path={'/view/header'} />
            <div className='columns is-centered'>

                {/* <Section section_path={'/view/section/TestSection'} /> */}

                <RumpusQuillForm quill={<RumpusQuill />} />

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