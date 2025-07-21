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
<div class="main flex h-screen justify-center items-center mr-22 ml-2 mb-2 mt-2">
Hello Mr.Satan
</div>
</template>

<style>
body {
  background-color: #100203;
}
button {
  width: 100px;
  background-color: black;
}

.main {
  height: calc(100vh - 14px);
  /* color: white; */
  background-color: white;
  box-shadow: 0px 0px 5px black inset;
  border: 1px solid black;
  border-radius: 20px;
}
</style>
