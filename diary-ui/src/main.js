// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// 1. 引入 TDesign 组件库及其样式
import TDesign from 'tdesign-vue-next';
import 'tdesign-vue-next/es/style/index.css';

const app = createApp(App)

// 2. 使用 TDesign 插件 - 这一步至关重要！
//    它会全局注册所有 TDesign 组件 (如 t-button, t-form 等)
app.use(TDesign);

// 3. 使用 Vue Router
app.use(router)

// 4. 挂载应用到 #app 元素
app.mount('#app')