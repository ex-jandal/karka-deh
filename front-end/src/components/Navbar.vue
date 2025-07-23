<script>
export default {
  data() {
    return {
      barIcons: ['barIcons', 'relative', 'flex', 'items-center', 'justify-center', 'h-14',
        'shadow-xl',
        'w-14', 'mt-2', 'mb-2', 'mr-2', 'text-red-50', 'rounded-3xl',
        'hover:text-red-950', 'hover:rounded-xl', 'hover:scale-105'
        ],
      iconContext: ['iconContext', 'w-auto', 'p-2', 'm-1', 'min-w-max', 'rounded-md',
        'right-14', 'hidden', 'sm:inline',
        'font-bold', 'duration-100', 'absolute']
    }
  }
}
</script>

<template>
  <nav class="fixed top-0 right-0 pr-2 sm:p-1 sm:w-20 sm:hover:w-44 sm:h-screen
              flex sm:flex-col gap-2 shadow-lg rounded-2xl flex-row w-screen h-20 overflow-hidden">
    <div class="karkadeh-logo border-solid sm:mt-2 pl-4 sm:pl-0 border-l-1 sm:border-l-0 sm:border-b-1 border-white flex items-center mb-1
      pb-1">
      <router-link :class="barIcons" to="">
        <font-awesome-icon :icon="['fas', 'glass-water']" /><span
          :class="iconContext">كركدية</span>
      </router-link>
    </div>
    <router-link :class="barIcons" to="">
      <font-awesome-icon :icon="['fas', 'home']" /><span
        :class="iconContext">الرئيسية</span>
    </router-link>
    <router-link :class="barIcons" to="">
      <font-awesome-icon :icon="['fas', 'user']"/><span
        :class="iconContext">من نحن</span>
    </router-link>
  </nav>
</template>

<style>
a:active {
  background-color: #cccccc;
  color: var(main-color);
}

nav {
  background-color: var(--main-color);
  color: #cccccc;
  font-family: var(--main-font);
  transition: all cubic-bezier(0.4, 0, 0.2, 1) 250ms;
}

.barIcons {
  transition: all cubic-bezier(0.4, 0, 0.2, 1) 250ms;
  transition-delay: .1s;
  color: var(--forth-color);
  background-color: var(--second-color);
}

.karkadeh-logo {
  color: var(--main-color);
}

.barIcons:hover {
  background-color: var(--fifth-color);
}
.barIcons a:active {
  background-color: var(--forth-color);

}

.iconContext {
  transition: all cubic-bezier(0.4, 0, 0.2, 1) 100ms;
  transition-delay: .1s;
}

/* nav:hover .barIcons { */
/*   min-width: 90%; */
/*   transition-delay: .1s; */
/*   padding-right: 1.2rem; */
/*   justify-content: start; */
/* } */
/* nav:hover .iconContext { */
/*   display: inline; */
/* } */
</style>
