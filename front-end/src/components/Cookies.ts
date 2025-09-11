// src/composables/useVueCookies.js

import {inject} from 'vue'

// Import the library directly
import type { VueCookies } from 'vue-cookies'


export function useVueCookies() {
  const $cookies = inject<VueCookies>('$cookies')

  const set = (name: string, value: string, expireTimes: string): void => {
    $cookies?.set(name, value, expireTimes)
  }

  const get = (name: string): string => {
    return $cookies?.get(name)
  }

  const remove = (name: string): void => {
    $cookies?.remove(name);
  }

  const isKey = (value: string): boolean => {
    return $cookies?.isKey(value) ?? false
  }

  return {
    set,
    get,
    isKey,
    remove,
  }
}
