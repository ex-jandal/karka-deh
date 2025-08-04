<script setup lang="ts">
import { ref, onMounted, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';
import { useVueCookies } from '../components/Cookies';

const cookies = useVueCookies();
const router = useRouter();

const articlesAmount = ref(10);
const newsAmount = ref(20);
const usersAmount = ref(50);
</script>
<template>
  <section class="relative w-full p-3 flex flex-col h-screen">
    <div class="frist-view p-5 bg-fifth-color w-full flex flex-col-reverse md:flex-row
      justify-between rounded-4xl">
      <div class="view-rightside flex flex-col justify-center">
        <h1 class="title font-title text-[4rem] lg:text-[7rem]">كركديه</h1>
        <p class="text-lg">
          منصة تقنية متكاملة تجمع بين المقالات التعليمية والنقاشات المجتمعية المتخصصة. نهدف إلى بناء
          مجتمع عربي واعٍ يشارك المعرفة، ويطوّر مهاراته في مجالات البرمجة، الأمن السيبراني، الشبكات،
          وتكنولوجيا المعلومات عمومًا. من خلال المقالات المتجددة والمنتدى التفاعلي، يوفر كركديه بيئة
          مثالية لتبادل الخبرات، طرح الأسئلة، ومواكبة كل جديد في عالم التقنية.
        </p>
      </div>
      <div class="view-leftside items-center flex flex-row justify-center">
        <img
          id="logo"
          class="min-w-[400px] max-w-[100px] h-[400px]"
          src="../assets/logo.png"
          alt="logo"
        />
      </div>
    </div>

    <div class="last-desicion flex flex-col md:flex-row gap-5 mt-5">
      <div class="general-info text-fourth-color h-30 flex flex-row items-center grow-4
        bg-amber-500 rounded-[100px] overflow-hidden">
        <div class="general-info_item w-2.5 flex flex-col justify-center items-center
          text-sm border-l-gray-300 border-l-1  grow">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'lines-leaning']"
          />
          <span class="mb-2">مقال</span>
          <span>{{ articlesAmount }}</span>
        </div>
        <div class="general-info_item w-2.5 flex flex-col justify-center items-center
          text-sm border-l-gray-300 border-l-1 grow">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'newspaper']"
          />
          <span class="mb-2">خبر</span>
          <span>{{ newsAmount }}</span>
        </div>
        <div class="general-info_item w-2.5 flex flex-col justify-center items-center
          text-sm grow">
          <font-awesome-icon
            class="text-2xl"
            :icon="['fas', 'user']"
          />
          <span class="mb-2">مستخدم</span>
          <span>{{ usersAmount }}</span>
        </div>
      </div>

      <div class="direct-user text-sm h-20 md:h-30 flex flex-row justify-evenly items-center
        grow-1 transition-all group">
        <router-link
          class="bg-white text-amber-500 p-5 flex flex-row transition-all
          group-hover:bg-amber-500 group-hover:text-white justify-center items-center
          w-full h-full rounded-[100px]"
          to="/article-feeds"
        >
          <p class="grow-3 flex flex-row justify-center text-xl">مقالات</p>
          <div class="rounded-[50px] flex justify-center items-center w-14 h-14 md:h-20
            md:w-20
            text-3xl group-hover:text-amber-500 group-hover:bg-white text-white bg-amber-500 transition-all">
            <font-awesome-icon
              :icon="['fas', 'circle-left']"
              class="rotate-[-90deg] group-hover:rotate-0 duration-200 transition-all"
            />
          </div>
        </router-link>
      </div>
    </div>
  </section>
</template>
