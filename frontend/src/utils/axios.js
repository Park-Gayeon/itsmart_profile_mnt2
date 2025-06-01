import axios from 'axios'
import { useAuthStore } from '@/stores/auth.js'
import router from '@/router/index.js'
import { toast } from 'vue3-toastify'

const apiClient = axios.create({
  baseURL: 'http://localhost:9999/api',
  timeout: 10000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest',
  },
})

// 중복 refresh 방지용 플래그 및 큐
let isRefreshing = false
let refreshSubscribers = []
let refreshTokenPromise = null

function subscribeTokenRefresh(cb) {
  refreshSubscribers.push(cb)
}

function onRefreshed(newToken) {
  refreshSubscribers.forEach((cb) => cb(newToken))
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

    // 인증 요청은 token 제외
    if (config.url?.includes('/auth/')) {
      return config
    }
    const accessToken = authStore.accessToken
    const isExpired = authStore.isAccessTokenExpired()

    console.log('Request interceptor - token:', accessToken, 'Expired:', isExpired) // 디버깅용

    if (accessToken && isExpired) {
      if (!refreshTokenPromise) {
        // 토큰 갱신 시도
        refreshTokenPromise = authStore
          .refreshAccessToken()
          .then((refreshedData) => {
            console.log('Access token expired, attempting refresh...')
            const newToken = refreshedData.accessToken
            console.log('refreshToken 갱신 성공 ! : ', newToken)
            onRefreshed(newToken)
            return newToken
          })
          .catch((error) => {
            authStore.logout()
            console.error('refresh 토큰 갱신 중 오류 발생 : ', error)
            throw error
          })
          .finally(() => {
            refreshTokenPromise = null
          })
      }

      // 모든 요청은 이 promise를 기다림
      return refreshTokenPromise.then((newToken) => {
        config.headers.Authorization = `Bearer ${newToken}`
        return config
      })
    }

    // 토큰이 있고 만료 안됐으면 설정
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`
    } else {
      toast.error('세션이 만료되었습니다. 다시 로그인 해주세요.')
      authStore.logout()
    }
    return config
  },
  (error) => Promise.reject(error),
)

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const authStore = useAuthStore()

    const status = error.response?.status
    if (status === 401) {
      // 권한 없음 → 로그아웃 후 로그인 페이지로 이동
      toast.error('세션이 만료되었습니다. 다시 로그인 해주세요.')
      authStore.logout()
    } else if (status === 403) {
      toast.warn('접근 권한이 없습니다.')
    } else if (status === 500) {
      toast.error('서버 오류가 발생했습니다. 관리자에게 문의하세요.')
    } else if (status >= 400) {
      const msg = error.response?.data?.message || '요청 중 오류가 발생했습니다.'
      toast.error(msg)
    }

    return Promise.reject(error)
  },
)

export default apiClient
