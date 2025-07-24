<script>
export default {
  data() {
    return {
      barIcons: ['barIcons', 'relative', 'flex', 'items-center', 'justify-center', 'h-14',
        'shadow-xl', 'mr-2',
        'w-14', 'mt-2', 'mb-2', 'rounded-4xl', 'hover:rounded-xl', 'hover:scale-105', ' transition-all'
        ],
      iconContext: ['iconContext', 'w-auto', 'p-2', 'm-1', 'min-w-max', 'rounded-md',
        'right-14', 'hidden',
        'font-bold', 'duration-100', 'absolute']
    }
  }
}
</script>

<template>
  <nav class="fixed top-0 right-0 pr-2 flex-row m-2
              flex justify-between items-center gap-2 h-20 overflow-hidden">
    <div class="right-side shadow-sm flex flex-row pr-5 pl-4">
      <router-link to="" class="flex flex-row justify-center items-center">
        <div class="nav-logo ml-2">
          <img class="ml-2" src="../assets/logo.svg" alt="logo" width="45px">
        </div>
      </router-link>
      <router-link :class="barIcons" to="">
        <font-awesome-icon :icon="['fas', 'lines-leaning']" /><span
          :class="iconContext">مقالات</span>
      </router-link>
      <router-link :class="barIcons" to="">
        <font-awesome-icon :icon="['fas', 'newspaper']"/><span
          :class="iconContext">أخبار</span>
      </router-link>
    </div>

    <div class="left-side shadow-sm flex-row pr-5 pl-4 m-2 h-16 w-25">
      <router-like to="" class="user-info relative mr-2 mt-6 mb-2 ml-2 flex flex-row justify-center items-center ">
        <font-awesome-icon :icon="['fas', 'user']"/>
      </router-like>
    </div>
  </nav>

</template>
<style>
nav {
  width: calc(100% - 16px);
}

.right-side {
  background-color: var(--main-color);
  border-radius: 50px;
}

.barIcons {
  background-color: var(--second-color);
  color: var(--forth-color);
}

.barIcons:hover {
  background-color: var(--forth-color);
  color: var(--second-color);
}

.left-side {
  background-color: var(--therd-color);
  color: var(--forth-color);
  border-radius: 50px;
  /* height: 120%; */
}
</style>
