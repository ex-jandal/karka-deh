import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../components/HeroSection.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/loginScreen',
      name: 'loginScreen',
      component: () => import('../views/SigninScreen.vue'),
    },
    {
      path: '/articlefeeds',
      name: 'article',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/ArticleFeeds.vue'),
    },
  ],
})

export default router
