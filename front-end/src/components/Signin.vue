<script>
export default {

}
</script>

<template>
<div>

    <div class="login-section pb-[25px] h-screen w-screen sm:w-140 sm:h-190 flex flex-col
      justify-center items-center sm:rounded-4xl">
      <font-awesome-icon class="text-5xl grow" :icon="['fas', 'user']"/>
      <div class="login-form p-10 rounded-4xl flex flex-col gap-10 items-center ">

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

        <form class="w-full flex flex-col gap-4" action="" >

          <div class="input-feild flex flex-col sm:flex-row gap-4 sm:gap-[2%]">
            <label for="frist-name" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'user']"/>
            </label>
            <input id="frist-name" class="p-2 pl-10 block rounded-4xl bg-white
              text-black sm:w-[49%]
              border-2" type="text" required placeholder="frist name"/>

            <label for="last-name" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'user']"/>
            </label>
            <input id="last-name" class="p-2 pl-5 block rounded-4xl bg-white
              text-black sm:w-[49%]
              border-2" type="text" required placeholder="last name"/>
          </div>
          <div class="input-feild flex flex-col">
            <label for="username" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'envelope']"/>
            </label>
            <input id="username" class="p-2 pl-10 block rounded-4xl bg-white text-black
              border-2" type="text" required placeholder="username"/>
          </div>
          <div class="input-feild flex flex-col">
            <label for="email" class="absolute text-black-100 flex flex-row justify-between
              items-center text-black bg-transparent px-4
              pt-4">
              <font-awesome-icon :icon="['fas', 'envelope']"/>
            </label>
            <input id="email" class="p-2 pl-10 block rounded-4xl bg-white text-black
              border-2" type="email" required placeholder="email"/>
          </div>
          <div class="relative input-feild flex flex-col">
            <label for="password" class="absolute flex flex-row justify-between
              items-center text-black px-4 pt-4">
              <font-awesome-icon :icon="['fas', 'lock']"/>
            </label>
            <input id="password" class="p-2 pl-10 border-2 block rounded-4xl bg-white
              text-black" type="password" required placeholder="Password"/>
          </div>

          <input class="submit cursor-pointer p-2 block rounded-4xl hover:rounded-xl transition-all" type="submit" value="login">
        </form>
        <button class="cursor-pointer underline text-gray-200" @click="$emit('switch2login')"
          type="button">Login</button>
      </div>

    </div>
  </div>
</template>

<style>

</style>
