<script setup>
import { RouterView } from 'vue-router'
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { setAuthHelpers } from '@/utils/axios'
import { ModalsContainer } from 'vue-final-modal'

const authStore = useAuthStore()

// 앱이 마운트된 후 auth helpers 등록
onMounted(() => {
  console.log('Setting up auth helpers...') // 디버깅용
  setAuthHelpers(
    () => {
      console.log('Getting token from store:', authStore.accessToken) // 디버깅용
      return authStore.accessToken
    },
    async () => {
      console.log('Refreshing token...')
      return await authStore.refreshAccessToken()
    },
  )
})
</script>

<template>
  <router-view />
  <ModalsContainer />
</template>
