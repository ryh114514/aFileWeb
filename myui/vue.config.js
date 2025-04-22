const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 11451,
    proxy: {
      '/flash': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/ini': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/select': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/changePage': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    },
  }
})