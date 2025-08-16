<script setup lang="ts">
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'
import { useVueCookies } from '../components/Cookies'

let cookies = useVueCookies()
const router = useRouter()

const loginUsername = ref<string>('')
const loginPassword = ref<string>('')
let loginMessage = ref<string>('')

async function login() {
  const loginResponse = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      username: loginUsername.value,
      password: loginPassword.value,
    }),
  })

  const loginData = await loginResponse.json()

  if (loginResponse.ok) {
    cookies.set('token', loginData.token, '7d')
    cookies.set('loginUsername', loginUsername.value, '7d')
    loginMessage.value = 'Logged in successfuly'
    router.push('/article-feeds')
    window.location.reload()
  } else {
    loginMessage.value = 'Wrong username or Password'
  }
}
</script>

<template>
  <div
    class="login-section w-full sm:w-120 flex flex-col justify-center items-center sm:rounded-4xl"
  >
    <div class="flex justify-center items-center text-6xl h-90 w-full">
      <font-awesome-icon :icon="['fas', 'right-to-bracket']" />
    </div>
    <div
      class="login-form bg-fifth-color p-10 rounded-4xl flex flex-col gap-7 items-center justify-between w-full"
    >
      <div
        class="login-oauth border-b-1 border-gray-300 pb-5 flex flex-row justify-evenly w-full text-3xl"
      >
        <button
          class="cursor-pointer flex flex-row items-center h-11 p-1"
          @click="$emit('oauthGoogle')"
        >
          <font-awesome-icon :icon="['fab', 'google']" />
        </button>
        <button
          class="cursor-pointer flex flex-row items-center h-11 p-1"
          @click="$emit('oauthGithub')"
        >
          <font-awesome-icon :icon="['fab', 'github']" />
        </button>
      </div>

      <form @submit.prevent="login" class="w-full grow flex flex-col gap-4" action="">
        <div class="input-feild flex flex-col">
          <label
            for="username"
            class="absolute text-black-100 flex flex-row justify-between items-center text-black bg-transparent px-4 pt-4"
          >
            <font-awesome-icon :icon="['fas', 'envelope']" />
          </label>
          <input
            id="username"
            v-model="loginUsername"
            class="p-2 pl-10 block rounded-4xl bg-white text-black border-2"
            type="text"
            required
            placeholder="Username"
          />
        </div>
        <div class="relative input-feild flex flex-col">
          <label
            for="password"
            class="absolute flex flex-row justify-between items-center text-black px-4 pt-4"
          >
            <font-awesome-icon :icon="['fas', 'lock']" />
          </label>
          <input
            id="password"
            v-model="loginPassword"
            class="p-2 pl-10 border-2 block rounded-4xl bg-white text-black"
            type="password"
            required
            placeholder="Password"
          />
        </div>

        <button
          class="submit bg-third-color mt-2 cursor-pointer p-2 block rounded-4xl hover:rounded-xl hover:scale-105 hover:bg-fourth-color hover:text-third-color transition-all"
          type="submit"
        >
          Login
        </button>
      </form>
      <span class="text-red-900">{{ loginMessage }}</span>
      <button
        class="cursor-pointer underline text-gray-200"
        @click="$emit('switch2signin')"
        type="button"
      >
        SignIn
      </button>
    </div>
  </div>
</template>

<style>
.login-form {
  direction: ltr;
}
</style>
