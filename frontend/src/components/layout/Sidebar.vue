<!-- src/components/layout/Sidebar.vue -->
<template>
  <div class="offcanvas offcanvas-start text-bg-dark" tabindex="-1" id="offcanvasDarkNavbar">
    <div class="offcanvas-header">
      <button
        type="button"
        class="btn-close btn-close-white"
        data-bs-dismiss="offcanvas"
        aria-label="Close"
      ></button>
    </div>
    <div class="offcanvas-body">
      <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3 mb-5">
          <li class="nav-item">
            <RouterLink class="nav-link" :class="{ active: isActive('/') }" to="/">HOME</RouterLink>
          </li>
          <li class="nav-item" v-if="userRole[0] === 'ROLE_ADMIN'">
            <RouterLink
              class="nav-link"
              :class="{ active: isActive('/profile/info/list') }"
              to="/profile/info/list"
              >직원 프로필관리</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link"
              :class="{ active: isActive(`/profile/${loginUser}`) }"
              :to="`/profile/${loginUser}`"
              >내 프로필관리</RouterLink
            >
          </li>
          <li class="nav-item" v-if="userRole[0] === 'ROLE_ADMIN'">
            <RouterLink
              class="nav-link"
              :class="{ active: isActive('/project/common/list') }"
              to="/project/common/list"
              >프로젝트 관리</RouterLink
            >
            <RouterLink
              class="nav-link"
              :class="{ active: isActive('/schedule/list') }"
              to="/schedule/list"
              >프로젝트 일정관리</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" :class="{ active: isActive('/orgChart') }" to="/orgChart"
              >회사조직도</RouterLink
            >
          </li>
        </ul>

        <hr />
        <div class="dropdown">
          <a
            href="#"
            class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
            data-bs-toggle="dropdown"
            data-bs-auto-close="true"
            aria-expanded="false"
          >
            <strong>{{ loginUser }}</strong>
          </a>
          <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
            <li><a class="dropdown-item" @click.prevent="changePw(loginUser)">비밀번호변경</a></li>
            <li><a class="dropdown-item" @click.prevent="logout">로그아웃</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
const props = defineProps({
  loginUser: String,
  userRole: Array,
})

const route = useRoute()

const isActive = (path) => route.path.startsWith(path)

const changePw = (user) => {
  alert(`${user} 비밀번호 변경`)
}
const logout = () => {
  alert('로그아웃 처리')
}
</script>
