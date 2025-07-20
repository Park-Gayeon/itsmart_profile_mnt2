<template>
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
      <tbody class="table-group-divider">
        <!-- view -->
        <template v-if="mode === 'view'">
          <tr v-if="!qualification.length">
            <td colspan="5" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
          <tr v-for="(qualification, index) in qualification" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ qualification.qualificationNm }}</td>
            <td>{{ qualification.issuer }}</td>
            <td>{{ formatDate(qualification.acquisitionDate) }}</td>
            <td>{{ formatDate(qualification.expiryDate) }}</td>
          </tr>
        </template>
        <!-- edit -->
        <template v-else>
          <tr v-if="!qualification.length">
            <td colspan="5" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
          <tr v-for="(item, index) in qualification" :key="index">
            <td>{{ index + 1 }}</td>
            <td>
              <input type="text" v-model="item.qualificationNm" />
            </td>
            <td>
              <input type="text" v-model="item.issuer" />
            </td>
            <td>
              <input v-model="item.acquisitionDate" />
            </td>
            <td>
              <input v-model="item.expiryDate" />
            </td>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'QualificationTable',
  props: {
    qualification: {
      type: Array,
      default: () => [],
    },
    mode: {
      type: String,
      default: 'view',
    },
  },
  emits: ['update-qualification'],
  watch: {
    qualification: {
      handler(newVal) {
        this.$emit('update:qualification', newVal)
      },
      deep: true,
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
