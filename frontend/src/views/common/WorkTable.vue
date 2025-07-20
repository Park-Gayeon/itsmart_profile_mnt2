<!-- src/components/profile/WorkExperienceTable.vue -->
<template>
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
        <!-- view -->
        <template v-if="mode === 'view'">
          <tr v-if="!experiences.length">
            <td colspan="6" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
          <tr v-for="(item, index) in experiences" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.workPlace }}</td>
            <td>{{ item.workPosition }}</td>
            <td>{{ item.workAssignedTask }}</td>
            <td>{{ formatDate(item.workStartDate) }}</td>
            <td>{{ formatDate(item.workEndDate) }}</td>
          </tr>
        </template>
        <!-- edit -->
        <template v-else>
          <tr v-if="!experiences.length">
            <td colspan="6" class="figure-caption">작성된 내용이 없습니다</td>
          </tr>
          <tr v-for="(item, index) in experiences" :key="index">
            <td>{{ index + 1 }}</td>
            <td><input type="text" v-model="item.workPlace" /></td>
            <td><input type="text" v-model="item.workPosition" /></td>
            <td><input type="text" v-model="item.workAssignedTask" /></td>
            <td><input type="text" v-model="item.workStartDate" /></td>
            <td><input type="text" v-model="item.workEndDate" /></td>
          </tr>
        </template>
      </tbody>
    </table>
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
    mode: {
      type: String,
      default: 'view',
    },
  },
  emits: ['update-experiences'],
  watch: {
    qualification: {
      handler(newVal) {
        this.$emit('update:experiences', newVal)
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
