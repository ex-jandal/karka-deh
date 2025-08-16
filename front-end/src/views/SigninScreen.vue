<script setup lang="ts">
import Login from '../components/Login.vue'
import Signin from '@/components/Signin.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useVueCookies } from '../components/Cookies'

const cookies = useVueCookies()
const router = useRouter()

const showLogin = ref<Boolean>(true)

function isLoggedin() {
  try {
    if (cookies.isKey('token')) {
      router.push('/article-feeds')
    }
  } catch {
    console.log('Not LoggedIn')
  }
}
function switch2signin() {
  showLogin.value = false
}
function switch2login() {
  showLogin.value = true
}

onMounted(() => {
  isLoggedin()
})
</script>

<template>
  <div class="flex flex-col justify-center items-center">
    <transition
      mode="out-in"
      enter-active-class="transition-all duration-400"
      enter-from-class="opacity-0"
      enter-to-class=" opacity-100"
      leave-active-class="duration-300"
      leave-to-class="opacity-0 translate-y-8"
    >
      <Login v-if="showLogin" @switch2signin="switch2signin" />
      <Signin v-else @switch2login="switch2login" />
    </transition>
  </div>
</template>
