<script setup lang="ts">
import { ref, onMounted } from 'vue';

const signinUsername = ref<string>('');
const signinPassword = ref<string>('');
const signinConformPassword = ref<string>('')
const signinMessage = ref<string>('');

async function signin() {

  if (signinConformPassword.value != signinPassword.value) {
    signinMessage.value = "Not mattched Passwords.. Rewrite your password agine.";
    return;
  }
  const signinResponse = await fetch('http://localhost:8080/auth/signup', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({
      username: signinUsername.value,
      password: signinPassword.value,
    }),
  });

  if (signinResponse.ok) {
    signinMessage.value = 'Sign in successfuly..\nYou will be redirect after 5 seconed to login page';
    await new Promise(reslove => setTimeout(reslove, 5000))
    window.location.reload();

  } else {
    signinMessage.value = 'Signin failed.. You have entered a used username';
  }
}
</script>

<template>
  <div>
    <div class="login-section h-screen w-full sm:w-140 sm:h-220 flex flex-col
      justify-center items-center sm:rounded-4xl">
      <font-awesome-icon class="text-5xl grow" :icon="['fas', 'user']"/>
      <div class="login-form bg-fifth-color p-10 rounded-4xl flex flex-col gap-10 items-center ">

        <div class="login-oauth border-b-1 border-gray-300 pb-5 p-1 flex flex-row
          justify-center-safe gap-20 w-full text-3xl">
          <button class="cursor-pointer flex flex-row items-center h-11 p-1"
            @click="$emit('oauthGoogle')">
            <font-awesome-icon :icon="['fab', 'google']"/>
          </button>
          <button class="cursor-pointer flex flex-row items-center h-11 p-1"
            @click="$emit('oauthGithub')">
            <font-awesome-icon :icon="['fab', 'github']"/>
          </button>
        </div>

        <form @submit.prevent="signin" class="w-full flex flex-col gap-4" action="" >

          <div class="input-feild flex flex-col sm:flex-row gap-4 sm:gap-[2%]">
            <label for="frist-name" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'user']"/>
            </label>
            <input id="frist-name" class="p-2 pl-10 block rounded-4xl bg-white
              text-black sm:w-[49%]
              border-2" type="text" placeholder="frist name"/>

            <label for="last-name" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'user']"/>
            </label>
            <input id="last-name" class="p-2 pl-5 block rounded-4xl bg-white
              text-black sm:w-[49%]
              border-2" type="text" placeholder="last name"/>
          </div>
          <div class="input-feild flex flex-col">
            <label for="username" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'envelope']"/>
            </label>
            <input id="username" v-model="signinUsername" class="p-2 pl-10 block rounded-4xl bg-white text-black
              border-2" type="text" required placeholder="username"/>
          </div>
          <div class="input-feild flex flex-col">
            <label for="email" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'envelope']"/>
            </label>
            <input id="email" class="p-2 pl-10 block rounded-4xl bg-white text-black
              border-2" type="email" placeholder="email"/>
          </div>
          <div class="relative input-feild flex flex-col">
            <label for="password" class="absolute flex flex-row justify-between
              items-center text-black px-4 pt-4">
              <font-awesome-icon :icon="['fas', 'lock']"/>
            </label>
            <input id="password" v-model="signinPassword" class="p-2 pl-10 border-2 block rounded-4xl bg-white
              text-black" type="password" required placeholder="Password"/>
          </div>
          <div class="relative input-feild flex flex-col">
            <label for="conform-password" class="absolute flex flex-row justify-between
              items-center text-black px-4 pt-4">
              <font-awesome-icon :icon="['fas', 'lock']"/>
            </label>
            <input id="conform-password" v-model="signinConformPassword" class="p-2 pl-10 border-2 block rounded-4xl bg-white
              text-black" type="password" required placeholder="Conform Password"/>
          </div>

          <button class="submit bg-third-color text-fourth-color cursor-pointer p-2 block rounded-4xl hover:rounded-xl
          hover:scale-105 hover:bg-fourth-color hover:text-third-color transition-all" type="submit">Signin</button>
        </form>
        <span class="text-red-900">{{ signinMessage }}</span>
        <button class="cursor-pointer underline text-gray-200" @click="$emit('switch2login')"
          type="button">Login</button>
      </div>
    </div>
  </div>
</template>
