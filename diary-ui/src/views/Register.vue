<!-- src/views/Register.vue -->
<template>
  <div class="auth-container">
    <div class="auth-form">
      <h2 class="form-title">用户注册</h2>
      <t-form :data="formData" :rules="rules" @submit="handleSubmit">
        <t-form-item label="用户名" name="username">
          <t-input v-model="formData.username" placeholder="请输入用户名"></t-input>
        </t-form-item>
        <t-form-item label="密码" name="password">
          <t-input v-model="formData.password" type="password" placeholder="请输入密码"></t-input>
        </t-form-item>
        <t-form-item label="确认密码" name="confirmPassword">
          <t-input v-model="formData.confirmPassword" type="password" placeholder="请再次输入密码"></t-input>
        </t-form-item>
        <t-form-item>
          <t-button theme="primary" type="submit" block>注册</t-button>
        </t-form-item>
        <t-form-item>
          <t-button variant="text" @click="$router.push('/login')">已有账号？立即登录</t-button>
        </t-form-item>
      </t-form>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'

export default {
  name: 'RegisterView',
  setup() {
    const router = useRouter()

    const formData = reactive({
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    })

    const rules = {
      username: [{ required: true, message: '请输入用户名', type: 'error' }],
      email: [
        { required: true, message: '请输入邮箱', type: 'error' },
        { email: true, message: '请输入正确的邮箱格式', type: 'warning' }
      ],
      password: [
        { required: true, message: '请输入密码', type: 'error' },
        { min: 6, message: '密码长度不能少于6位', type: 'warning' }
      ],
      confirmPassword: [
        { required: true, message: '请确认密码', type: 'error' },
        {
          validator: (val) => val === formData.password,
          message: '两次输入密码不一致',
          type: 'error'
        }
      ]
    }

    const handleSubmit = ({ validateResult, firstError }) => {
      if (validateResult === true) {
        // 模拟注册逻辑
        // 实际项目中应发送请求到后端创建用户
        MessagePlugin.success('注册成功! 请返回登录。')
        router.push('/login')
      } else {
         console.log('Errors: ', firstError);
         MessagePlugin.warning(firstError);
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
/* 复用 Login.vue 的样式 *//* 复用并修改 Login.vue 的背景样式 */
.auth-container {
  /* 使用相同的背景图片 */
  background-image: url('@/assets/background.png'); /* 确保路径正确 */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
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
  background: rgba(255, 255, 255, 0.8); /* 可选：同登录页 */
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