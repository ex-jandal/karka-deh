<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useVueCookies } from '../components/Cookies';

const cookies = useVueCookies();

const username = ref<string>(null);
console.log(cookies.get('loginUsername'))
const barIcons = ref<object>([
  'barIcons',
  'bg-second-color',
  'text-fourth-color',
  'relative',
  'flex',
  'items-center',
  'justify-center',
  'h-14',
  'shadow-lg',
  'mr-2',
  'w-14',
  'my-2',
  'rounded-4xl',
  'hover:bg-fourth-color',
  'hover:text-second-color',
  'hover:rounded-3xl',
  'hover:scale-107',
  'transition-all',
]);
const iconContext = ref<object>([
  'iconContext',
  'w-auto',
  'p-2',
  'm-1',
  'min-w-max',
  'rounded-md',
  'right-14',
  'hidden',
  'font-bold',
  'duration-100',
  'absolute',
]);

function checkUsername() {
  try {
    username.value = cookies.get('loginUsername');
  } catch {
    console.error("Not loggedIn")
  }
}
onMounted(() => {
    checkUsername();
})
</script>

<template>
  <nav class="w-full z-9 fixed top-0 right-0 px-5 flex-row m-2 flex justify-between items-center
    gap-2 h-22">
    <div class="right-side bg-main-color rounded-[50px] shadow-2xl flex flex-row pr-5 pl-3">
      <router-link
        to="/"
        class="flex flex-row justify-center items-center"
      >
        <div class="nav-logo ml-2">
          <img
            class="ml-2"
            src="../assets/logo.png"
            alt="logo"
            width="45px"
          />
        </div>
      </router-link>
      <router-link
        :class="barIcons"
        to="/article-feeds"
        active-class="bg-third-color text-main-color"
      >
        <font-awesome-icon :icon="['fas', 'lines-leaning']" /><span :class="iconContext">مقالات</span>
      </router-link>
      <router-link
        :class="barIcons"
        to="/me"
        active-class="bg-third-color text-main-color"
      >
        <font-awesome-icon :icon="['fas', 'newspaper']" /><span :class="iconContext">أخبار</span>
      </router-link>
    </div>

    <router-link v-if="username === null"
      to="/login-screen"
      class="relative"
    >
      <div class="left-side bg-third-color text-fourth-color transition-all shadow-lg flex flex-row justify-center gap-3 items-center
        m-2 h-16 w-20 sm:w-40 rounded-4xl hover:bg-fourth-color hover:text-third-color hover:rounded-3xl hover:scale-105"
      >
        <font-awesome-icon :icon="['fas', 'user']" />
        <span class="hidden sm:inline">سجل الدخول</span>
      </div>
    </router-link>
    <div v-else
      class="relative"
    >
      <div class="left-side bg-third-color text-fourth-color transition-all shadow-sm flex flex-row justify-center gap-3 items-center
        m-2 h-16 w-20 sm:w-40 rounded-4xl hover:bg-fourth-color hover:text-third-color hover:rounded-3xl hover:scale-105">
        <font-awesome-icon :icon="['fas', 'user']" />
        <span class="hidden sm:inline">{{ username }}</span>
      </div>
    </div>
  </nav>
</template>
