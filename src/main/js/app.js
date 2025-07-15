import * as React from 'react';
import { Outlet } from 'react-router-dom';

// import Footer from './common/footer';
// import Header from './common/header';
// import Section from './common/section';
import { Footer, Header, Section, RumpusQuillForm, RumpusQuill } from '@rumpushub/common-react'
// import RumpusQuillForm from './common/rumpus_quill_form';
// import RumpusQuill from './common/rumpus_quill';
import { AuthRoot } from '@rumpushub/common-react';

export default function App() {

    console.log('rumpus React version:', React.version);

    return (
        <>
            <AuthRoot>
                <Header header_path={'/view/header'} />
                <div className='columns is-centered'>

                    {/* <RumpusQuillForm quill={<RumpusQuill />} /> */}


                    <div className='column'></div>
                    <div className='column is-three-fifths'>
                        <Outlet />
                    </div>
                    <div className='column'></div>
                </div>
                <Footer footer_path={"/view/footer"} />
            </AuthRoot>
        </>
    )
}