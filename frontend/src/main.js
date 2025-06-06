import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import '@/assets/css/basic.css'
import modalsPlugin from '@/plugins/modals'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createVfm } from 'vue-final-modal'

import App from './App.vue'
import router from './router'
import Toast from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'

const app = createApp(App)
const pinia = createPinia()
const vfm = createVfm()

app.use(pinia)
app.use(router)
app.use(vfm)
app.use(Toast, {
  autoClose: 3000,
  position: 'top-right',
})
app.use(modalsPlugin)

app.mount('#app')
