<template>
  <header class="header">
    <div class="header-content">
      <button class="menu-btn" @click="toggleNavigation">
        <span class="icon icon-menu"></span>
      </button>
      <div class="header-actions">
        <!-- 추가 헤더 액션이 필요한 경우 여기에 -->
        <slot name="actions"></slot>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: 'DefaultHeader',
  emits: ['toggle-navigation'],
  methods: {
    toggleNavigation() {
      this.$emit('toggle-navigation')
    },
  },
}
</script>

<style scoped>
.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1000;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.menu-btn {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.menu-btn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.logo {
  font-size: 1.5rem;
  font-weight: bold;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.icon {
  display: inline-block;
  width: 20px;
  height: 20px;
  vertical-align: middle;
}

.icon-menu::before {
  content: '☰';
}

.top-menu {
  width: 250px;
  text-align: right;
}

.header-actions .top-menu a.login {
  padding-left: 20px;
}

@media (max-width: 768px) {
  .header {
    padding: 1rem;
  }
}
</style>
