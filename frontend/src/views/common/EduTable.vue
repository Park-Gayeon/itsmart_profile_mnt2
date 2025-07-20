<template>
  <div class="row mb-2 g-0">
    <div v-if="!edu.length">
      <p>작성된 내용이 없습니다</p>
    </div>
    <div v-else class="col-md px-5 g-0">
      <div v-for="(item, index) in edu" :key="index" class="row mb-2 g-0">
        <SelectBox
          label="학교구분"
          v-model="item.schoolGubun"
          :codeId="'SCHL'"
          :disabled="isViewMode"
        />
        <InfoBox label="학교명" v-model="item.schoolNm" :readonly="isViewMode" />
        <InfoBox label="전공명" v-model="item.major" :readonly="isViewMode" />
        <InfoBox label="입학일자" v-model="item.schoolStartDate" :readonly="isViewMode" />
        <InfoBox label="졸업일자" v-model="item.schoolEndDate" :readonly="isViewMode" />
        <InfoBox label="학점" v-model="item.totalGrade" :readonly="isViewMode" />
        <SelectBox
          label="졸업상태"
          v-model="item.gradStatus"
          :codeId="'STUT'"
          :disabled="isViewMode"
        />
      </div>
    </div>
  </div>
</template>
<script>
import InfoBox from './InfoBox.vue'
import SelectBox from './SelectBox.vue'
export default {
  name: 'EduTable',
  components: { InfoBox, SelectBox },
  props: {
    edu: {
      type: Array,
      default: () => [],
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
  emits: ['update-edu'],
  watch: {
    edu: {
      handler(newVal) {
        this.$emit('update:edu', newVal)
      },
      deep: true,
    },
  },
}
</script>
<style scoped>
@import '@/assets/css/detail.css';
</style>
