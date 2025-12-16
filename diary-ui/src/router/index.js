// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

// 导入页面组件
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import Calendar from '../views/Calendar.vue'

const routes = [
  {
    path: '/',
    redirect: '/login' // 默认跳转到登录页
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true } // 标记需要认证
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar,
    meta: { requiresAuth: true } // 标记需要认证
  }
]

const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 History 模式
  routes
})

// 全局前置守卫：检查路由元信息实现简单权限控制
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated') // 检查本地存储是否有认证标志
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login') // 如果目标路由需要认证但未认证，则重定向到登录页
  } else {
    next() // 否则允许导航
  }
})

export default router