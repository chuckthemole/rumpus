const React = require('react');
import { createApiClient } from "@rumpushub/common-react";
import { setApi } from "@rumpushub/common-react";

export default function Api() {
    const api = createApiClient(process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080');
    setApi(api); // inject the instance early
}