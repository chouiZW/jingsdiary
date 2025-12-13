<!-- src/App.vue -->
<template>
  <div id="app">
    <!-- 路由匹配到的组件将在这里渲染 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 全局样式 */
#app {
  height: 100vh;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/* 可选：重置一些默认样式 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
</style>