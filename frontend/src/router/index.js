import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/components/layout/DefaultLayout.vue'),
    children: [
      {
        path: '/',
        name: 'Main',
        component: () => import('@/views/MainView.vue'),
      },
      {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: { requiresAuth: false },
      },
      {
        path: '/profile/info/list',
        name: 'ProfileList',
        component: () => import('@/views/ProfileInfoList.vue'),
      },
      {
        path: '/profile/:userId',
        name: 'Profile',
        component: () => import('@/views/UserProfile.vue'),
        props: true,
      },
      {
        path: '/project/common/list',
        name: 'Project',
        component: () => import('@/views/ProjectList.vue'),
      },
      {
        path: '/schedule/list',
        name: 'Schedule',
        component: () => import('@/views/ScheduleList.vue'),
      },
      {
        path: '/orgChart',
        name: 'Org',
        component: () => import('@/views/OrgChart.vue'),
      },
    ],
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
