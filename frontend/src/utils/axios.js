import axios from 'axios'
import { useAuthStore } from '@/stores/auth.js'

const apiClient = axios.create({
  baseURL: 'http://localhost:9999/api',
  timeout: 10000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest',
  },
})

let isRefreshing = false
let refreshSubscribers = []

function onRefreshed(newToken) {
  refreshSubscribers.forEach((callback) => callback(newToken))
  refreshSubscribers = []
}

// 토큰을 메모리에서 가져오기 위한 함수들
let getTokenFromStore = null
let refreshTokenFromStore = null

// Store 함수들을 등록하는 함수
export const setAuthHelpers = (getToken, refreshToken) => {
  getTokenFromStore = getToken
  refreshTokenFromStore = refreshToken
}

// 요청 인터셉터
apiClient.interceptors.request.use(
  async (config) => {
    const authStore = useAuthStore()
    const accessToken = authStore.accessToken
    const isExpired = authStore.isAccessTokenExpired

    // 로그인 요청은 토큰 제외
    if (config.url === '/auth/login' || config.url?.includes('/auth/login')) {
      return config
    }
    console.log('Request interceptor - token:', accessToken, 'Expired:', isExpired) // 디버깅용

    if (isExpired) {
      try {
        console.warn('Access token expired, attempting refresh...')
        await authStore.refreshAccessToken()
        const newToken = authStore.accessToken

        if (newToken) {
          config.headers.Authorization = `Bearer ${newToken}`
        }
      } catch (error) {
        console.error('Token refresh failed:', error)
        authStore.logout()
      }
    } else {
      config.headers.Authorization = `Bearer ${accessToken}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true

      if (isRefreshing) {
        return new Promise((resolve) => {
          refreshSubscribers.push((newToken) => {
            originalRequest.headers.Authorization = `Bearer ${newToken}`
            resolve(apiClient(originalRequest))
          })
        })
      }

      isRefreshing = true

      try {
        if (refreshTokenFromStore) {
          await refreshTokenFromStore()
          const newToken = getTokenFromStore ? getTokenFromStore() : null
          if (newToken) {
            onRefreshed(newToken)
            originalRequest.headers.Authorization = `Bearer ${newToken}`
            return apiClient(originalRequest)
          }
        }

        throw new Error('Token refresh failed')
      } catch (refreshError) {
        window.location.href = '/login'
        return Promise.reject(refreshError)
      } finally {
        isRefreshing = false
      }
    }

    return Promise.reject(error)
  },
)

export default apiClient
