<template>
  <div class="col-md common-box input-box pt-4 me-2">
    <span>{{ label }}</span>
    <select
      class="form-select noneBorder"
      :value="modelValue"
      :disabled="disabled"
      style="background-color: transparent"
      @change="$emit('update:modelValue', $event.target.value)"
    >
      <option v-for="opt in codeList" :key="opt.codeId" :value="opt.codeValue">
        {{ opt.codeValue }}
      </option>
    </select>
  </div>
</template>

<script>
import apiClient from '@/utils/axios'

export default {
  props: {
    label: String,
    modelValue: String, // v-model 사용을 위한 표준 prop
    codeId: String,
    disabled: Boolean,
  },
  data() {
    return {
      codeList: [],
      initialized: false,
    }
  },
  watch: {
    codeId: {
      immediate: true,
      handler(newVal) {
        this.getCodeList(newVal)
      },
    },
  },
  methods: {
    async getCodeList(codeId) {
      try {
        const response = await apiClient.get(`/common/code/${codeId}`)
        this.codeList = response.data.data || []

        this.initialized = true
      } catch (error) {
        console.error('API 호출 실패:', error)
      }
    },
  },
}
</script>
<style scoped>
@import '@/assets/css/detail.css';
</style>
