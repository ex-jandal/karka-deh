<script setup>
import Navbar from './components/Navbar.vue'
document.documentElement.lang = 'ar';
document.documentElement.dir = 'rtl';
</script>
<script>
export default {
  data() {
    return {
      number: 0,
    }
  },
  methods: {
    increamnt() {
      this.number++
    }
  }
}
</script>
<script setup>
</script>

<template>
<Navbar />
<div class="main flex flex-col justify-center items-center sm:mr-20 mr-2 ml-2 sm:mt-1
    mt-20">
  <h1>Hello Mr.Satan</h1>
  <button class="bg-red-950 text-white" @click="increamnt">count: {{ number }}</button>
</div>
</template>

<style>
body {
  background-color: #100203;
}

button {
  width: 100px;
}

.main {
  height: calc(100vh - 10px);
  /* color: white; */
  background-color: white;
  box-shadow: 0px 0px 10px black inset;
  /* border: 1px solid black; */
  border-radius: 20px;
}
</style>
