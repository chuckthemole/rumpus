import * as React from "react";
import * as ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import App from './app';
import ErrorBoundary from './error';
import ErrorPage from './error_page';
import Users, {delete_user, loader as usersLoader} from './user/users';
import User, {loader as userLoader} from './user/user';
import Logout, { loader } from "./logout";

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
            },
            {
                path: 'deleteUser/:username',
                // element: <Users />,
                action: async ({ request, params }) => {
                    // console.log(params);
                    // console.log(request.formData());
                    // console.log(request.url);
                    delete_user(params.username);
                    return request;
                },
                // loader: usersLoader,
                // errorElement: <ErrorPage />
            }
        ],
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);