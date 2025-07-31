<script>
export default {
  data() {
    return {
      username: '',
      barIcons: [
        'barIcons',
        'relative',
        'flex',
        'items-center',
        'justify-center',
        'h-14',
        'shadow-sm',
        'mr-2',
        'w-14',
        'my-2',
        'rounded-4xl',
        'hover:rounded-3xl',
        'hover:scale-107',
        'transition-all',
      ],
      iconContext: [
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
      ],
    }
  },
  async mounted() {
    await this.checkUsername();
  },
  methods: {
    async checkUsername() {
      if (this.$cookies.get('loginUsername')) {
        this.username = this.$cookies.get('loginUsername');
        this.$router.push('/articlefeeds')
      } else {
        this.username = '';
      }
    }
  },
}
</script>

<template>
  <nav class="fixed top-0 right-0 pr-2 flex-row m-2 flex justify-between items-center gap-2 h-20 overflow-hidden">
    <div class="right-side shadow-sm flex flex-row pr-5 pl-3">
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
        to="/articleFeeds"
      >
        <font-awesome-icon :icon="['fas', 'lines-leaning']" /><span :class="iconContext">مقالات</span>
      </router-link>
      <router-link
        :class="barIcons"
        to="/ff"
      >
        <font-awesome-icon :icon="['fas', 'newspaper']" /><span :class="iconContext">أخبار</span>
      </router-link>
    </div>

    <router-link v-if="username === ''"
      to="/loginScreen"
      class="relative"
    >
      <div class="left-side transition-all shadow-sm flex flex-row justify-center gap-3 items-center
        m-2 h-16 w-20 sm:w-40 rounded-4xl hover:rounded-3xl hover:scale-105">
        <font-awesome-icon :icon="['fas', 'user']" />
        <span class="hidden sm:inline">سجل الدخول</span>
      </div>
    </router-link>
    <div v-else
      class="relative"
    >
      <div class="left-side transition-all shadow-sm flex flex-row justify-center gap-3 items-center
        m-2 h-16 w-20 sm:w-40 rounded-4xl hover:rounded-3xl hover:scale-105">
        <font-awesome-icon :icon="['fas', 'user']" />
        <span class="hidden sm:inline">{{ username }}</span>
      </div>
    </div>
  </nav>
</template>
<style>
nav {
  width: calc(100% - 16px);
  z-index: 9;
}

.right-side {
  background-color: var(--main-color);
  border-radius: 50px;
}

.barIcons {
  background-color: var(--second-color);
  color: var(--forth-color);
}

.barIcons.router-link-exact-active {
  color: var(--main-color);
  background-color: var(--therd-color);
}

.barIcons:hover {
  background-color: var(--forth-color);
  color: var(--second-color);
}

.left-side {
  background-color: var(--therd-color);
  color: var(--forth-color);
  /* height: 120%; */
}
.left-side:hover {
  background-color: var(--forth-color);
  color: var(--therd-color);
}
left-side.router-link-exact-active {
  background-color: var(--forth-color);
  color: var(--therd-color);
}

</style>
