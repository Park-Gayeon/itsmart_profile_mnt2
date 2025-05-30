<!-- src/layouts/DefaultLayout.vue -->
<template>
  <div class="layout-wrapper">
    <Header :loginUser="loginUser" :userRole="userRole" />

    <main class="layout-content">
      <router-view />
    </main>

    <Footer />
  </div>
</template>

<script>
import Header from '@/components/layout/Header.vue'
import Footer from '@/components/layout/Footer.vue'

export default {
  name: 'DefaultLayout',
  components: {
    Header,
    Footer,
  },
  props: {
    loginUser: {
      type: String,
      default: 'guest',
    },
    userRole: {
      type: Array,
      default: () => ['ROLE_USER'],
    },
  },
}
</script>

<style scoped>
.layout-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  overflow: hidden;
}

.layout-content {
  flex: 1;
  overflow-y: auto;
  padding-top: 64px; /* header height */
  background-color: #e9ecef;
}
</style>
