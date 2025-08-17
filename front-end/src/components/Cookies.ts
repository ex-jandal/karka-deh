// src/composables/useVueCookies.js

// Import the library directly
import VueCookies from 'vue-cookies'

export function useVueCookies() {
  const set = (name: string, value: string, expireTimes: string): void => {
    VueCookies.set(name, value, expireTimes)
  }

  const get = (name: string): string => {
    return VueCookies.get(name)
  }

  const remove = (name: string): void => {
    VueCookies.remove(name)
  }

  const isKey = (value: string): boolean => {
    return VueCookies.isKey(value)
  }

  return {
    set,
    get,
    isKey,
    remove,
  }
}
