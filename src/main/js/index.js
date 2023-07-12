import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
  BrowserRouter
} from "react-router-dom";

import App from './app';
import ErrorBoundary from './error';
import ErrorPage from './error_page';
import Users, {loader as usersLoader} from './user/users';
import User, {loader as userLoader} from './user/user';
import Header from "./header";
import Footer from "./footer";
import Logout from "./logout";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: 'error',
                element: <h1>Something went wrong! </h1>,
            },
            {
                path: 'admin',
                element: <Users />,
                loader: usersLoader,
                errorElement: <ErrorPage />
            },
            {
                path: 'user/:userId',
                element:
                    <ErrorBoundary fallback={<ErrorPage />}>
                        <User />
                    </ErrorBoundary>,
                loader: userLoader,
                errorElement: <ErrorPage />,
            },
            {
                path: 'logout',
                element: <Logout />,
            }
        ],
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);