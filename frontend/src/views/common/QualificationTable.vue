<template>
  <div>
    <header class="description mb-md-3">
      <div class="left-group">자격사항</div>
    </header>
    <div class="table-responsive px-5">
      <table class="table table-hover mb-5">
        <colgroup>
          <col style="width: 10%" />
          <col style="width: auto" />
          <col style="width: 25%" />
          <col style="width: 15%" />
          <col style="width: 15%" />
        </colgroup>
        <thead>
          <tr>
            <th scope="col">NO</th>
            <th scope="col">자격증명</th>
            <th scope="col">발행기관</th>
            <th scope="col">취득일자</th>
            <th scope="col">만기일자</th>
          </tr>
        </thead>
        <tbody v-if="!qualification || qualification.length === 0">
          <tr>
            <td colspan="5" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
        </tbody>
        <tbody v-else class="table-group-divider">
          <tr v-for="(qualification, index) in qualification" :key="qualification.id || index">
            <td>{{ index + 1 }}</td>
            <td>{{ qualification.qualificationNm }}</td>
            <td>{{ qualification.issuer }}</td>
            <td>{{ formatDate(qualification.acquisitionDate) }}</td>
            <td>{{ formatDate(qualification.expiryDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QualificationTable',
  props: {
    qualification: {
      type: Object,
      default: () => ({}),
    },
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) return ''

      // yyyyMMdd 형식의 문자열을 yyyy-MM-dd 형식으로 변환
      if (dateString.length === 8) {
        const year = dateString.substring(0, 4)
        const month = dateString.substring(4, 6)
        const day = dateString.substring(6, 8)
        return `${year}-${month}-${day}`
      }

      // 이미 yyyy-MM-dd 형식이거나 다른 형식인 경우 그대로 반환
      return dateString
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/detail.css';
</style>
