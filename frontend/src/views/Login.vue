<template>
  <main class="content">
    <div class="form-signin w-100 m-auto">
      <div class="logo pb-3">
        <h2>로그인</h2>
      </div>
      <form @submit.prevent="login">
        <div class="form-floating mb-3">
          <input
            type="text"
            class="form-control"
            id="floatingInput"
            v-model="userId"
            :disabled="loading"
          />
          <label for="floatingInput">ID</label>
        </div>
        <div class="form-floating mb-3">
          <input
            type="password"
            class="form-control"
            id="floatingPassword"
            v-model="userPw"
            autocomplete="off"
            :disabled="loading"
          />
          <label for="floatingPassword">PW</label>
        </div>
        <button type="submit" class="btn btn-sign w-100 py-3" :disabled="loading">
          <span class="basic-medium">
            {{ loading ? '로그인 중...' : '로그인' }}
          </span>
        </button>
      </form>
    </div>
  </main>
</template>

<script>
import image from '@/assets/images/etc/person-circle.svg'
import { useAuthStore } from '@/stores/auth'
import { toast } from 'vue3-toastify'
import { mapStores } from 'pinia'

export default {
  name: 'LoginView',
  data() {
    return {
      userId: '',
      userPw: '',
      logoSrc: image,
      loading: false,
    }
  },
  computed: {
    // Pinia store를 computed로 접근
    ...mapStores(useAuthStore),
  },
  methods: {
    async login() {
      // 입력값 검증
      if (!this.userId || !this.userPw) {
        toast.error('ID/PW를 확인해주세요.')
        return
      }

      this.loading = true

      try {
        await this.authStore.login({
          userId: this.userId,
          userPw: this.userPw,
        })

        this.$router.push({ name: 'Main' })
      } catch (error) {
        console.error('로그인 실패:', error)

        const errorMessage =
          error.response?.data?.message ||
          error.response?.data?.error ||
          '로그인에 실패했습니다. 다시 시도해주세요.'

        alert(errorMessage)

        // 로그인 실패 시 비밀번호만 초기화
        this.userPw = ''
      } finally {
        this.loading = false
      }
    },

    handleEnter(event) {
      if (event.key === 'Enter' && !event.shiftKey && !this.loading) {
        this.login()
      }
    },
  },

  mounted() {
    // 이미 로그인된 상태라면 메인 페이지로 이동
    if (this.authStore.isLoggedIn) {
      this.$router.push({ name: 'Main' })
      return
    }

    // Enter 키 이벤트 리스너 추가
    window.addEventListener('keydown', this.handleEnter)
  },

  unmounted() {
    // 컴포넌트 해제 시 이벤트 리스너 제거
    window.removeEventListener('keydown', this.handleEnter)
  },
}
</script>

<style scoped>
@import '@/assets/css/login.css';

.form-signin {
  max-width: 330px;
  padding: 1rem;
}

.logo-image img {
  width: 64px;
  height: 64px;
}
</style>
