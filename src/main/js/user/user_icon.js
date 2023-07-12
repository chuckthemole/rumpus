const React = require('react');
import useSWR from 'swr';
import { Tooltip as ReactTooltip } from "react-tooltip";

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export default function UserIcon() {
    const { data, error } = useSWR(
        "/api/current_user",
        fetcher
    );

    if (error) return <p>Error</p>;
    if (!data) return <p>Loading</p>;
   
    return (
        <>
            <a
                data-tooltip-id="my-tooltip"
                data-tooltip-html={
                    "Username: " + data.username +
                    "<br />Email: " + data.email
                }
                data-tooltip-place="bottom"
            >
                ◕‿‿◕
            </a>
            <ReactTooltip id="my-tooltip" />
        </>
    )
}