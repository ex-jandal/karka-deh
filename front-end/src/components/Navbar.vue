<script>
export default {
  data() {
    return {
      barIcons: ['barIcons', 'relative', 'flex', 'items-center', 'justify-center', 'h-14',
        'w-14', 'mt-2', 'mb-2',
      'mx-auto', 'bg-red-950', 'text-red-50', 'rounded-3xl','hover:bg-white',
        'hover:text-red-950', 'hover:rounded-xl', 'hover:scale-105'
        ],
      iconContext: ['iconContext', 'w-auto', 'p-2', 'min-w-max', 'rounded-md', 'right-14',
        'font-bold', 'duration-100', 'hidden']
    }
  }
}
</script>

<template>
  <nav class="fixed top-0 right-0 w-20 m-1 p-1
              flex flex-col shadow-lg rounded-2xl">
    <router-link :class="barIcons" to="">
      <font-awesome-icon :icon="['fas', 'glass-water']" /><span
        :class="iconContext">كركدية</span>
    </router-link>
    <hr/>
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
  color: #100203;
}
nav {
  background-color: #100203;
  color: #cccccc;
  height: calc(100% - 10px);
  /* width: 64px; */

  transition: all cubic-bezier(0.4, 0, 0.2, 1) 250ms;
}
hr {
  width: 80%;
  margin: 5px auto;

  border-radius: 25px;
}

.barIcons {
  /* justify-content: start; */
  transition: all cubic-bezier(0.4, 0, 0.2, 1) 250ms;
  transition-delay: .1s;
}
.iconContext {
  transition: all cubic-bezier(0.4, 0, 0.2, 1) 100ms;
  transition-delay: .1s;
}
/* nav:hover { */
/*   width: 180px */
/* } */
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
