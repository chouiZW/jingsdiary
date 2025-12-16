<!-- src/views/Home.vue -->
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
        <div class="page-content">
          <h1>欢迎来到首页</h1>
          <p>这是应用的核心区域。您可以从左侧菜单导航到其他功能。</p>
        </div>
      </t-content>
    </t-layout>
  </t-layout>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'

export default {
  name: 'HomeView',
  setup() {
    const router = useRouter()
    const route = useRoute()

    const currentUser = computed(() => {
      return localStorage.getItem('currentUser') || '访客'
    })

    const activeMenu = ref('home') // 默认激活首页菜单项

    // 根据当前路由更新激活的菜单项
    const updateActiveMenu = () => {
       if (route.name === 'Home') {
         activeMenu.value = 'home'
       } else if (route.name === 'Calendar') {
         activeMenu.value = 'calendar'
       }
    }

    // 在组件挂载和路由变化时更新菜单
    onMounted(updateActiveMenu)
    // 注意：对于简单的同步更新，可以监听 route 对象的变化
    // 但在更复杂的情况下，可能需要 watch 或者在 router.beforeEach 中处理


    const handleMenuChange = (value) => {
      activeMenu.value = value
      if (value === 'home') {
        router.push('/home')
      } else if (value === 'calendar') {
        router.push('/calendar')
      }
    }

    const handleLogout = () => {
      localStorage.removeItem('isAuthenticated')
      localStorage.removeItem('currentUser')
      MessagePlugin.success('已退出登录')
      router.push('/login')
    }

    return {
      currentUser,
      activeMenu,
      handleMenuChange,
      handleLogout
    }
  }
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

.page-content h1 {
  margin-bottom: 16px;
}
</style>