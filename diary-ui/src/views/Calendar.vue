<!-- src/views/Calendar.vue -->
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
        <div class="calendar-container">
          <div class="calendar-header">
            <h2 class="calendar-title">我的日历</h2>
            <t-date-picker
              v-model="selectedMonth"
              mode="month"
              @change="handleMonthChange"
              format="YYYY-MM"
              allow-input
            />
          </div>
          <t-calendar
            :value="selectedDate"
            :cell-click="handleCellClick"
            :controller-config="calendarControllerConfig"
            :mode="'month'"
          />
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
  name: 'CalendarView',
  setup() {
    const router = useRouter()
    const route = useRoute()

    const currentUser = computed(() => {
      return localStorage.getItem('currentUser') || '访客'
    })

    const activeMenu = ref('calendar') // 默认激活日历菜单项

    // 根据当前路由更新激活的菜单项
    const updateActiveMenu = () => {
       if (route.name === 'Home') {
         activeMenu.value = 'home'
       } else if (route.name === 'Calendar') {
         activeMenu.value = 'calendar'
       }
    }

    onMounted(updateActiveMenu)


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


    // --- 日历相关 ---
    const selectedDate = ref(new Date()) // 当前选中的具体日期
    const selectedMonth = ref(new Date()) // 当前显示的月份 (DatePicker 控制)
    const calendarControllerConfig = ref({ visible: true })

    const handleCellClick = (date) => {
      selectedDate.value = date
      console.log('点击了日期:', date)
      MessagePlugin.info(`您点击了 ${date.toLocaleDateString()}`)
    }

    const handleMonthChange = (newMonth) => {
      selectedMonth.value = newMonth
      // 注意：TDesign Calendar 的 value 属性通常控制选中日期，
      // 而 mode='month' 时，它也会影响显示的月份。
      // 为了让 DatePicker 和 Calendar 显示月份保持同步，
      // 我们让 selectedDate 跟随 selectedMonth 的变化而变化到该月的第一天。
      // 这样做是为了确保 Calendar 总是显示 DatePicker 选择的那个月份。
      // 如果只想改变显示月份而不改变选中日期，可以只改变 Calendar 的 key 来强制刷新。
      // 但此处为了简化，让 selectedDate 指向新月份的第一天。
      // 更精确的做法是使用 Calendar 的 panel 属性或者通过 key 刷新。
      // 这里采用简单直接的方式。
      selectedDate.value = new Date(newMonth.getFullYear(), newMonth.getMonth(), 1);
    }

    return {
      currentUser,
      activeMenu,
      handleMenuChange,
      handleLogout,
      selectedDate,
      selectedMonth,
      calendarControllerConfig,
      handleCellClick,
      handleMonthChange
    }
  }
}
</script>

<style scoped>
/* 复用 Home.vue 的样式 */
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

/* 特定于日历页面的样式 */
.calendar-container {
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.calendar-title {
  font-size: 20px;
  font-weight: bold;
}
</style>