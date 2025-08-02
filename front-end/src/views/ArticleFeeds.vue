<script>
import articleCover from '../assets/logo.png'
import ArticleCard from '../components/ArticleCard.vue'
import ArticleContent from '../views/ArticleContent.vue'
export default {
  components: {
    ArticleCard,
    ArticleContent
  },
  data() {
    return {
      userSignedIn: false,
      articleCover: articleCover,
      loading: true,
      postsData: {
        content: [],
        page: {
          size: 0,
          number: 0,
          totalElements: 0,
          totalPages: 0
        }
      }
    }
  },
  async mounted() {
    try {
      const data = await fetch("http://localhost:8080/posts/all", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${this.$cookies.get('token')}`
        },
      });
      this.postsData = await data.json();
      this.userSignedIn = true
      await new Promise(reslove => setTimeout(reslove, 1000))
      this.loading = false;

    } catch {
      throw new Error("not logged in")
    }
  }
}
</script>

<template>
  <Transition
      enter-active-class="transition-opacity duration-700"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
  >

    <div v-if="!userSignedIn" class="fixed h-screen w-screen top-0 right-0 flex flex-row gap-2 text-2xl justify-center
      items-center transition-all">
      <router-link
        class="bg-white text-amber-500 p-3 flex flex-row transition-all group
        hover:bg-amber-500 hover:text-white justify-between items-center w-60 h-25 rounded-4xl"
        to="/loginScreen"
      >
        <p class="grow-3 flex flex-row justify-center">سجل اولا</p>
        <div class="grow-1 rounded-4xl flex justify-center items-center h-full px-5 group-hover:text-amber-500 group-hover:bg-white text-lg text-white bg-amber-500 transition-all">
        <font-awesome-icon
          :icon="['fas', 'circle-left']"
        />
        </div>
      </router-link>
    </div>

    <div v-else-if="loading" class="fixed h-screen w-screen top-0 right-0 flex flex-row gap-2 text-2xl justify-center
      items-center transition-all">
      <font-awesome-icon :icon="['fas', 'circle-notch']" class="animate-spin" />
      <p>يحمل</p>
    </div>

      <section v-else class="pt-20 px-5 flex flex-col gap-7 justify-center">
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
