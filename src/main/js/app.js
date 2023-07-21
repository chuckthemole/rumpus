const client = require('./client');

import * as React from 'react';
import { Outlet } from 'react-router-dom';

import Footer from './footer';
import Header from './header';

export default function App() {
    return (
        <>
            <Header />
            <Outlet />
            <Footer />
        </>
    )
}