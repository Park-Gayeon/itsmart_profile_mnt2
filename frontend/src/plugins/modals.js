import { useModal } from 'vue-final-modal'
import ConfirmModal from '@/components/modals/ConfirmModal.vue'
import AlertModal from '@/components/modals/AlertModal.vue'

/**
 * 팝업(모달)을 위한 플러그인
 * - alert (this.$alertMsg())
 * - confirm (this.$confirmMsg())
 *
 * https://vuejs.org/guide/reusability/plugins.html
 */
export default {
  install: (app, options) => {
    /**
     * [Plugin] Confirm 팝업(모달)
     * @param {string} message
     * @param {function} onConfirm
     * @param {function} onCancel
     */
    app.config.globalProperties.$confirmMsg = (message, onConfirm, onCancel) => {
      const { open } = useModal({
        component: ConfirmModal,
        attrs: {
          message,
          onConfirm,
          onCancel,
          clickToClose: false,
          escToClose: false,
        },
      })

      open()
    }

    /**
     * [Plugin] Alert 팝업(모달)
     * @param {string} message
     * @param {function} onOk
     */
    app.config.globalProperties.$alertMsg = (message, onOk) => {
      const { open, close } = useModal({
        component: AlertModal,
        attrs: {
          message,
          onOk: () => {
            if (onOk && typeof onOk === 'function') {
              onOk()
            }
            close() // 명시적으로 모달 닫기
          },
        },
      })

      open()
    }
  },
}
