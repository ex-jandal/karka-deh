<script setup lang="ts">
import Spiner from '@/components/Spiner.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useVueCookies } from '../components/Cookies'

const cookies = useVueCookies()

const router = useRouter()

const slug = ref<string>('')
const title = ref<string>('')
const content = ref<string>('')
const createdAt = ref<string>('')
const notFound = ref<boolean>(false)
const loading = ref<Boolean>(true)

async function getContect() {
  try {
    slug.value = `${router.currentRoute.value.params.slug}`
    const response = await fetch(`http://localhost:8080/posts/${slug.value}`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.get('token')}`,
      },
    })
    const article = await response.json()

    title.value = article.title
    content.value = article.content
    createdAt.value = `${new Date(article.createdAt).getFullYear()}/${new Date(article.createdAt).getMonth()}/${new Date(article.createdAt).getDay()}`

    await new Promise((resolve) => setTimeout(resolve, 500))
    loading.value = false
  } catch (err) {
    notFound.value = true
    console.error('Failed to load article')
  }
}

onMounted(() => {
  getContect()
})
</script>
<template>
  <div
    v-if="notFound"
    class="fixed top-0 right-0 h-screen w-screen flex flex-col gap-5 justify-center items-center text-2xl text-gray-400 font-bold"
  >
    <font-awesome-icon :icon="['fas', 'heart-crack']" class="text-5xl animate-pulse" />
    <p class="">للأســـف.. المقال غير موجود.</p>
  </div>

  <Spiner v-else-if="loading" />

  <section v-else class="article pt-20 flex flex-col justify-center">
    <h2 class="text-6xl bold pb-3 leading-18">{{ title }}</h2>
    <span class="text-gray-400 pb-2 border-b-1 mb-4 border-white">{{ createdAt }}</span>
    <div class="article-content pt-5 flex flex-col gap-1" v-html="content"></div>
  </section>
</template>

<style>
.article-content h2 {
  padding-top: 20px;
  font-size: var(--text-2xl);
  font-weight: bold;
}

.article-content h3 {
  padding-top: 20px;
  font-size: var(--text-xl);
  font-weight: bold;
}
</style>
