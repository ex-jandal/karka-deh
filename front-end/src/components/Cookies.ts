// src/composables/useVueCookies.js

// Import the library directly
import VueCookies from 'vue-cookies'

export function useVueCookies() {
  const set = (name: string, value: string, expireTimes: string) => {
    VueCookies.set(name, value, expireTimes)
  }

  const get = (name: string) => {
    return VueCookies.get(name)
  }

  const remove = (name: string) => {
    VueCookies.remove(name)
  }

  const isKey = (value: string) => {
    return VueCookies.isKey(value)
  }

  return {
    set,
    get,
    isKey,
    remove,
  }
}
