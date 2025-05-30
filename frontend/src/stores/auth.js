import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router/index.js'

// auth 전용 axios 인스턴스 (순환참조 방지)
const authApiClient = axios.create({
  baseURL: 'http://localhost:9999/api',
  timeout: 10000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest',
  },
})

export const useAuthStore = defineStore('auth', {
  state: () => ({
    // 메모리에만 저장 - 새로고침 시 초기화됨
    accessToken: null,
    refreshToken: null,
    username: null,
    authorities: [],
  }),

  getters: {
    isLoggedIn: (state) => {
      // 토큰이 있는지 확인
      if (!state.accessToken) {
        return false
      }
      return true
    },

    hasAuthority: (state) => (authority) => {
      return state.authorities.includes(authority)
    },

    isAccessTokenExpired: (state) => {
      const decodeJwtPayload = (token) => {
        if (!token) return null
        try {
          const payloadBase64 = token.split('.')[1]
          const payloadJson = atob(payloadBase64)
          return JSON.parse(payloadJson)
        } catch (e) {
          console.error('JWT 디코딩 실패:', e)
          return null
        }
      }

      const payload = decodeJwtPayload(state.accessToken) // ✅ 내부 함수로 호출
      if (!payload || !payload.exp) return true
      const now = Math.floor(Date.now() / 1000)
      return payload.exp < now
    },

    isRefreshTokenExpired: (state) => {
      const payload = decodeJwtPayload(state.refreshToken) // ✅ 오타 수정
      if (!payload || !payload.exp) return true
      const now = Math.floor(Date.now() / 1000)
      return payload.exp < now
    },
  },

  actions: {
    async login(credentials) {
      try {
        const response = await authApiClient.post('/auth/login', {
          userId: credentials.userId,
          userPw: credentials.userPw,
        })

        const authData = response.data.data
        this.setAuthData(authData)

        return authData
      } catch (error) {
        console.error('Login failed:', error)
        throw error
      }
    },

    async refreshAccessToken() {
      if (!this.refreshToken) {
        throw new Error('No refresh token available')
      }

      // refresh token도 만료되었다면 로그아웃
      if (this.isRefreshTokenExpired()) {
        this.logout()
        throw new Error('Refresh token expired')
      }

      try {
        const response = await authApiClient.post('/auth/refresh', {
          refreshToken: this.refreshToken,
        })

        const authData = response.data.data
        this.setAuthData(authData)

        return authData
      } catch (error) {
        console.error('Token refresh failed:', error)
        this.logout()
        throw error
      }
    },

    setAuthData(authData) {
      console.log('Setting auth data:', authData) // 디버깅용

      this.accessToken = authData.accessToken
      this.refreshToken = authData.refreshToken
      this.username = authData.username
      this.authorities = authData.authorities || []

      console.log('Auth store state after setting:', {
        accessToken: this.accessToken,
        refreshToken: this.refreshToken,
        username: this.username,
      }) // 디버깅용
    },

    logout() {
      // 메모리에서만 제거
      this.accessToken = null
      this.refreshToken = null
      this.username = null
      this.authorities = []

      router.push({ name: 'Login' })
    },

    decodeJwtPayload(token) {
      if (!token) return null
      try {
        const payloadBase64 = token.split('.')[1]
        const payloadJson = atob(payloadBase64)
        return JSON.parse(payloadJson)
      } catch (e) {
        console.error('JWT 디코딩 실패:', e)
        return null
      }
    },
  },
})
