<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useVueCookies } from '../components/Cookies'
import { useRoute } from 'vue-router'
import ArticleCard from '@/components/ArticleCard.vue'
import InProgress from '../components/InProgress.vue';
import Logout from '../components/Logout.vue'
import articleCover from '@/assets/logo.png'

const cookies = useVueCookies();
const route = useRoute();
const username = ref(cookies.get('loginUsername'));
const wantToLogOut = ref<boolean>();

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

function showLogoutPopup() {
  wantToLogOut.value = true
}

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
  <div class="pt-5">
    <Logout v-if="wantToLogOut" @close="wantToLogOut = false"/>
    <section>
      <div
        class="h-35 flex text-5xl text-gray-500 justify-center items-center bg-gray-600 overflow-hidden rounded-3xl"
      >
        <p>Cover</p>
      </div>

      <div class="w-full h-45 sm:h-45 pt-5 px-5 flex flex-row justify-between">
        <div class="flex flex-row ">
          <div class="relative z-2 top-[-40%] w-30 h-30">
            <img
              class="border-main-color border-2 outline-second-color outline-4 rounded-full bg-gray-500"
              src="../assets/logo.png"
              alt=""
            />
          </div>
          <div class="text-xl grow overflow-hidden">
            <p class="overflow-hidden px-5 h-full wrap-break-word w-full">{{ username
            }}</p>
          </div>
        </div>

        <div class="h-full flex relative top-[-14px] pl-2 flex-row grow justify-end">
          <button
            @click="showLogoutPopup"
            class="cursor-pointer text-2xl flex h-12 w-12 p-1 justify-center items-center hover:scale-115 hover:bg-fourth-color hover:text-second-color transition-all hover:p-2 rounded-4xl"
          >
            <font-awesome-icon :icon="['fas', 'right-from-bracket']"/>
          </button>
        </div>
      </div>
    </section>

    <section class="w-full flex flex-col gap-7 justify-center">
      <div class="h-15 flex flex-row gap-2 items-center px-5 border-fifth-color border-1
        rounded-3xl">
        <router-link
          to="/me"
          class="h-10 ml-5 relative flex items-center transition-all duration-300"
          exact-active-class="after:content-[''] after:absolute after:bottom-0 after:w-full after:h-1 after:bg-fourth-color after:rounded-4xl"
        >
          <font-awesome-icon :icon="['fas', 'home']"/>
        </router-link>
        <router-link
          to="/me/articles"
          class="h-10 px-2 relative flex items-center justify-center transition-all duration-300"
          active-class="pb-2 after:content-[''] after:absolute after:bottom-0 after:w-2/3
          after:h-1 after:bg-fourth-color after:rounded-4xl before:content-[''] before:absolute before:bottom-0 before:w-[120%] before:h-full before:bg-fourth-color before:rounded-xl before:opacity-10 before:p-5"
        >
          مقالاتي
        </router-link>

        <router-link
          to="/me/videos"
          class="h-10 px-2 relative flex items-center justify-center transition-all duration-300"
          active-class="pb-2 after:content-[''] after:absolute after:bottom-0 after:w-2/3 after:h-1 after:bg-fourth-color after:rounded-4xl before:content-[''] before:absolute before:bottom-0 before:w-[120%] before:h-full before:bg-fourth-color before:rounded-xl before:opacity-10 before:p-5"
        >
          فيديوهاتي
        </router-link>
      </div>

      <transition
        enter-active-class="transition-opacity duration-400"
        enter-from-class="opacity-0 absolute"
        enter-to-class=" opacity-100 delay-400"
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
        <div
          v-else-if="route.path === '/me/videos'"
          class="flex flex-row justify-center flex-wrap gap-5">
          <InProgress/>
        </div>
      </transition>
    </section>
  </div>
</template>

<style>
</style>
