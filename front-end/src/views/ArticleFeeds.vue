<script setup>
import articleCover from '../assets/logo.png'
import ArticleCard from '../components/ArticleCard.vue'
</script>

<script>
export default {

}
</script>

<template>
  <section class="pt-20 px-5 flex flex-col justify-center">
    <h2 class="text-7xl pb-2">مقالات</h2>
    <hr class="horisantl-row pb-10" />

    <div class="articles-feed justify-center flex flex-row flex-wrap gap-7">
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
      <ArticleCard :articleCover="articleCover" articleTitle="عنوان من خارج"
        articleDescription="وصف خارجي" articleDate="2002"/>
    </div>
  </section>
</template>

<style>
h2 {
  font-family: 'oi';
}
hr {
  width: 99%;
  color: var(--fifth-color);
  border-radius: 20px;
}
</style>
