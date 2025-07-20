<template>
  <div class="table-responsive px-5">
    <!-- 현재 진행중인 사업 내역 -->
    <header class="basic-medium"><span class="star">*</span>현재 진행중인 사업 내역</header>
    <table class="table table-hover mb-5 twin">
      <colgroup>
        <col style="width: 5%" />
        <col style="width: 15%" />
        <col style="width: 24%" />
        <col style="width: 15%" />
        <col style="width: 25%" />
        <col style="width: 10%" />
        <col style="width: 6%" />
      </colgroup>
      <thead>
        <tr>
          <th scope="col">NO</th>
          <th scope="col">발주처</th>
          <th scope="col">사업명</th>
          <th scope="col">참여기간</th>
          <th scope="col">담당업무</th>
          <th scope="col">역할</th>
          <th scope="col">기술</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-if="currentProjects.length === 0">
          <td colspan="7" class="figure-caption text-center">진행중인 사업이 없습니다</td>
        </tr>
        <tr
          v-else
          v-for="(project, index) in currentProjects"
          :key="`current-${project.projectSeq}`"
        >
          <td>{{ index + 1 }}</td>
          <td>{{ project.projectClient }}</td>
          <td>{{ project.projectNm }}</td>
          <td>
            {{ formatDate(project.projectStartDate) }}
            ~ {{ formatDate(project.projectEndDate) }}
          </td>
          <td>
            {{ project.assignedTaskLarNm }}
            <span v-if="project.assignedTaskMidNm"> > {{ project.assignedTaskMidNm }} </span>
          </td>
          <td>{{ project.projectRoleNm }}</td>
          <td>
            <button
              type="button"
              class="btn btn-warning btn-sm"
              @click="viewSkill(project.projectSeq, '0')"
            >
              <span>보기</span>
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 과거 사업 내역 -->
    <header class="basic-medium"><span class="star">*</span>과거 사업 내역</header>
    <table class="table table-hover twin">
      <colgroup>
        <col style="width: 5%" />
        <col style="width: 15%" />
        <col style="width: 24%" />
        <col style="width: 15%" />
        <col style="width: 25%" />
        <col style="width: 10%" />
        <col style="width: 6%" />
      </colgroup>
      <thead>
        <tr>
          <th scope="col">NO</th>
          <th scope="col">발주처</th>
          <th scope="col">사업명</th>
          <th scope="col">참여기간</th>
          <th scope="col">담당업무</th>
          <th scope="col">역할</th>
          <th scope="col">기술</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-if="pastProjects.length === 0">
          <td colspan="7" class="figure-caption text-center">참여했던 사업이 없습니다</td>
        </tr>
        <tr v-else v-for="(project, index) in pastProjects" :key="`past-${project.projectSeq}`">
          <td>{{ index + 1 }}</td>
          <td>{{ project.projectClient }}</td>
          <td>{{ project.projectNm }}</td>
          <td>
            {{ formatDate(project.projectStartDate) }}
            ~ {{ formatDate(project.projectEndDate) }}
          </td>
          <td>
            {{ project.assignedTaskLarNm }}
            <span v-if="project.assignedTaskMidNm"> > {{ project.assignedTaskMidNm }} </span>
          </td>
          <td>{{ project.projectRoleNm }}</td>
          <td>
            <button
              type="button"
              class="btn btn-warning btn-sm"
              @click="viewSkill(project.projectSeq, '0')"
            >
              <span>보기</span>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ProjectTable',
  props: {
    project: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    today() {
      // 오늘 날짜를 YYYY-MM-DD 형식으로 반환
      const now = new Date()
      const year = now.getFullYear()
      const month = String(now.getMonth() + 1).padStart(2, '0')
      const day = String(now.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },

    currentProjects() {
      // 현재 진행중인 프로젝트 (종료일이 오늘 이후)
      return this.project.filter((project) => {
        const endDate = this.formatDate(project.projectEndDate)
        return endDate && this.today <= endDate
      })
    },

    pastProjects() {
      // 과거 완료된 프로젝트 (종료일이 오늘 이전)
      return this.project.filter((project) => {
        const endDate = this.formatDate(project.projectEndDate)
        return endDate && this.today > endDate
      })
    },
  },

  methods: {
    formatDate(dateString) {
      if (!dateString) return ''

      // YYYYMMDD 형식을 YYYY-MM-DD로 변환
      if (dateString.length === 8) {
        const year = dateString.substring(0, 4)
        const month = dateString.substring(4, 6)
        const day = dateString.substring(6, 8)
        return `${year}-${month}-${day}`
      }

      // 이미 YYYY-MM-DD 형식인 경우 그대로 반환
      return dateString
    },

    viewSkill(projectSeq, type) {
      // 기술 보기 팝업 또는 모달 열기
      this.$emit('view-skill', { projectSeq, type })

      // 또는 팝업 창으로 열기 (기존 방식 유지)
      // const url = `/skill/view?projectSeq=${projectSeq}&type=${type}`
      // const properties = this.calcPopupSize(800, 600)
      // window.open(url, 'viewSkill', properties)
    },

    calcPopupSize(width, height) {
      const left = screen.width / 2 - width / 2
      const top = screen.height / 2 - height / 2
      return `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/detail.css';
</style>
