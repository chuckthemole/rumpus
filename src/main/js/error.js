const React = require('react');

class ErrorBoundary extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            hasError: false,
            error: { message: '', stack: '' },
            info: { componentStack: '' }
        };
    }

    static getDerivedStateFromError = error => {
        return { hasError: true };
    };

    componentDidCatch = (error, info) => {
        this.setState({ error, info });
    };

    render() {
        if (this.state.hasError) {
            // You can render any custom fallback UI
            return this.props.fallback;
        }
        return this.props.children;
    }
}

export default ErrorBoundary;