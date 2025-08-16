import { createRouter, createWebHistory } from 'vue-router'
import { useVueCookies } from '@/components/Cookies'

const isLoggedIn = () => {
  if (useVueCookies().get('loginUsername')) {
    return true
  } else {
    return false
  }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../components/HeroSection.vue'),
      beforeEnter: () => {
        try {
          if (isLoggedIn()) {
            router.push('/article-feeds')
          }
        } catch {
          console.log('Not Logged In')
        }
      },
    },
    {
      path: '/login-screen',
      name: 'loginScreen',
      component: () => import('../views/SigninScreen.vue'),
      beforeEnter: () => {
        try {
          if (isLoggedIn()) {
            router.push('/article-feeds')
          }
        } catch {
          console.log('Not Logged In')
        }
      },
    },
    {
      path: '/article-feeds',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      children: [
        {
          path: '',
          name: 'articleFeeds',
          component: () => import('../views/ArticleFeeds.vue'),
        },
        {
          path: ':slug',
          name: 'articleContent',
          component: () => import('../views/ArticleContent.vue'),
        },
      ],
    },
    {
      path: '/me',
      children: [
        {
          path: '',
          name: 'userPage',
          component: () => import('../views/UserInfo.vue'),
          // beforeEnter() {
          //   router.push('/me/articles')
          // },
        },
        {
          path: 'articles',
          name: 'userArticle',
          component: () => import('../views/UserInfo.vue'),
        },
      ],
    },
    {
      path: '/video-feeds',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      children: [
        {
          path: '',
          name: 'videoFeeds',
          component: () => import('../views/VideoFeeds.vue'),
        },
        {
          path: ':video-slug',
          name: 'VideoContent',
          component: () => import('../views/VideoContent.vue'),
        },
      ],
    },
    {
      path: '/new-post',
      name: 'createPost',
      component: () => import('../views/CreatePost.vue'),
    }
  ],
})

export default router
