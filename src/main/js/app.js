import * as React from 'react';
import { Outlet } from 'react-router-dom';

import Footer from './common/footer';
import Header from './common/header';
import { CREATE_USER_PATH, getCurrentUserAuthorities, isCurrentUserAuthenticated } from './rumpus';

export default function App() {
    return (
        <>
            <Header user_path={'/api/current_user'} current_user_authorities={getCurrentUserAuthorities()} is_current_user_authenticated={isCurrentUserAuthenticated()} create_path={CREATE_USER_PATH}/>
            <Outlet />
            <Footer footer_path={"/view/footer"}/>
        </>
    )
}