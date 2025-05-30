<template>
  <header class="site-header"></header>
</template>

<script>
export default {
  name: 'Header',
}
</script>

<style scoped>
.site-header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 64px;
  background-color: #212529;
  color: white;
  z-index: 1000;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-inner {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  padding: 0 1rem;
}

.logo {
  font-size: 1.2rem;
  margin: 0;
}

/* 추가 스타일이 필요한 경우 */
.navbar-brand img {
  transition: opacity 0.3s ease;
}

.navbar-brand:hover img {
  opacity: 0.8;
}

.nav-link.router-link-active {
  color: #fff !important;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 0.375rem;
}
</style>
