const React = require('react');
const ReactDOM = require('react-dom/client');

import useSWR from 'swr';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit, faEye, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons'
import { Common, CREATE_USER_PATH, DELETE_USER_PATH, GET_USERS_PATH, GET_USER_PATH, TEMPLATE_GET_USER_PATH } from "../rumpus";
import UpdateUser from './update';
import { ConvertEpochToDate } from '../../../../../common/src/main/js/common';

import {
    createBrowserRouter,
    RouterProvider,
    useLoaderData,
  } from "react-router-dom";

// const fetcher = (...args) => fetch(...args).then((res) => res.json());

// const fetcher = async url => {
//     const res = await fetch(url)

//     // If the status code is not in the range 200-299,
//     // we still try to parse and throw it.
//     if (!res.ok) {
//         const error = new Error('An error occurred while fetching the data.')
//         // Attach extra info to the error object.
//         error.info = await res.json()
//         error.status = res.status
//         throw error
//     }

//     return res.json()
// }

function User() {

    const data = useLoaderData();
    console.log(data);

    if (!data) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )

    return (
        <div className="container">
            <div className="columns">

                <div className="column is-9">

                    <nav className="breadcrumb" aria-label="breadcrumbs">
                        <ul>
                            <li><a href="../">Bulma</a></li>
                            <li><a href="../">Templates</a></li>
                            <li><a href="../">Examples</a></li>
                            <li className="is-active"><a href="#" aria-current="page">Admin</a></li>
                        </ul>
                    </nav>
                    
                    <section className="hero is-info welcome is-small">
                        <div className="hero-body">
                            <div className="container">
                                <h1 className="title">
                                    Hello, Admin.
                                </h1>
                                <h2 className="subtitle">
                                    <span>View user profile</span>
                                </h2>
                            </div>
                        </div>
                    </section>

                    <section className="info-tiles">
                        <div className="tile is-ancestor has-text-centered">
                            <div className="tile is-parent">
                                <article className="tile is-child box">
                                    <p className="title">Username</p>
                                    <p className="subtitle"><span>{data.userDetails.username}</span></p>
                                </article>
                            </div>
                            <div className="tile is-parent">
                                <article className="tile is-child box">
                                    <p className="title">Email</p>
                                    <p className="subtitle"><span>{data.email}</span></p>
                                </article>
                            </div>
                            <div className="tile is-parent">
                                <article className="tile is-child box">
                                    <p className="title">ID</p>
                                    <p className="subtitle"><span>{data.id}</span></p>
                                </article>
                            </div>
                            <div className="tile is-parent">
                                <article className="tile is-child box">
                                    <p className="title">Role</p>
                                    <p className="subtitle"></p>
                                </article>
                            </div>
                        </div>
                    </section>

                    <div className="columns">
                        <div className="column is-6">
                            <div className="card events-card">

                                <header className="card-header">
                                    <p className="card-header-title">
                                        Events
                                    </p>
                                    <a href="#" className="card-header-icon" aria-label="more options">
                                        <span className="icon">
                                            <i className="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </header>

                                <div className="card-table">
                                    <div className="content">
                                        <table className="table is-fullwidth is-striped">
                                            <tbody>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                                <tr>
                                                    <td width="5%"><i className="fa fa-bell-o"></i></td>
                                                    <td>TODO</td>
                                                    <td className="level-right"><a className="button is-small is-primary" href="#">Action</a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <footer className="card-footer">
                                    <a href="#" className="card-footer-item">View All</a>
                                </footer>
                            </div>
                        </div>

                        <div className="column is-6">

                            <div className="card">
                                <header className="card-header">
                                    <p className="card-header-title">
                                        Inventory Search
                                    </p>
                                    <a href="#" className="card-header-icon" aria-label="more options">
                                        <span className="icon">
                                            <i className="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </header>
                                <div className="card-content">
                                    <div className="content">
                                        <div className="control has-icons-left has-icons-right">
                                            <input className="input is-large" type="text" placeholder="" />
                                            <span className="icon is-medium is-left">
                                                <i className="fa fa-search"></i>
                                            </span>
                                            <span className="icon is-medium is-right">
                                                <i className="fa fa-check"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="card">
                                <header className="card-header">
                                    <p className="card-header-title">
                                        User Search
                                    </p>
                                    <a href="#" className="card-header-icon" aria-label="more options">
                                        <span className="icon">
                                            <i className="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </header>
                                <div className="card-content">
                                    <div className="content">
                                        <div className="control has-icons-left has-icons-right">
                                            <input className="input is-large" type="text" placeholder="" />
                                            <span className="icon is-medium is-left">
                                                <i className="fa fa-search"></i>
                                            </span>
                                            <span className="icon is-medium is-right">
                                                <i className="fa fa-check"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default User;