<script setup lang="ts">
import { defineEmits } from 'vue'
import { useVueCookies } from '../components/Cookies'

const cookies = useVueCookies();

const emit = defineEmits<{
  (closeLogout: 'close')
}>()

function closeLogout() {
  emit('close')
}

function logout(): void {
  cookies.remove('token')
  cookies.remove("loginUsername")
  document.location.reload();
}

</script>

<template>
  <div class="conform-logout absolute top-0 right-0 h-screen w-screen flex justify-center items-center z-11 backdrop-blur-xl">
    <button
      @click="closeLogout"
      class="absolute top-0 right-0 h-screen w-screen z-[-1]"
    >
    </button>
    <div class="bg-second-color p-7 pt-0 h-70 w-[70%] rounded-4xl flex flex-col">
      <div class="h-[80%] text-gray-400 flex flex-col items-center justify-evenly">
        <font-awesome-icon class="text-5xl" :icon="['fas', 'warning']"/>
        <p class="text-center text-sm sm:text-lg">
          هل تريد حقا تسجيل خروجك من كركدية؟
        </p>
      </div>
      <div class="flex gap-5 h-[20%] text-sm sm:text-lg flex-row">
        <button
          @click="closeLogout"
          class="grow bg-third-color rounded-2xl cursor-pointer hover:scale-110 transition-all"
        >إلغاء</button>
        <button
          @click="logout"
          class="grow bg-fifth-color rounded-2xl cursor-pointer hover:scale-110 transition-all"
        >تأكيد</button>
      </div>
    </div>
  </div>
</template>

<style>
.conform-logout {
  background-color: rgba(0, 0, 0, .7);
}
</style>
