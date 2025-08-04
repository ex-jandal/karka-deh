<script setup lang="ts">
import ArticleCard from '@/components/ArticleCard.vue'
import ArticleContent from '@/views/ArticleContent.vue'
import Spiner from '@/components/Spiner.vue'
import articleCover from '@/assets/logo.png'
import { ref, onMounted } from 'vue'
import { useVueCookies } from '../components/Cookies';

let cookies = useVueCookies();

const notSignedIn = ref<Boolean>(true);
const loading = ref<Boolean>(true)

interface PostsData {
  content: {
    createdAt: Date,
    autherId: string,
    title: string,
    content: string,
    slug: string
  } [];
  page: {
    size: number,
    number: number,
    totalElements: number,
    totalPages: number
  } []
}
const postsData = ref<PostsData>({
  content: [],
  page: []
})
onMounted(async () => {
  try {
    const data = await fetch("http://localhost:8080/posts/all", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${cookies.get('token')}`
      },
    });
    postsData.value = await data.json();
    await new Promise(reslove => setTimeout(reslove, 1000))
    loading.value = false;

  } catch {
    notSignedIn.value = false
    console.error("not logged in")
  }
})
</script>

<template>
  <Transition
      enter-active-class="transition-opacity duration-700"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
  >

    <div v-if="!notSignedIn" class="fixed h-screen w-screen top-0 right-0 flex flex-row gap-2 text-2xl justify-center
      items-center transition-all">
      <router-link
        class="bg-white text-amber-500 p-5 flex flex-row transition-all group
        hover:bg-amber-500 hover:text-white justify-between items-center w-60 h-26 rounded-[100px]"
        to="/login-screen"
      >
        <p class="grow-3 flex flex-row justify-center">سجل اولا</p>
        <div class="grow-1 rounded-[100px] flex justify-center items-center
          group-hover:text-amber-500 group-hover:bg-white h-18 w-15 text-3xl text-white bg-amber-500 transition-all">
        <font-awesome-icon
          :icon="['fas', 'circle-left']"
          class="rotate-[-90deg] group-hover:rotate-0 duration-200 transition-all"
        />
        </div>
      </router-link>
    </div>

    <Spiner v-else-if="loading"/>

      <section v-else class="pt-20 flex flex-col gap-7 justify-center">
        <h2 class="feeds-title text-7xl pb-4">مقالات</h2>
          <div
            v-for="article in postsData.content"
            :key="article.id"
            class="articles-feed justify-center flex flex-row gap-7"
          >
            <ArticleCard
              :articleCover="articleCover"
              :articleTitle="article.title"
              :articleDescription="article.content"
              :articleDate="article.createdAt"
              :articleSlug="article.slug"
            />
          </div>
      </section>
  </Transition>
</template>

<style>
.feeds-title {
  font-family: 'oi';
}
hr {
  width: 99%;
  color: var(--fifth-color);
  border-radius: 20px;
}
</style>
