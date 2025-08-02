<script>
export default {
  data() {
    return {
      slug: '',
      title: '',
      content: '',
      createdAt: '',
      loading: true
    }
  },
  async mounted() {
    try {
      this.slug = this.$router.currentRoute.value.params.slug;
      const response = await fetch(`http://localhost:8080/posts/${this.slug}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.$cookies.get('token')}`
        }
      });
      const article = await response.json();
      this.title = article.title;
      this.content = article.content;
      this.createdAt = `${new Date(article.createdAt).getFullYear()}/${new Date(article.createdAt).getMonth()}/${new Date(article.createdAt).getDay()}`;
      this.loading = false;
    } catch (err) {
      console.error('Failed to load article:', err)
    }
  }
}
</script>

<template>
  <div v-if="loading">Loading...</div>
  <section v-else class="article pt-20 px-5 flex flex-col justify-center">
    <h2 class="text-7xl pb-1 wrap-anywhere">{{ title }}</h2>
    <span class="text-gray-400 pb-2 border-b-1 mb-4 border-white">{{ createdAt }}</span>
    <div class="article-content pt-5 flex flex-col gap-1" v-html="content"></div>
  </section>
</template>

<style >
.article-content h2{
  padding-top: 15px;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
