<!-- src/views/Calendar.vue -->
<template>
  <MainLayout>
    <div class="calendar-container">
      <div class="calendar-header">
        <h2 class="calendar-title">我的日历</h2>
        <!-- Template: 插入到 $PLACEHOLDER$ 位置 -->
        <div class="calendar-actions" style="display:flex;align-items:center;gap:12px;margin-left:16px;">
          <t-button theme="primary" @click="openWeightDialog">
             <template #icon><AddIcon /></template>
            录入体重</t-button>

          <t-dialog v-model:visible="weightDialogVisible" header="录入体重" width="420px">
            <div style="display:flex;flex-direction:column;gap:12px;padding:8px 0;">
              <t-date-picker
                v-model="weightFormDate"
                mode="date"
                format="YYYY-MM-DD"
                :disabled-date="disableOutsideMonth"
                allow-input
              />
              <t-input
                v-model="weightFormWeight"
                type="number"
                placeholder="输入体重 (kg)"
              />
            </div>
            <template #footer>
              <t-button @click="weightDialogVisible = false">取消</t-button>
              <t-button theme="primary" @click="saveWeight">保存</t-button>
            </template>
          </t-dialog>
        </div>
      </div>
      <t-calendar
        :value="selectedDate"
        :cell-click="handleCellClick"
        :controller-config="calendarControllerConfig"
        :mode="'month'"
      >
        <template #cell="{ date }">
          <div class="cell-content">
            <div class="cell-day">{{ date.getDate() }}</div>
            <div v-if="entries[formatDayKey(date)]" class="cell-meta">
              <div v-show="entries[formatDayKey(date)] && entries[formatDayKey(date)].weight !== null && entries[formatDayKey(date)].weight !== ''" class="weight">{{ entries[formatDayKey(date)].weight }}kg</div>
              <div v-show="entries[formatDayKey(date)] && entries[formatDayKey(date)].weightDiff !== null" class="weight-diff">{{ entries[formatDayKey(date)].weightDiff > 0 ? '+' : '' }}{{ entries[formatDayKey(date)].weightDiff  }}kg</div>
              <div class="mark">{{ entries[formatDayKey(date)].mark }}</div>
            </div>
          </div>
        </template>
      </t-calendar>
    </div>
  </MainLayout>
</template>

<script>
import { ref, onMounted } from 'vue'
import MainLayout from '@/layouts/MainLayout.vue'
import { MessagePlugin } from 'tdesign-vue-next'
import request from '@/utils/request'
import { AddIcon } from 'tdesign-icons-vue-next';

export default {
  name: 'CalendarView',
  components: { MainLayout, AddIcon },
  setup() {
    const selectedDate = ref(new Date())
    const selectedMonth = ref(new Date())
    const calendarControllerConfig = ref({ visible: true })
    const entries = ref({}) // keyed by YYYY-MM-DD -> { weight, mark }
    // Weight dialog state and helpers
    const weightDialogVisible = ref(false)
    const weightFormDate = ref(new Date())
    const weightFormWeight = ref('')

    const disableOutsideMonth = (date) => {
      const d = new Date(date)
      const m = selectedMonth.value
      return !(d.getFullYear() === m.getFullYear() && d.getMonth() === m.getMonth())
    }

    const openWeightDialog = () => {
      const defaultDate = selectedDate.value || new Date(selectedMonth.value.getFullYear(), selectedMonth.value.getMonth(), 1)
      weightFormDate.value = defaultDate
      const key = formatDayKey(new Date(weightFormDate.value))
      weightFormWeight.value = entries.value[key]?.weight ?? ''
      weightDialogVisible.value = true
    }

    const saveWeight = async () => {
      if (weightFormWeight.value === '' || weightFormWeight.value === null) {
        MessagePlugin.warning('请输入体重')
        return
      }
      try {
        const dateObj = new Date(weightFormDate.value)
        const payload = {
          targetDate: dateObj.toISOString(),
          weight: Number(weightFormWeight.value)
        }
        const res = await request({ url: 'api/healthRecord/addOrUpdateHealthRecord', method: 'POST', data: payload })
        if (res && res.code === 200) {
          MessagePlugin.success('保存成功')
          weightDialogVisible.value = false
          const newMonth = new Date(dateObj.getFullYear(), dateObj.getMonth(), 1)
          selectedMonth.value = newMonth
          await fetchMonthData(newMonth)
        } else {
          MessagePlugin.warning(res?.msg || '保存失败')
        }
      } catch (e) {
        MessagePlugin.error(e.message || '保存失败')
      }
    }

    const formatDayKey = (d) => {
      const y = d.getFullYear()
      const m = String(d.getMonth() + 1).padStart(2, '0')
      const day = String(d.getDate()).padStart(2, '0')
      return `${y}-${m}-${day}`
    }

    const fetchMonthData = async (monthDate) => {
      try {
        const year = String(monthDate.getFullYear())
        const month = String(monthDate.getMonth() + 1)
        const payload = {
          targetYear: year,
          targetMonth: month,
          targetDate: monthDate.toISOString()
        }
        const res = await request({ url: 'api/calendar/getDailyInfoList', method: 'POST', data: payload })
        if (res && res.code === 200 && Array.isArray(res.data)) {
          const map = {}
          res.data.forEach(item => {
            if (!item || !item.targetDate) return
            const d = new Date(item.targetDate)
            const key = formatDayKey(d)
            map[key] = { weight: item.weight, mark: item.mark ,weightDiff: item.weightDiff}
          })
          entries.value = map
        } else {
          MessagePlugin.warning(res?.msg || '无日历数据')
        }
      } catch (e) {
        MessagePlugin.error(e.message || '获取日历数据失败')
      }
    }

    onMounted(() => {
      fetchMonthData(selectedMonth.value)
    })

    const handleCellClick = (date) => {
      selectedDate.value = date
      MessagePlugin.info(`您点击了 ${date.toLocaleDateString()}`)
    }

    const handleMonthChange = (newMonth) => {
      selectedMonth.value = newMonth
      selectedDate.value = new Date(newMonth.getFullYear(), newMonth.getMonth(), 1)
      fetchMonthData(newMonth)
    }

    return {
      selectedDate,
      selectedMonth,
      calendarControllerConfig,
      handleCellClick,
      handleMonthChange,
      entries,
      formatDayKey
      ,
      // weight dialog
      weightDialogVisible,
      weightFormDate,
      weightFormWeight,
      disableOutsideMonth,
      openWeightDialog,
      saveWeight
    }
  }
}
</script>

<style scoped>
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

.cell-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 4px 6px;
}
.cell-day {
  font-weight: 600;
}
.cell-meta {
  margin-top: 4px;
  font-size: 12px;
  color: #666;
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.weight {
  color: #1f8ef1;
}
.weight-diff {
  color: #f50e0e;
}
.mark {
  color: #ff6b6b;
}
</style>