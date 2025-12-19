<!-- src/views/Login.vue -->
<template>
  <div class="auth-container">
    <div class="auth-form">
      <h2 class="form-title">用户登录</h2>
      <t-form :data="formData" :rules="rules" @submit="handleSubmit">
        <t-form-item label="用户名" name="username">
          <t-input v-model="formData.username" placeholder="请输入用户名"></t-input>
        </t-form-item>
        <t-form-item label="密码" name="password">
          <t-input v-model="formData.password" type="password" placeholder="请输入密码"></t-input>
        </t-form-item>
        <t-form-item>
          <t-button theme="primary" type="submit" block>登录</t-button>
        </t-form-item>
        <t-form-item>
          <t-button variant="text" @click="$router.push('/register')">还没有账号？立即注册</t-button>
        </t-form-item>
      </t-form>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import request, { setAuthToken } from '@/utils/request'

export default {
  name: 'LoginView',
  setup() {
    const router = useRouter()

    const formData = reactive({
      username: '',
      password: ''
    })

    const rules = {
      username: [{ required: true, message: '请输入用户名', type: 'error' }],
      password: [
        { required: true, message: '请输入密码', type: 'error' },
        { min: 6, message: '密码长度不能少于6位', type: 'warning' }
      ]
    }

    const handleSubmit = async ({ validateResult, firstError }) => {
      if (validateResult === true) {
        try {
          const payload = {
            loginType: 2,
            account: formData.username,
            password: formData.password,
          }
          const res = await request({ url: 'auth/login', method: 'POST', data: payload })
          if (res && res.code === 200 && res.data && res.data.token) {
            setAuthToken(res.data.token)
            localStorage.setItem('isAuthenticated', 'true')
            localStorage.setItem('currentUser', formData.username)
            MessagePlugin.success('登录成功!')
            router.push('/home')
          } else {
            MessagePlugin.error(res?.msg || '登录失败')
          }
        } catch (e) {
          MessagePlugin.error(e.message || '登录异常')
        }
      } else {
        console.log('Errors: ', firstError)
        MessagePlugin.warning(firstError)
      }
    }

    return {
      formData,
      rules,
      handleSubmit
    }
  }
}
</script>

<style scoped>
/* 引用背景图片 */
.auth-container {
  /* 使用背景图片 */
  background-image: url('@/assets/background.png'); /* 确保路径正确 */
  background-size: cover; /* 覆盖整个容器 */
  background-position: center; /* 图片居中 */
  background-repeat: no-repeat; /* 不重复 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  /* 移除之前的纯色背景 */
  /* background-color: #f5f7fa; */
}

.auth-form {
  width: 400px;
  padding: 30px;
  background: rgba(255, 255, 255, 0.8); /* 可选：给表单加个半透明白色背景，使文字更易读 */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.form-title {
  text-align: center;
  margin-bottom: 24px;
  font-size: 24px;
  color: #333;
}
</style>