<script setup lang="ts">
import { ref, defineProps } from 'vue'

const props = defineProps({
  articleCover: {
    type: String,
    required: true,
  },
  articleTitle: {
    type: String,
    required: true,
  },
  articleDescription: {
    type: String,
    required: true,
  },
  articleDate: {
    type: String,
    required: true,
  },
  articleSlug: {
    type: String,
  },
})

const dateForamt = new Date(props.articleDate)
const articleDate = ref<string>(
  `${dateForamt.getFullYear()}/${dateForamt.getMonth()}/${dateForamt.getDay()}`,
)
</script>

<template>
  <router-link
    :to="`/article-feeds/${articleSlug}`"
    :slug="articleSlug"
    class="article-card w-90 sm:h-75 sm:w-55 flex flex-col justify-between items-center
    bg-fifth-color rounded-3xl overflow-hidden hover:bg-fifth-color
    hover:translate-y-[-20px] hover:outline-4 hover:scale-110 hover:rounded-xl transition-all"
  >
    <div class="overflow-hidden flex justify-center items-center backdrop-opacity-75 w-full">
      <img class="object-cover w-full mx-auto" :src="articleCover" alt="article cover" />
    </div>

    <div
      class="article-text h-23 w-full px-5 py-2 flex flex-col justify-between grow overflow-hidden"
    >
      <h3 class="text-white text-md overflow-hidden">{{ articleTitle }}</h3>
      <p
        v-html="articleDescription"
        class="text-fourth-color text-sm hidden h-6 whitespace-nowrap overflow-hidden"
      ></p>

      <span class="text-gray-400 pt-2 text-sm">{{ articleDate }}</span>
    </div>
  </router-link>
</template>

<style scoped>
article-card {
  background-color: rgba(255, 255, 255, 1);
}
</style>
