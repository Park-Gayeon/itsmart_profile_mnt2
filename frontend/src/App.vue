<script setup></script>

<template>
  <div id="app">
    <header>
      <h1>My Vue 3 App</h1>
    </header>

    <!-- 라우트 컴포넌트 렌더링 -->
    <router-view />

    <!-- 전역 모달 컨테이너 -->
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
header {
  margin-bottom: 2rem;
}
</style>
