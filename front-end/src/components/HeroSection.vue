<script>
export default {
  data() {
    return {
      articlesAmount: 10,
      newsAmount: 20,
      usersAmount: 50,
    }
  },
  async mounted() {
    await this.checkUsername();
  },
  methods: {
    async checkUsername() {
      if (this.$cookies.get('loginUsername')) {
        this.$router.push('/article-feeds')
      }
    }
  },
}
</script>

<template>
  <section class="relative w-full p-3 flex flex-col h-screen">
    <div class="frist-view w-full flex flex-col-reverse md:flex-row justify-between p-5 rounded-4xl">
      <div class="view-rightside flex flex-col justify-center">
        <h1 class="title text-[4rem] lg:text-[7rem]">كركديه</h1>
        <p class="text-lg">
          منصة تقنية متكاملة تجمع بين المقالات التعليمية والنقاشات المجتمعية المتخصصة. نهدف إلى بناء
          مجتمع عربي واعٍ يشارك المعرفة، ويطوّر مهاراته في مجالات البرمجة، الأمن السيبراني، الشبكات،
          وتكنولوجيا المعلومات عمومًا. من خلال المقالات المتجددة والمنتدى التفاعلي، يوفر كركديه بيئة
          مثالية لتبادل الخبرات، طرح الأسئلة، ومواكبة كل جديد في عالم التقنية.
        </p>
      </div>
      <div class="view-leftside flex flex-row justify-center">
        <img
          id="logo"
          class="lg:p-10"
          src="../assets/logo.png"
          alt="logo"
        />
      </div>
    </div>

    <div class="last-desicion flex flex-col md:flex-row gap-5 mt-5 rounded-4xl">
      <div class="general-info flex flex-row items-center grow-5 bg-amber-500 rounded-4xl overflow-hidden">
        <div class="general-info_item">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'lines-leaning']"
          />
          <span class="mb-2">مقال</span>
          <span>{{ articlesAmount }}</span>
        </div>
        <div class="general-info_item">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'newspaper']"
          />
          <span class="mb-2">خبر</span>
          <span>{{ newsAmount }}</span>
        </div>
        <div class="general-info_item">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'user']"
          />
          <span class="mb-2">مستخدم</span>
          <span>{{ usersAmount }}</span>
        </div>
      </div>

      <div class="direct-user flex flex-row justify-evenly items-center grow-1 transition-all group">
        <router-link
          class="bg-white text-amber-500 p-5 flex flex-row transition-all group-hover:bg-amber-500 group-hover:text-white justify-center items-center w-full h-full rounded-4xl"
          to="/article-feeds"
        >
          <p class="grow-3 flex flex-row justify-center">مقالات</p>
          <div class="grow-1 rounded-4xl flex justify-center items-center h-full px-5 group-hover:text-amber-500 group-hover:bg-white text-lg text-white bg-amber-500 transition-all">
            <font-awesome-icon
              :icon="['fas', 'circle-left']"
            />
          </div>
        </router-link>
      </div>
    </div>
  </section>
</template>

<style>
body {
  background-color: var(--second-color);
  color: var(--forth-color);
}

.title {
  font-family: var(--title-font);
}

#logo {
  min-width: 200px;
  max-width: 500px;
}

.frist-view {
  background-color: var(--fifth-color);
}

.general-info_item {
  display: flex;
  border-left: 1px solid #e38200;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  flex-grow: 1;
  /* padding: 2rem; */
  height: 120px;
}
</style>
