var path = require('path');
const webpack = require('webpack');
require('dotenv').config();

console.log('ENV VALUE:', process.env.REACT_APP_API_BASE_URL);

module.exports = {
    mode: 'development',
    context: path.resolve(__dirname, '../../src/main/js'),
    entry: {
        app: './index.js',
        // quill: './common/rumpus_quill.js'
        quill: '@rumpushub/common-react/dist/components/rumpus_quill'
    },
    devtool: 'source-map',
    cache: true,
    output: {
        path: path.resolve(__dirname, '../../src/main/resources/static/js'),
        filename: '[name].bundle.js'
    },
    resolve: {
        // TODO: may need to not alias for production
        alias: {
            'Parchment': path.resolve(__dirname, '../../node_modules/parchment/src/parchment.ts'),
            'quill$': path.resolve(__dirname, '../../node_modules/quill/quill.js'),
            'react': path.resolve(__dirname, '../../node_modules/react'),
            'react-dom': path.resolve(__dirname, '../../node_modules/react-dom'),
            'react-router-dom': path.resolve(__dirname, '../../node_modules/react-router-dom')
        },
        extensions: ['.mjs', '.js', '.ts', '.svg'],
        symlinks: true
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env.REACT_APP_API_BASE_URL': JSON.stringify(process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080')
        })
    ],
    module: {
        rules: [
            {
                test: path.join(path.resolve(__dirname, '../../'), '.') && /\.js$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }, {
                test: path.join(path.resolve(__dirname, '../../'), '.') && /\.(tsx|ts)$/,
                loader: 'babel-loader'
            }, {
                test: path.join(path.resolve(__dirname, '../../'), '.') && /\.svg$/,
                use: [{
                    loader: 'html-loader',
                    options: {
                        minimize: true
                    }
                }]
            }, {
                test: path.join(path.resolve(__dirname, '../../'), '.') && /\.mjs$/,
                include: /(node_modules)/,
                type: 'javascript/auto'
            }, {
                test: path.join(path.resolve(__dirname, '../../'), '.') && /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ]
    }
}