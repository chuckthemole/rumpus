import * as RumpusCommon from "../../../../common/src/main/js/common.js";

export const Common = RumpusCommon;

// GET
export const GET_USERS_PATH = '/api/users';

// CRUD
export const CREATE_USER_PATH = '/api/user';
export const GET_USER_PATH = '/api/get_user_by_id/';
export const UPDATE_USER_PATH = '/api/update_user';
export const DELETE_USER_PATH = '/api/delete_user';

// Modals
export const modals = [
    {name : "signupModal", container: document.getElementsByClassName("signup")[0], button: document.getElementsByClassName("signupBtn")[0]},
    {name : "loginModal", container: document.getElementsByClassName("login")[0], button: document.getElementsByClassName("loginBtn")[0]}
];