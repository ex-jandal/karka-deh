<script setup lang="ts">
import Navbar from '@/components/Navbar.vue'

document.documentElement.lang = 'ar'
document.documentElement.dir = 'rtl'
</script>

<template>
  <Navbar />
  <div class="main text-fourth-color pt-23 px-10 mx-auto">
    <router-view v-slot="{ Component }">
      <transition
        mode="out-in"
        enter-active-class="transition-all duration-400"
        enter-from-class="opacity-0"
        enter-to-class=" opacity-100"
        leave-active-class="duration-300"
        leave-to-class="opacity-0 translate-y-8"
      >
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Oi&family=Playpen+Sans+Arabic:wght@100..800&display=swap');
body {
  font-family: var(--font-main);
  background-color: var(--color-second-color);
}
.main {
  max-width: var(--width-body-size);
}
</style>
