<template>
  <div class="col-md common-box input-box pt-4 me-2">
    <span>{{ label }}</span>
    <input type="text" :value="modelValue" :readonly="readonly" @input="onInput" />
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    modelValue: {
      type: String,
      default: '',
    },
    readonly: {
      type: Boolean,
      default: true,
    },
  },
  emits: ['update:modelValue'],
  methods: {
    onInput(event) {
      this.$emit('update:modelValue', event.target.value)
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/detail.css';
</style>
