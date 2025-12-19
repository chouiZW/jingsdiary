<template>
  <t-layout class="layout-container">
    <t-header class="header">
      <div class="header-content">
        <h2>日历管理系统</h2>
        <div>
          <span>欢迎, {{ currentUser }}!</span>
          <t-button theme="default" variant="outline" @click="handleLogout" style="margin-left: 16px;">退出</t-button>
        </div>
      </div>
    </t-header>

    <t-layout>
      <t-aside class="sidebar">
        <t-menu :value="activeMenu" @change="handleMenuChange" theme="light">
          <t-menu-item value="home">
            <template #icon><t-icon name="home" /></template>
            首页
          </t-menu-item>
          <t-menu-item value="calendar">
            <template #icon><t-icon name="calendar" /></template>
             我的日历
          </t-menu-item>
        </t-menu>
      </t-aside>

      <t-content class="content">
        <slot />
      </t-content>
    </t-layout>
  </t-layout>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'

const router = useRouter()
const route = useRoute()

const currentUser = computed(() => localStorage.getItem('currentUser') || '访客')
const activeMenu = ref('home')

const updateActiveMenu = () => {
  if (route.name === 'Home') activeMenu.value = 'home'
  else if (route.name === 'Calendar') activeMenu.value = 'calendar'
}

watch(() => route.name, updateActiveMenu, { immediate: true })

const handleMenuChange = (value) => {
  activeMenu.value = value
  if (value === 'home') router.push('/home')
  else if (value === 'calendar') router.push('/calendar')
}

const handleLogout = () => {
  localStorage.removeItem('isAuthenticated')
  localStorage.removeItem('currentUser')
  localStorage.removeItem('authToken')
  MessagePlugin.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  background-color: #0052d9;
  color: white;
  padding: 0 24px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.sidebar {
  width: 240px;
  background-color: #f6f8fa;
}

.content {
  padding: 24px;
  overflow-y: auto;
}
</style>
