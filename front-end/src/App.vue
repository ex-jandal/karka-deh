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
  <button class="text-amber-500" v-on:click="increamnt()">{{ number }}</button>
  <p>hello world</p>
</template>

<style>
button {
  width: 100px;
  background-color: black;
}
</style>
