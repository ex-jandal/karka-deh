<script>
import Navbar from './components/Navbar.vue'
import LoginScreen from './views/SigninScreen.vue'

document.documentElement.lang='ar'
document.documentElement.dir='rtl'

export default {
  components: {
    Navbar,
    LoginScreen,
  },
  methods() {
  }

}
</script>

<template>
  <Navbar />
    <div class="main">
      <router-view v-slot="{ Component }">
        <Transition
          enter-active-class="transition-opacity duration-700"
          enter-from-class="opacity-0"
          enter-to-class="opacity-100"
        >
          <component :is="Component" />
        </Transition>
      </router-view>
    </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Oi&family=Playpen+Sans+Arabic:wght@100..800&display=swap');
:root {
  --main-color: #460809;
  --second-color: #6a1a19;
  --therd-color: #eebf11;
  --forth-color: #f6f6f5;
  --fifth-color: #976c69;
  --main-font: 'Playpen Sans Arabic', cursive;
  --title-font: 'oi';
}

body {
  font-family: var(--main-font);
}

.main {
  height: calc(100vh - 10px);
  margin: auto;
  max-width: 1024px;
  margin-top: 85px;
  /* color: white; */
  /*   background-color: white; */
  /*   box-shadow: 0px 0px 10px black inset; */
  /* border: 1px solid black; */
  /*   border-radius: 20px; */
}
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease 0.3s; /* 0.3s delay */
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: scale(0.9);
}
</style>
