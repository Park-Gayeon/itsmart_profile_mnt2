<!-- src/components/profile/WorkExperienceTable.vue -->
<template>
  <div>
    <header class="description mb-md-3">
      <div class="left-group">
        근무경력 <span name="wk_career">{{ formattedCareerSummary }}</span>
      </div>
    </header>

    <div class="table-responsive px-5">
      <table class="table table-hover">
        <colgroup>
          <col style="width: 10%" />
          <col style="width: auto" />
          <col style="width: 10%" />
          <col style="width: 15%" />
          <col style="width: 15%" />
          <col style="width: 15%" />
        </colgroup>
        <thead>
          <tr>
            <th scope="col">NO</th>
            <th scope="col">회사명</th>
            <th scope="col">직급</th>
            <th scope="col">담당업무</th>
            <th scope="col">입사일자</th>
            <th scope="col">퇴사일자</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-if="!experiences || experiences.length === 0">
            <td colspan="6" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
          <tr v-for="(work, index) in experiences" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ work.workPlace }}</td>
            <td>{{ work.workPosition }}</td>
            <td>{{ work.workAssignedTask }}</td>
            <td>{{ formatDate(work.workStartDate) }}</td>
            <td>{{ formatDate(work.workEndDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'WorkTable',
  props: {
    experiences: {
      type: Array,
      default: () => [],
    },
    careerSummary: {
      type: [Number, String],
      default: '',
    },
  },
  computed: {
    formattedCareerSummary() {
      const totalMonth = Number(this.careerSummary)
      if (!totalMonth || totalMonth <= 0) {
        return '0개월'
      }
      const year = Math.floor(totalMonth / 12)
      const months = totalMonth % 12

      let result = ''
      if (year > 0) result += `${year}년 `
      if (months > 0) result += `${months}개월`
      return result
    },
  },
  methods: {
    formatDate(dateStr) {
      if (!dateStr) return ''
      if (dateStr.length === 8) {
        // 'yyyyMMdd' → 'yyyy-MM-dd'
        return `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6)}`
      }
      return dateStr
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/detail.css';
</style>
