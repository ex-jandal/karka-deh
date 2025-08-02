<script>
import Login from '../components/Login.vue'
import Signin from '@/components/Signin.vue'
export default {
  components: {
    Login,
    Signin,
  },
  data() {
    return {
      showLogin: true
    }
  },
  mounted() {
    this.isLoggedin();
  },
  methods: {
    isLoggedin() {
      if (this.$cookies.isKey('token')) {
        this.$router.push('/article-feeds')
      }
    },
    switch2signin() {
      this.showLogin = false
    },
    switch2login() {
      this.showLogin = true
    },
  }

}
</script>

<template>

  <div class="login-screen flex flex-col justify-center items-center z-8 w-full
    transition-all
    h-full backdrop-blur-3xl">
    <Transition
      enter-active-class="transition-opacity duration-700"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
    >
      <Login v-if="showLogin" @switch2signin="switch2signin"/>
      <Signin v-else @switch2login="switch2login"/>
    </Transition>
  </div>

</template>

<style>
/* .login-screen::before { */
/*   content: ''; */
/*   position: absolute; */
/*   top: 0; */
/*   right: 0; */
/*   z-index: -1; */
/*   height: 100vh; */
/*   width: 100vw; */
/*   opacity: 65%; */
/*   backdrop-filter: blur(2000px); */
/*   background-color: black; */
/* } */
</style>
