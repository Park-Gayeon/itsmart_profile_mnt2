<template>
  <div class="layout-wrapper">
    <!-- 헤더 -->
    <DefaultHeader @toggle-navigation="toggleSidebar">
      <template #actions>
        <slot name="header-actions"></slot>
      </template>
    </DefaultHeader>

    <!-- 사이드바 -->
    <DefaultSidebar
      :is-open="isSidebarOpen"
      :header-title="sidebarConfig.headerTitle"
      :header-subtitle="computedSidebarConfig.headerSubtitle"
      :account-title="sidebarConfig.accountTitle"
      :menu-items="sidebarConfig.menuItems"
      :account-items="sidebarConfig.accountItems"
      @close="closeSidebar"
      @navigate="handleNavigation"
      @account-action="handleAccountAction"
    />

    <!-- 메인 컨텐츠 -->
    <main class="main-content">
      <div class="content-wrapper">
        <router-view />
      </div>
    </main>

    <!-- 푸터 -->
    <DefaultFooter
      :company-name="footerConfig.companyName"
      :version="footerConfig.version"
      @navigate="handleFooterNavigation"
    />
  </div>
</template>

<script>
import DefaultHeader from '@/components/layout/DefaultHeader.vue'
import DefaultFooter from '@/components/layout/DefaultFooter.vue'
import DefaultSidebar from '@/components/layout/DefaultSidebar.vue'
import { useAuthStore } from '@/stores/auth.js'
import router from '@/router/index.js'

export default {
  name: 'DefaultLayout',
  components: {
    DefaultHeader,
    DefaultFooter,
    DefaultSidebar,
  },
  props: {
    sidebarConfig: {
      type: Object,
      default: () => ({
        headerTitle: '환영합니다',
        headerSubtitle: '',
        accountTitle: '계정 설정',
        menuItems: [
          { id: 'Main', label: 'HOME', route: 'Main' },
          { id: 'ProfileList', label: '직원 프로필관리', route: 'ProfileList' },
          // { id: 'Profile', label: '내 프로필관리', route: 'Profile' },
          { id: 'Project', label: '프로젝트 관리', route: 'Project' },
          { id: 'Schedule', label: '프로젝트 일정관리', route: 'Schedule' },
          { id: 'Org', label: '회사조직도', route: 'Org' },
        ],
        accountItems: [
          { id: 'logout', label: '로그아웃', icon: 'icon-logout', action: 'logout' },
          {
            id: 'changePassword',
            label: '비밀번호 변경',
            icon: 'icon-lock',
            action: 'changePassword',
          },
        ],
      }),
    },
    footerConfig: {
      type: Object,
      default: () => ({
        companyName: 'My Company',
        version: '1.0.0',
      }),
    },
  },
  computed: {
    computedSidebarConfig() {
      const authStore = useAuthStore()
      return {
        ...this.sidebarConfig,
        headerSubtitle: authStore.username + ' 님',
      }
    },
  },
  emits: ['navigate', 'account-action', 'footer-navigate'],
  data() {
    return {
      authStore: useAuthStore(),
      isSidebarOpen: false,
      menuItems: [
        { id: 'Main', label: 'HOME', route: 'Main' },
        { id: 'ProfileList', label: '직원 프로필관리', route: 'ProfileList' },
        {
          id: 'Profile',
          label: '내 프로필관리',
          // route: { name: 'Profile', params: { userId } },
        },
        { id: 'Project', label: '프로젝트 관리', route: 'Project' },
        { id: 'Schedule', label: '프로젝트 일정관리', route: 'Schedule' },
        { id: 'Org', label: '회사조직도', route: 'Org' },
      ],
    }
  },
  methods: {
    toggleSidebar() {
      if (!this.authStore.isLoggedIn) {
        this.isSidebarOpen = false
        this.$alertMsg('로그인 후 이용 가능합니다.', () => this.login())
      } else {
        this.isSidebarOpen = !this.isSidebarOpen
        console.log(authStore.authorities[0])
        console.log(authStore.username)
      }
    },

    login() {
      router.push({ name: 'Login' })
    },

    closeSidebar() {
      this.isSidebarOpen = false
    },

    handleNavigation(item) {
      this.$emit('navigate', item)
    },

    handleAccountAction(item) {
      this.$emit('account-action', item)
    },

    handleFooterNavigation(page) {
      this.$emit('footer-navigate', page)
    },

    escapeHandler(e) {
      if (e.key === 'Escape' && this.isSidebarOpen) {
        this.closeSidebar()
      }
    },
  },

  mounted() {
    document.addEventListener('keydown', this.escapeHandler)
  },

  beforeUnmount() {
    document.removeEventListener('keydown', this.escapeHandler)
  },
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.layout-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f5f5f5;
}

.main-content {
  flex: 1;
  padding: 2rem;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .main-content {
    padding: 1rem;
  }
}
</style>
