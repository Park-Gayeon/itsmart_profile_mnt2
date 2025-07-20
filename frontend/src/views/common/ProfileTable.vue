<template>
  <div class="col-md-10 g-0">
    <div class="row mb-2 g-0">
      <InfoBox label="이름" v-model="user.userNm" :readonly="true" />
      <InfoBox label="생년월일" v-model="user.userBirth" :readonly="true" />
      <InfoBox label="휴대전화" v-model="user.userPhone" :readonly="isViewMode" />
      <InfoBox label="이메일" :modelValue="`${user.userId}@itsmart.co.kr`" :readonly="true" />
    </div>
    <div class="row mb-2 g-0">
      <SelectBox
        label="소속"
        v-model="user.userDepartment"
        :codeId="'ORG'"
        :disabled="isViewMode"
      />
      <SelectBox
        label="직위/직급"
        v-model="user.userPosition"
        :codeId="'PSIT'"
        :disabled="isViewMode"
      />
      <InfoBox label="입사일자" :modelValue="user.hireDate" :readonly="true" />
      <InfoBox label="주소" :modelValue="user.userAddress" :readonly="isViewMode" />
    </div>
  </div>
</template>
<script>
import InfoBox from './InfoBox.vue'
import SelectBox from './SelectBox.vue'

export default {
  name: 'ProfileTable',
  components: { InfoBox, SelectBox },
  props: {
    user: {
      type: Object,
      required: true,
    },
    mode: {
      type: String,
      default: 'view',
    },
  },
  computed: {
    isViewMode() {
      return this.mode === 'view'
    },
  },
  emits: ['update-profile'],
  watch: {
    edu: {
      handler(newVal) {
        this.$emit('update:profile', newVal)
      },
      deep: true,
    },
  },
}
</script>
<style scoped>
@import '@/assets/css/detail.css';
</style>
