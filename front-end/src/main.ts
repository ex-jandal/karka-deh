import "./assets/main.css"
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
library.add(fas, fab)


import App from './App.vue'
import router from './router'

import VueCookies from 'vue-cookies'

const app = createApp(App)

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(VueCookies)
app.use(createPinia())
app.use(router)

app.mount('#app')
