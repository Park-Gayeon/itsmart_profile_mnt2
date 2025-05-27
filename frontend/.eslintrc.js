module.exports = {
    root: true,
    env: {
        browser: true,
        node: true,
        es2021: true,
    },
    parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
    },
    extends: [
        'eslint:recommended',
        'plugin:vue/vue3-recommended',   // Vue 3 권장 규칙
        'plugin:prettier/recommended',    // Prettier 플러그인 활성화
    ],
    rules: {
        // 프로젝트별 룰 커스터마이징 예시:
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'vue/multi-word-component-names': 'off',
        // …
    },
};
