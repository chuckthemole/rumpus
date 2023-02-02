var path = require('path');
// const ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = [
    {
        entry: './src/main/js/app.js',
        devtool: 'sourcemaps',
        cache: true,
        mode: 'development',
        output: {
            path: __dirname,
            filename: './src/main/resources/static/js/bundle.js'
        },
        module: {
            rules: [
                {
                    test: path.join(__dirname, '.'),
                    exclude: /(node_modules)/,
                    use: [{
                        loader: 'babel-loader',
                        options: {
                            presets: ["@babel/preset-env", "@babel/preset-react"]
                        }
                    }]
                }
            ]
        }
    },
    // {
    //     entry: './src/main/js/rumpus-bulma.js',
    //     output: {
    //         path: __dirname,
    //         filename: './src/main/resources/static/built/js/bulma-bundle.js'
    //     },
    //     module: {
    //         rules: [{
    //             test: /\.scss$/,
    //             use: ExtractTextPlugin.extract({fallback: 'style-loader', use: ['css-loader','sass-loader']})
    //         }]
    //     },
    //     plugins: [
    //       new ExtractTextPlugin('./src/main/resources/static/css/rumpus-styles.css'),
    //     ]
    // }
];