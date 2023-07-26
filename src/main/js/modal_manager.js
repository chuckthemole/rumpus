import Modal from 'react-modal';
Modal.setAppElement('#root');

// TODO: not using right now. see if we can insert in app.js

export let is_modal_active; // keeps track if user has a modal open. only allow one at a time.

/**
 * @returns true if a modal is active
 */
export function isModalActive() {
    if(is_modal_active !== undefined) {
        return is_modal_active;
    }
    return false;
}

/**
 * set modal open to true
 */
export function setModalActive() {
    is_modal_active = true;
}

/**
 * set modal open to false
 */
export function setModalInactive() {
    is_modal_active = false;
}