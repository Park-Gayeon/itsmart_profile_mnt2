<template>
  <VueFinalModal
    v-model="show"
    class="common-popup"
    content-class="common-popup-content"
    :z-index-fn="zIndexFn"
  >
    <div class="modal-header">
      <h2>비밀번호 변경</h2>
      <button class="modal-close-btn" @click="onClickCloseButton()">
        <img :src="closeBtn" alt="close button" />
      </button>
    </div>

    <div class="modal-body">
      <form @submit.prevent="handleSubmit">
        <div class="row-box row mb-0 g-0">
          <span>새 비밀번호</span>
          <input
            id="newPassword"
            v-model="formData.newPassword"
            type="password"
            class="form-input"
            :class="{ error: errors.newPassword }"
            placeholder="문자/숫자/특수문자 조합 8~20자 이상"
            required
          />
          <span v-if="errors.newPassword" class="error-message">
            {{ errors.newPassword }}
          </span>
        </div>

        <div class="row-box row mb-0 g-0">
          <span>새 비밀번호 확인</span>
          <input
            id="confirmPassword"
            v-model="formData.confirmPassword"
            type="password"
            class="form-input"
            :class="{ error: errors.confirmPassword }"
            placeholder="새 비밀번호를 다시 입력하세요"
            required
          />
          <span v-if="errors.confirmPassword" class="error-message">
            {{ errors.confirmPassword }}
          </span>
        </div>

        <div class="btn-box">
          <button type="submit" class="btn btn-success" :disabled="isLoading">
            {{ isLoading ? '저장 중...' : '저장' }}
          </button>
        </div>
      </form>
    </div>
  </VueFinalModal>
</template>

<script>
import { VueFinalModal } from 'vue-final-modal'
import { useAuthStore } from '@/stores/auth.js'
import apiClient from '@/utils/axios'
import closeBtn from '@/assets/images/etc/icon_modal_close.png'
import router from '@/router/index.js'

export default {
  name: 'ChangePasswordModal',
  components: {
    VueFinalModal,
  },
  props: {
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue', 'closed'],
  data() {
    return {
      authStore: useAuthStore(),
      closeBtn,
      formData: {
        newPassword: '',
        confirmPassword: '',
      },
      errors: {},
      isLoading: false,
    }
  },
  computed: {
    show: {
      get() {
        return this.modelValue
      },
      set(value) {
        this.$emit('update:modelValue', value)
      },
    },
  },
  watch: {
    show(newVal) {
      if (newVal) {
        this.resetForm()
      }
    },
  },
  methods: {
    zIndexFn({ index }) {
      return 100000 + index * 2
    },

    closeModal() {
      this.$emit('closed')
    },

    resetForm() {
      this.formData = {
        newPassword: '',
        confirmPassword: '',
      }
      this.errors = {}
    },

    onClickCloseButton() {
      this.closeModal()
    },

    validateForm() {
      this.errors = {}

      // 새 비밀번호 검증
      if (!this.formData.newPassword) {
        this.errors.newPassword = '새 비밀번호를 입력하세요.'
      } else if (this.formData.newPassword.length < 8) {
        this.errors.newPassword =
          '비밀번호는 8~20자 이내로, 문자/숫자/특수문자 조합으로 작성해주세요.'
      }

      // 비밀번호 확인 검증
      if (!this.formData.confirmPassword) {
        this.errors.confirmPassword = '비밀번호 확인을 입력하세요.'
      } else if (this.formData.newPassword !== this.formData.confirmPassword) {
        this.errors.confirmPassword = '비밀번호가 일치하지 않습니다.'
      }

      return Object.keys(this.errors).length === 0
    },

    async handleSubmit() {
      if (!this.validateForm()) {
        return
      }

      this.isLoading = true
      try {
        const response = await apiClient.post('/auth/password', {
          userId: this.authStore.username,
          userPw: this.formData.newPassword,
        })

        if (response.data.success) {
          this.$alertMsg(
            '비밀번호가 성공적으로 변경되었습니다. \n로그인 화면으로 이동합니다. ',
            () => this.closeModal(),
          )
        }
      } catch (error) {
        console.error('비밀번호 변경 오류:', error)
      } finally {
        this.isLoading = false
      }
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/pop.css';
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
  padding: 1.5rem;
  text-align: justify;
}

.modal-body p {
  margin: 0;
  font-size: 16px;
  line-height: 1.5;
  color: #4b5563;
  word-break: break-word;
}

.row-box span {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #333;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  transition:
    border-color 0.2s,
    box-shadow 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.1);
}

.form-input.error {
  border-color: #dc3545;
}

.error-message {
  display: block;
  margin-top: 0.25rem;
  font-size: 0.875rem;
  color: #dc3545;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  margin-top: 2rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
  min-width: 100px;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-cancel {
  background-color: #6c757d;
  color: white;
}

.btn-cancel:hover:not(:disabled) {
  background-color: #5a6268;
}

.btn-primary {
  background-color: #667eea;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background-color: #5a67d8;
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

  .alert-box {
    padding: 20px 24px;
  }

  .alert-box p {
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
