// vite.config.js
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(), // .vue 파일을 처리할 플러그인
  ],
  resolve: {
    alias: {
      '@': '/src', // Optional: `@` → `src/` 경로 별칭
    },
  },
  server: {
    port: 5173, // 원한다면 포트 지정
    open: true, // dev 서버 시작 시 자동 브라우저 열기
  },
});
