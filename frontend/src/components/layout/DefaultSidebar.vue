<template>
  <!-- 오버레이 -->
  <div class="overlay" :class="{ active: isOpen }" @click="closeSidebar"></div>

  <!-- 사이드바 -->
  <nav class="sidebar" :class="{ active: isOpen }">
    <div class="sidebar-header">
      <h2>{{ headerTitle }}</h2>
      <p>{{ headerSubtitle }}</p>
    </div>

    <div class="sidebar-menu">
      <router-link
        v-for="item in menuItems"
        :key="item.id"
        :to="item.route"
        class="menu-item"
        @click.native="handleNavigation(item)"
      >
        <span :class="`icon ${item.icon || ''}`"></span>
        {{ item.label }}
      </router-link>
    </div>

    <div class="menu-divider"></div>

    <div class="account-section">
      <button class="account-toggle" @click="toggleAccountMenu">
        <span>
          <span class="icon icon-settings"></span>
          {{ accountTitle }}
        </span>
        <span class="icon icon-chevron" :class="{ rotate: isAccountMenuOpen }"></span>
      </button>

      <div class="account-menu" :class="{ active: isAccountMenuOpen }">
        <button
          v-for="item in accountItems"
          :key="item.id"
          class="account-item"
          @click="handleAccountAction(item)"
        >
          <span :class="`icon ${item.icon}`"></span>
          {{ item.label }}
        </button>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'DefaultSidebar',
  props: {
    isOpen: {
      type: Boolean,
      default: false,
    },
    headerTitle: {
      type: String,
      required: true,
    },
    headerSubtitle: {
      type: String,
      required: true,
    },
    accountTitle: {
      type: String,
      required: true,
    },
    menuItems: {
      type: Array,
      required: true,
    },
    accountItems: {
      type: Array,
      required: true,
    },
  },
  emits: ['close', 'navigate', 'account-action'],
  data() {
    return {
      isAccountMenuOpen: false,
    }
  },
  watch: {
    isOpen(newVal) {
      if (newVal) {
        this.isAccountMenuOpen = false
      }
    },
  },
  methods: {
    handleNavigation(item) {
      this.$emit('navigate', item)
      this.closeSidebar()
    },
    closeSidebar() {
      this.isAccountMenuOpen = false
      this.$emit('close')
    },

    toggleAccountMenu() {
      this.isAccountMenuOpen = !this.isAccountMenuOpen
    },

    handleAccountAction(item) {
      this.$emit('account-action', item)
      this.closeSidebar()
    },
  },
}
</script>

<style scoped>
/* 오버레이 */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
}

.overlay.active {
  opacity: 1;
  visibility: visible;
}

/* 사이드바 */
.sidebar {
  position: fixed;
  top: 0;
  left: -320px;
  width: 320px;
  height: 100%;
  background: white;
  box-shadow: 2px 0 20px rgba(0, 0, 0, 0.1);
  z-index: 1001;
  transition: left 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  flex-direction: column;
}

.sidebar.active {
  left: 0;
}

.sidebar-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 2rem;
}

.sidebar-header h2 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.sidebar-header p {
  opacity: 0.8;
  font-size: 0.9rem;
}

.sidebar-menu {
  flex: 1;
  padding: 1rem 0;
}

.menu-item {
  display: block;
  padding: 1rem 2rem;
  color: #333;
  text-decoration: none;
  transition: all 0.3s ease;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  cursor: pointer;
  font-size: 1rem;
}

.menu-item:hover {
  background-color: #f8f9fa;
  color: #667eea;
  transform: translateX(5px);
}

.menu-divider {
  height: 1px;
  background-color: #e9ecef;
  margin: 1rem 2rem;
}

.account-section {
  padding: 1rem 0;
}

.account-toggle {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 2rem;
  background: none;
  border: none;
  width: 100%;
  cursor: pointer;
  font-size: 1rem;
  color: #333;
  transition: background-color 0.3s ease;
}

.account-toggle:hover {
  background-color: #f8f9fa;
}

.account-toggle .icon {
  margin-right: 1rem;
}

.account-menu {
  background-color: #f8f9fa;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s ease;
}

.account-menu.active {
  max-height: 120px;
}

.account-item {
  display: block;
  padding: 0.75rem 3rem;
  color: #666;
  text-decoration: none;
  transition: all 0.3s ease;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  cursor: pointer;
  font-size: 0.9rem;
}

.account-item:hover {
  background-color: #e9ecef;
  color: #667eea;
}

/* 아이콘 */
.icon {
  display: inline-block;
  width: 20px;
  height: 20px;
  vertical-align: middle;
}

.icon-settings::before {
  content: '⚙️';
}
.icon-logout::before {
  content: '🔑';
}
.icon-lock::before {
  content: '💫';
}
.icon-chevron::before {
  content: '▼';
}
.icon-chevron.rotate {
  transform: rotate(180deg);
}

/* 반응형 */
@media (max-width: 768px) {
  .sidebar {
    width: 280px;
    left: -280px;
  }
}
</style>
