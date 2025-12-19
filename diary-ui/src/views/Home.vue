<!-- src/views/Home.vue -->
<template>
  <MainLayout>
    <div class="page-content">
      <h1>网站建设中...</h1>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/layouts/MainLayout.vue'

export default {
  name: 'HomeView',
  components: { MainLayout }
}
</script>

<style scoped>
.page-content h1 {
  margin-bottom: 16px;
}
</style>