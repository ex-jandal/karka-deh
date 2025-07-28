<script>
export default {
  // data() {
  //   return {
  //     articleCover: '../assets/logo.png',
  //     articleTitle: 'عنوان',
  //     articleDescription: 'هنا وصف عن المقالة',
  //     articleDate: '2000/2/2',
  //   }
  // },
  props: {
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
    }
  }
}
</script>

<template>
  <div class="article-card h-80 w-70 flex flex-col justify-between border-1 rounded-3xl
    overflow-hidden">
    <img class="overflow-hidden mx-auto w-fit" :src="articleCover" alt="article cover">

    <div class="article-text p-2">
      <h3 class="text-white text-2xl">{{ articleTitle }}</h3>
      <p class="article-description">{{ articleDescription }}</p>
      <span class="article-date">{{ articleDate }}</span>
    </div>

  </div>

</template>

<style>
.article-description {
  color: #eeeeee;
}
.article-date {
  color: #cccccc;
}

</style>
