<template>
  <VueFinalModal
    :z-index-fn="zIndexFn"
    class="modal-wrapper"
    content-class="modal-content"
    @click-outside="onClickCloseButton"
  >
    <div class="modal-header">
      <h5 class="modal-title">{{ title }}</h5>
    </div>
    <div class="modal-body">
      <p>{{ message }}</p>
    </div>
    <div class="modal-footer">
      <button class="btn btn-secondary" @click="onClickCancelButton">
        {{ cancelButtonTitle }}
      </button>
      <button class="btn btn-primary" @click="onClickConfirmButton">
        {{ confirmButtonTitle }}
      </button>
    </div>
  </VueFinalModal>
</template>

<script>
import { VueFinalModal } from 'vue-final-modal'

export default {
  components: { VueFinalModal },
  props: {
    message: { type: String, required: true },
    title: { type: String, default: 'Confirm' },
    confirmButtonTitle: { type: String, default: 'Ok' },
    cancelButtonTitle: { type: String, default: 'Cancel' },
    onConfirm: Function,
    onCancel: Function,
  },
  methods: {
    zIndexFn({ index }) {
      return 100000 + index * 2
    },
    onClickConfirmButton() {
      this.onConfirm?.()
      this.$emit('close')
    },
    onClickCancelButton() {
      this.onCancel?.()
      this.$emit('close')
    },
    onClickCloseButton() {
      this.onCancel?.()
      this.$emit('close')
    },
  },
}
</script>

<style scoped>
.modal-content {
  padding: 1rem;
  background-color: #fff;
  border-radius: 0.3rem;
}
</style>
