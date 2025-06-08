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
      :menu-items="computedMenuItems"
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

    <!-- 비밀번호 변경 모달 -->
    <PasswordChangeModal v-model="showChangePasswordModal" @closed="handlePasswordModalClosed" />
  </div>
</template>

<script>
import DefaultHeader from '@/components/layout/DefaultHeader.vue'
import DefaultFooter from '@/components/layout/DefaultFooter.vue'
import DefaultSidebar from '@/components/layout/DefaultSidebar.vue'
import PasswordChangeModal from '@/components/modals/PasswordChangeModal.vue'
import { useAuthStore } from '@/stores/auth.js'
import router from '@/router/index.js'

export default {
  name: 'DefaultLayout',
  components: {
    DefaultHeader,
    DefaultFooter,
    DefaultSidebar,
    PasswordChangeModal,
  },
  data() {
    return {
      authStore: useAuthStore(),
      isSidebarOpen: false,
      showChangePasswordModal: false,
    }
  },
  props: {
    sidebarConfig: {
      type: Object,
      default: () => ({
        headerTitle: '환영합니다',
        headerSubtitle: '',
        accountTitle: '계정 설정',
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
        companyName: 'ITSmart',
        version: '1.0.0',
      }),
    },
  },
  computed: {
    computedSidebarConfig() {
      return {
        ...this.sidebarConfig,
        headerSubtitle: this.authStore.username + ' 님',
      }
    },
    computedMenuItems() {
      const userId = this.authStore.username
      const items = [
        { id: 'Main', label: 'HOME', route: { name: 'Main' } },
        { id: 'ProfileList', label: '직원 프로필관리', route: { name: 'ProfileList' } },
        { id: 'Project', label: '프로젝트 관리', route: { name: 'Project' } },
        { id: 'Schedule', label: '프로젝트 일정관리', route: { name: 'Schedule' } },
        { id: 'Org', label: '회사조직도', route: { name: 'Org' } },
      ]
      if (userId) {
        items.splice(1, 0, {
          id: 'Profile',
          label: '내 프로필관리',
          route: { name: 'Profile', params: { userId } },
        })
      }

      return items
    },
  },
  emits: ['navigate', 'account-action', 'footer-navigate'],

  methods: {
    toggleSidebar() {
      if (!this.authStore.isLoggedIn) {
        this.isSidebarOpen = false
        this.$alertMsg('로그인 후 이용 가능합니다.', () => this.login())
      } else {
        this.isSidebarOpen = !this.isSidebarOpen
        console.log(this.authStore.authorities[0])
        console.log(this.authStore.username)
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
      switch (item.action) {
        case 'logout':
          this.authStore.logout()
          router.push({ name: 'Login' })
          break
        case 'changePassword':
          this.openChangePassword()
          break
        default:
          this.$emit('account-action', item)
      }
    },

    // 비밀번호 변경 모달 열기
    openChangePassword() {
      this.showChangePasswordModal = true
    },

    handlePasswordModalClosed() {
      this.showChangePasswordModal = false
      this.authStore.logout()
      router.push({ name: 'Login' })
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
