<script setup lang="ts">
  const props = defineProps({
    articleCover: {
      type: String,
      required: true
    },
    articleTitle: {
      type: String,
      required: true
    },
    articleDescription: {
      type: String,
      required: true
    },
    articleDate: {
      type: String,
      required: true
    },
    articleSlug: {
      type: String
    }
  });
</script>

<template>
  <router-link :to="`/article-feeds/${articleSlug}`" :slug="articleSlug"
    class="article-card h-40 w-full flex flex-row justify-start items-center border-1 rounded-3xl
    overflow-hidden hover:bg-[#976c69] hover:border-0 hover:scale-102 transition-all">
    <img class="overflow-hidden w-40 p-2 " :src="articleCover" alt="article cover">

    <div class="article-text p-4 h-full w-full flex flex-col justify-center overflow-hidden">
      <h3 class="text-white text-2xl overflow-hidden">{{ articleTitle }}</h3>
      <p v-html="articleDescription" class="article-description h-5 overflow-hidden"></p>
      <span class="article-date">{{ `${new Date(articleDate).getFullYear()}/${new Date(articleDate).getMonth()}/${new Date(articleDate).getDay()}` }}</span>

    </div>
  </router-link>

</template>

<style>
.article-description {
  color: #eeeeee;
}
.article-date {
  color: #cccccc;
}

</style>
