<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useVueCookies } from '../components/Cookies'
import { useRoute } from 'vue-router'
import ArticleCard from '../components/ArticleCard.vue'
import articleCover from '../assets/logo.png'

const cookies = useVueCookies();
const route = useRoute();
const username = ref(cookies.get('loginUsername'))

interface PostsData {
  content: {
    createdAt: Date
    title: string
    content: string
    slug: string
  }[]
  page: {
    size: number
    number: number
    totalElements: number
    totalPages: number
  }[]
}
const postsData = ref<PostsData>({
  content: [],
  page: [],
})
onMounted(async () => {
  try {
    const data = await fetch('http://localhost:8080/posts/me', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.get('token')}`,
      },
    })
    postsData.value = await data.json()
  } catch {
    console.error('not logged in')
  }
})
</script>

<template>
  <div>
    <section>
      <div
        class="h-35 flex text-5xl text-gray-500 justify-center items-center bg-gray-600 overflow-hidden rounded-3xl"
      >
        <p>Cover</p>
      </div>

      <div class="w-full pt-5 flex flex-row h-35">
        <div class="relative top-[-40%] h h-full pr-5">
          <img
            class="h-full border-main-color border-2 outline-second-color outline-4 rounded-full bg-gray-500"
            src="../assets/logo.png"
            alt=""
          />
        </div>

        <div class="text-xl pr-6">
          <p>{{ username }}</p>
        </div>
      </div>
    </section>

    <section class="w-full flex flex-col gap-7 justify-center">
      <div class="h-15 flex flex-row gap-3 items-center px-5 border-fifth-color border-1
        rounded-3xl">
        <router-link
          to="/me"
          class="h-10 relative flex items-center transition-all duration-300"
          exact-active-class="after:content-[''] after:absolute after:bottom-0 after:w-full after:h-1 after:bg-fourth-color after:rounded-4xl"
        >
          <font-awesome-icon :icon="['fas', 'home']"/>
        </router-link>
        <router-link
          to="/me/articles"
          class="h-10 px-5 relative flex items-center justify-center transition-all
          duration-300"
          active-class="pb-2 after:content-[''] after:absolute after:bottom-0 after:w-2/3 after:h-1 after:bg-fourth-color after:rounded-4xl before:content-[''] before:absolute before:bottom-0 before:w-full before:h-full before:bg-fourth-color before:rounded-xl before:opacity-10 before:p-5"
        >
          مقالاتي
        </router-link>
      </div>

      <transition
        enter-active-class="transition-opacity duration-300"
        enter-from-class="opacity-0"
        enter-to-class="opacity-100"
        leave-active-class="transition-opacity duration-300"
        leave-to-class="opacity-0"
      >
        <div
          v-if="route.path === '/me/articles'"
          class="flex flex-row justify-center flex-wrap gap-5">
          <ArticleCard
            v-for="article in postsData.content"
            :key="article.id"
            :articleCover="articleCover"
            :articleTitle="article.title"
            :articleDescription="article.content"
            :articleDate="article.createdAt"
            :articleSlug="article.slug"
          />
        </div>
      </transition>
    </section>
  </div>
</template>

<style></style>
