<template>
  <div class="text-center">
    <button @click="goList" class="btn btn-secondary"><span>목록</span></button>
    <button @click="goProfile" class="btn btn-secondary"><span>상세</span></button>
    <button @click="goProject" class="btn btn-secondary"><span>프로젝트</span></button>
    <button @click="goProjectSchedule" class="btn btn-secondary"><span>일정관리</span></button>
    <button @click="goOrg" class="btn btn-secondary"><span>조직도</span></button>

    <img
      src="@/assets/images/etc/city_view.webp"
      alt="city-view"
      :class="{ loaded: loaded }"
      @load="onLoad"
      class="main-image"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import router from '@/router/index.js'

const loaded = ref(false)
const onLoad = () => {
  loaded.value = true
}

function goList() {
  router.push({ name: 'ProfileList' })
}
function goProfile() {
  const userId = 'admin'
  router.push({ name: 'Profile', params: { userId } })
}
function goProject() {
  router.push({ name: 'Project' })
}
function goProjectSchedule() {
  router.push({ name: 'Schedule' })
}
function goOrg() {
  router.push({ name: 'Org' })
}
</script>

<style scoped>
.main-image {
  opacity: 0;
  transition: opacity 0.5s ease-in-out;
  max-width: 100%;
}
.main-image.loaded {
  opacity: 1;
}
</style>
