import * as RumpusCommon from "../../../../common/src/main/js/common.js";
import useSWR from 'swr';
const fetcher = (...args) => fetch(...args).then((res) => res.json());

export const Common = RumpusCommon;

// GET
export const GET_USERS_PATH = '/api/users';

// CRUD
export const CREATE_USER_PATH = '/api/user';
export const GET_USER_PATH = '/api/user/';
export const UPDATE_USER_PATH = '/api/update_user';
export const DELETE_USER_PATH = '/api/delete_user';

// TEMPLATES
export const TEMPLATE_GET_USER_PATH = '/get_user_by_id/';

// Modals
// Not using this anymore. Can prolly get rid of. using react-modal instead now. - chuck 2023/7/20
export const modals = [
    {name : "signupModal", container: document.getElementsByClassName("signup")[0], button: document.getElementsByClassName("signupBtn")[0]},
    {name : "loginModal", container: document.getElementsByClassName("login")[0], button: document.getElementsByClassName("loginBtn")[0]}
];

export function getUserById (id) {
    const { data, error, isLoading } = useSWR(
        `/api/user/${id}`,
        fetcher
    );

    return {
        user: data,
        isLoading,
        isError: error
    }
}

export function getCurrentUser() {
    const { data, error, isLoading } = useSWR(
        "/api/current_user",
        fetcher
    );

    return {
        user: data,
        isLoading,
        isError: error
    }
}

export function getCurrentUserAuthorities() {
    const { data, error, isLoading } = useSWR(
        "/api/current_user",
        fetcher
    );

    let authorities = [];
    if(!error && data !== undefined) {
        for(let i = 0; i < data.userDetails.authorities.length; i++) {
            authorities.push(data.userDetails.authorities[i].authority);
        }
    }

    return authorities;
}

export function isCurrentUserAuthenticated() {
    const { data, error, isLoading } = useSWR(
        "/api/is_authenticated",
        fetcher
    );

    return {
        isAuthenticated: data,
        isLoading,
        isError: error
    }
}