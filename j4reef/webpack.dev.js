const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const BUILD_DIR = path.resolve(__dirname, 'src/main/resources/static/dist');

module.exports = merge(common, {
  devtool: 'inline-source-map',
  mode: 'development',
  devServer: {
    liveReload: true,
    historyApiFallback: true,
    proxy: [{ context: ["/api/**","/libs/**","/css/**","/img/**'"],target: 'http://localhost:8090' }]
  },
  plugins: [
	    new HtmlWebpackPlugin({
	    	title: 'Toasthub React',
	    	template: './src/main/js/index.html',
	    	filename: BUILD_DIR + '/index.html'
	    }),
	  ],
  output: {
    path: BUILD_DIR,
    filename: 'bundle.js',
    publicPath: '/'
  },
});
