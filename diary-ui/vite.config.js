// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()], // 添加 Vue 插件
  resolve: {
    alias: {
      // 可选：为路径设置别名，方便导入
      '@': '/src'
    }
  },
  server: {
    port: 3000 // 设置开发服务器端口
  }
})