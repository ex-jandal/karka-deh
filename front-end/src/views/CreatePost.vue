<script setup lang="ts">
import { ref, shallowRef, onMounted } from 'vue';
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { useVueCookies } from '../components/Cookies';
import { useRouter } from 'vue-router';

import { VueMonacoEditor } from '@guolao/vue-monaco-editor'
const MONACO_EDITOR_OPTIONS = {
  // automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
  fontSize: 18,
}
const editor = shallowRef()

const cookies = useVueCookies();
const router = useRouter();
let maniualMode = ref<boolean>(false);

let notPubliched = ref<boolean>(true);
const postContent = ref(`<h2>اهلا</h2><p>ابدأ بالكتابة هنا...</p>`);
const postTitle = ref<string>();
const errorMessage = ref<string>();

const acritveModesClass = "h-13 px-5 grow relative flex items-center justify-center transition-all duration-300 pb-2 after:content-[''] after:absolute after:bottom-0 after:w-2/3 after:h-1 after:bg-fourth-color after:rounded-4xl before:content-[''] before:absolute before:bottom-0 before:w-full before:h-full before:bg-fourth-color before:rounded-xl before:opacity-10 before:p-5"
const unacritveModesClass = "h-13 grow px-5 relative flex items-center justify-center transition-all duration-300"

let maniualClass = ref(unacritveModesClass)
let normalClass = ref(acritveModesClass)
function switchManiualMode(): void {
  maniualMode.value = true;
  maniualClass.value = acritveModesClass
  normalClass.value = unacritveModesClass
}

function switchNormalMode(): void {
  maniualMode.value = false;
  normalClass.value = acritveModesClass
  maniualClass.value = unacritveModesClass
}

async function postThePost() {
  try {
    const data = await fetch('http://localhost:8080/posts', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.get('token')}`,
      },
      body: JSON.stringify({
        title: postTitle.value,
        content: postContent.value,
      })
    })
    if (data.ok) {
      notPubliched.value = false;
      await new Promise((resolve) => setTimeout(resolve, 1000))
      router.push('/article-feeds')
    } else {
      errorMessage.value = "العنوان محجوز.. قم بتغييره"
    }
  } catch {
    console.error('not logged in')
  }
}

</script>

<template>
  <transition
    enter-active-class="transition-opacity duration-300"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="transition-opacity duration-300"
    leave-to-class="opacity-0"
  >
    <form v-if="notPubliched" class="create-post" @submit.prevent="postThePost">
      <div class="h-18 flex flex-row gap-3 items-center px-5 border-fifth-color border-b-1">
        <button
          type="button"
          @click="switchNormalMode"
          :class="normalClass"
        >
          الوضع العادي
        </button>
        <button
          type="button"
          @click="switchManiualMode"
          :class="maniualClass"
        >
          الوضع المتقدم
        </button>
      </div>

      <div v-if="errorMessage" class="error-message">
        <font-awesome-icon :icon="['fas', 'circle-xmark']"/>
        <p>{{ errorMessage }}</p>
      </div>

      <div class="post-title">
        <textarea required v-model="postTitle" placeholder="العنوان" name="" class="post-title_area"></textarea>
      </div>

      <div v-if="maniualMode">
        <VueMonacoEditor
          v-model:value="postContent"
          theme="vs-dark"
          language="html"
          :options="MONACO_EDITOR_OPTIONS"
          height="500px"
          class="text-editor"
        />
      </div>

      <div v-else class="post-content">
        <div id="toolbar" class="toolbar">
          <button class="ql-bold"></button>
          <button class="ql-italic"></button>
          <button class="ql-underline"></button>
          <button class="ql-strike"></button>

          <button class="ql-script" value="sub"></button>
          <button class="ql-script" value="super"></button>

          <button class="ql-list" value="ordered"></button>
          <button class="ql-list" value="bullet"></button>
          <button class="ql-list" value="check"></button>

          <button class="ql-blockquote"></button>
          <button class="ql-code-block"></button>

          <button class="ql-direction" value="rtl"></button>
          <select class="ql-align"></select>
          <select class="ql-header">
            <option selected></option>
            <option value="2"></option>
            <option value="3"></option>
            <option value="4"></option>
            <option value="5"></option>
          </select>
        </div>
        <QuillEditor
          toolbar="#toolbar"
          class="post-content_editor rounded-b-3xl min-h-50"
          v-model:content="postContent"
          contentType="html"
          required
        >
        </QuillEditor>
      </div>

      <div class="post-button">
        <button class="post-button_post">
          <font-awesome-icon :icon="['fas', 'plus']"/>
          <span>نشر</span>
        </button>
      </div>
    </form>

    <div v-else class="post-postesd">
      <font-awesome-icon class="post-postesd_icon" :icon="['fas', 'circle-check']"/>
      <p class="post-posted_text">نُشر المقال</p>
    </div>
  </transition>
</template>

<style scoped>
.create-post {
  display: flex;
  flex-direction: column;
  gap: 50px;
  padding-top: 100px;
}
.error-message {
  padding: 10px 20px;
  height: 60px;
  background-color: var(--color-fourth-color);
  color: var(--color-red-500);
  /* text-shadow: 0px 0px 20px black; */
  box-shadow: 1px 1px 3px var(--color-gray-600);
  border-radius: 50px;
  display: flex;
  gap: 10px;
  flex-direction: row;
  align-items: center;
}
.post-title_area {
  border: 1px solid var(--color-fourth-color);
  border-radius: 24px;
  width: 100%;
  height: 50px;
  line-height: 30px;
  font-size: 18px;
  resize: none;
  padding: 10px;
  padding-right: 15px;
  display: flex;
  align-content: center;
}

.post-content_area {
  border: 1px solid var(--color-fourth-color);
  border-radius: 24px;
  width: 100%;
  min-height: 200px;
  direction: ltr;
  font-size: 18px;
  resize: none;
  padding: 20px;
}
.toolbar {
  border-top-right-radius: 24px;
  border-top-left-radius: 24px;
  padding: 10px 10px;
}
.post-content_editor {
  direction: ltr;
  text-align: left;
}
.post-button {
  text-align: left;
}
.post-button_post {
  background-color: var(--color-third-color);
  padding: 15px 40px;
  border-radius: 30px;
  transition: all ease-in 70ms;
}

.post-button_post:hover {
  background-color: var(--color-fourth-color);
  color: var(--color-third-color);
  scale: 110%;
  padding: 15px 40px;
  border-radius: 23px;
}
.post-postesd {
  position: absolute;
  top: 0;
  right: 0;
  height: 100vh;
  background-color: var(--color-green-500);
  width: 100%;
  margin: 0px;
  display: flex;
  gap: 10px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.post-postesd_icon {
  font-size: 70px;
}
.post-posted_text {
  font-size: 20px;
}
.text-editor {
  direction: ltr;
  text-align: left;
  overflow: hidden;
  border-radius: 20px;
  /* border: 5px solid white; */
  /* background-color: var(--color-gruvbox-dark0); */
}
</style>
