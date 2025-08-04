import { createRouter, createWebHistory } from 'vue-router'
import { useVueCookies } from '@/components/Cookies'

const isLoggedIn = (() => {
  if(useVueCookies().get('loginUsername')) {
    return true;
  } else {
    return false;
  }
});

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../components/HeroSection.vue'),
      beforeEnter: () => {
        try {
          if(isLoggedIn()) {
            router.push('/article-feeds')
          }
        } catch {
          console.log('Not Logged In')
        }
      }
    },
    {
      path: '/login-screen',
      name: 'loginScreen',
      component: () => import('../views/SigninScreen.vue'),
      beforeEnter: () => {
        try {
          if(isLoggedIn()) {
            router.push('/article-feeds')
          }
        } catch {
          console.log('Not Logged In')
        }
      }
    },
    {
      path: '/article-feeds',
      name: 'articleFeeds',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      children: [
        {
          path: '',
          component: () => import('../views/ArticleFeeds.vue')
        },
        {
          path: ':slug',
          component: () => import('../views/ArticleContent.vue')
        }

      ]
    },
    // {
    //   path: '/article-feeds/:slug',
    //   name: 'ArticleContent',
    //   component: () => import('../views/ArticleContent.vue')
    // },
  ],
})

export default router
