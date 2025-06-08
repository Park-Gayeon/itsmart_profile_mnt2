<template>
  <VueFinalModal
    v-model="show"
    class="common-popup"
    content-class="common-popup-content"
    :z-index-fn="zIndexFn"
  >
    <div class="modal-header">
      <h2>{{ title }}</h2>
      <button class="modal-close-btn" @click="onClickCloseButton()">
        <img :src="closeBtn" alt="close button" />
      </button>
    </div>

    <div class="modal-body">
      <p>{{ message }}</p>
    </div>

    <div class="btn-box">
      <button class="btn btn-primary" @click="onClickOkButton()">
        {{ okButtonTitle }}
      </button>
    </div>
  </VueFinalModal>
</template>

<script>
import { VueFinalModal } from 'vue-final-modal'
import closeBtn from '@/assets/images/etc/icon_modal_close.png'

export default {
  components: { VueFinalModal },
  props: {
    message: { type: String, required: true },
    title: { type: String, default: '알림' },
    okButtonTitle: { type: String, default: '확인' },
    onOk: { type: Function, default: null },
  },

  emits: ['update:modelValue', 'closed'],

  data() {
    return {
      closeBtn,
      show: true,
    }
  },

  methods: {
    zIndexFn({ index }) {
      return 100000 + index * 2
    },
    onClickOkButton() {
      // onOk 함수가 존재하고 함수타입인지 확인 후 실행
      if (this.onOk && typeof this.onOk === 'function') {
        try {
          this.onOk()
        } catch (error) {
          console.error('onOk 함수 실행 중 오류:', error)
        }
      }

      // 모달 닫기
      this.closeModal()
    },
    onClickCloseButton() {
      this.closeModal()
    },

    closeModal() {
      this.show = false
      this.$emit('update:modelValue', false)
      this.$emit('closed')
    },

    onModalClosed() {
      // 모달이 완전히 닫힌 후 추가 정리 작업
      this.$emit('closed')
    },
  },
}
</script>

<style>
.common-popup {
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  position: fixed !important;
  z-index: 200;
  left: 0 !important;
  top: 0 !important;
  width: 100% !important;
  height: 100% !important;
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(4px);
  padding: 20px;
  margin: auto;
}

.common-popup-content {
  position: relative;
  width: 100%;
  max-width: 500px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  animation: modalFadeIn 0.3s ease-out;
}

@keyframes modalFadeIn {
  from {
    opacity: 0;
    transform: scale(0.9) translateY(-20px);
  }
  to {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 32px 0;
  border-bottom: none;
}

.modal-header h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
}

.modal-close-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border: none;
  background: transparent;
  border-radius: 6px;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.2s ease;
}

.modal-close-btn:hover {
  background: #f3f4f6;
  color: #374151;
}

.modal-close-btn:active {
  transform: scale(0.95);
}

.modal-body {
  padding: 24px 32px;
  text-align: center;
}

.modal-body p {
  margin: 0;
  font-size: 16px;
  line-height: 1.5;
  color: #4b5563;
  word-break: break-word;
  white-space: pre-line;
}

.btn-box {
  padding: 0 32px 32px;
  text-align: center;
  border-top: 1px solid #e5e7eb;
  padding-top: 24px;
  margin-top: 8px;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 120px;
  height: 44px;
  padding: 0 24px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  text-decoration: none;
}

.btn-primary {
  background: #3b82f6;
  color: white;
}

.btn-primary:hover {
  background: #2563eb;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.btn-primary:active {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(59, 130, 246, 0.3);
}

.btn-primary:focus {
  outline: none;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .common-popup {
    padding: 16px;
  }

  .common-popup-content {
    max-width: none;
    margin: 0;
  }

  .modal-header {
    padding: 20px 24px 0;
  }

  .modal-header h2 {
    font-size: 18px;
  }

  .modal-body {
    padding: 20px 24px;
  }

  .modal-body p {
    font-size: 15px;
  }

  .btn-box {
    padding: 0 24px 24px;
    padding-top: 20px;
  }

  .btn {
    width: 100%;
    min-width: auto;
  }
}
</style>
