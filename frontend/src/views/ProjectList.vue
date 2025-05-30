<template>
  <div class="content">
    <form @submit.prevent="goFind" id="frm">
      <div class="container-md pt-3 mb-md-5">
        <h2>프로젝트 관리</h2>
        <div class="input-group pb-2 justify-content-md-end">
          <select v-model="searchType" name="searchType" class="search-type ps-3">
            <option value="projectNm">사업명</option>
            <option value="client">발주처</option>
            <option value="masterId">MASTER_ID</option>
          </select>
          <div class="search-group">
            <input
              type="text"
              v-model="searchText"
              name="searchText"
              class="search-input px-sm-3"
              maxlength="30"
              @keydown.enter.prevent="goFind"
            />
            <button type="button" class="btn btn-secondary" @click="goFind">
              <span>검색</span>
            </button>
          </div>
        </div>

        <div>
          <header class="description mb-sm-2 basic-medium">
            총 {{ totalCount }}건
            <div class="primary-btn">
              <button type="button" class="btn btn-warning" @click="register">
                <span>등록</span>
              </button>
            </div>
          </header>

          <div class="container-info mb-5">
            <div class="table-responsive">
              <table class="table table-hover mb-5">
                <thead>
                  <tr>
                    <th>NO</th>
                    <th>MASTER_ID</th>
                    <th>사업명</th>
                    <th>발주처</th>
                    <th>사업시작일</th>
                    <th>사업종료일</th>
                    <th>사용여부</th>
                  </tr>
                </thead>
                <tbody class="table-group-divider">
                  <tr v-for="(project, index) in projectList" :key="project.master_id">
                    <td>{{ totalCount - ((curPage - 1) * pageSize + index) }}</td>
                    <td>{{ project.master_id }}</td>
                    <td>{{ project.project_nm }}</td>
                    <td>{{ project.project_client }}</td>
                    <td>{{ formatDate(project.project_start_date) }}</td>
                    <td>{{ formatDate(project.project_end_date) }}</td>
                    <td>
                      <div class="form-check">
                        <input
                          class="form-check-input"
                          type="radio"
                          :name="'use_yn_' + index"
                          :id="'use_yn_y' + index"
                          value="Y"
                          :checked="project.use_yn === 'Y'"
                          @change="changeUseYn(index, 'Y', project.master_id)"
                        />
                        <label class="form-check-label" :for="'use_yn_y' + index">Y</label>
                      </div>
                      <div class="form-check">
                        <input
                          class="form-check-input"
                          type="radio"
                          :name="'use_yn_' + index"
                          :id="'use_yn_n' + index"
                          value="N"
                          :checked="project.use_yn === 'N'"
                          @change="changeUseYn(index, 'N', project.master_id)"
                        />
                        <label class="form-check-label" :for="'use_yn_n' + index">N</label>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>

              <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                  <li class="page-item" :class="{ disabled: curRange === 1 }">
                    <a class="page-link" href="#" @click.prevent="fnPaging(startPage - 1)">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>

                  <li
                    v-for="pageNum in pageRange"
                    :key="pageNum"
                    class="page-item"
                    :class="{ active: pageNum === curPage }"
                  >
                    <a class="page-link" href="#" @click.prevent="fnPaging(pageNum)">{{
                      pageNum
                    }}</a>
                  </li>

                  <li class="page-item" :class="{ disabled: curRange === rangeCnt }">
                    <a class="page-link" href="#" @click.prevent="fnPaging(endPage + 1)">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import router from '@/router/index.js'

export default {
  name: 'ProjectList',
  data() {
    return {
      searchType: 'projectNm',
      searchText: '',
      totalCount: 0,
      projectList: [],
      curPage: 1,
      pageSize: 10,
      startPage: 1,
      endPage: 1,
      curRange: 1,
      rangeCnt: 1,
    }
  },
  computed: {
    pageRange() {
      let pages = []
      for (let i = this.startPage; i <= this.endPage; i++) {
        pages.push(i)
      }
      return pages
    },
  },
  methods: {
    formatDate(dateStr) {
      if (!dateStr) return ''
      return `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6)}`
    },
    goFind() {
      this.curPage = 1
      this.fetchProjectList()
    },
    fnPaging(pageNum) {
      this.curPage = pageNum
      this.fetchProjectList()
    },
    fetchProjectList() {
      // Axios 요청 예시
      const params = {
        searchType: this.searchType,
        searchText: this.searchText,
        curPage: this.curPage,
      }
      this.$axios.get('/project/common/list', { params }).then((res) => {
        this.projectList = res.data.list
        this.totalCount = res.data.cnt
        this.startPage = res.data.page.startPage
        this.endPage = res.data.page.endPage
        this.curRange = res.data.page.curRange
        this.rangeCnt = res.data.page.rangeCnt
      })
    },
    changeUseYn(index, newVal, masterId) {
      const originVal = this.projectList[index].use_yn
      if (originVal === newVal) {
        alert('변경할 데이터가 없습니다.')
        return
      }

      if (confirm('사용여부 변경시 모든 직원의 수행이력에 즉시 반영됩니다.\n변경하시겠습니까?')) {
        this.$axios
          .post('/project/common/update', {
            master_id: masterId,
            use_yn: newVal,
          })
          .then(() => {
            alert('변경되었습니다.')
            this.fetchProjectList()
          })
          .catch((err) => {
            alert(err.response.data || '에러가 발생했습니다.')
          })
      } else {
        this.$set(this.projectList[index], 'use_yn', originVal)
      }
    },
    register() {
      const w = 670
      const h = 200
      const left = (window.screen.width - w) / 2
      const top = (window.screen.height - h) / 2
      const props = `width=${w},height=${h},left=${left},top=${top},resizable=no`
      window.open('/project/common/register', 'registerProject', props)
    },
  },
  mounted() {
    const userRole = this.$root.userRole || []
    if (userRole[0] === 'ROLE_USER') {
      alert('접근권한이 없습니다')
      router.push({ name: 'Login' })
    } else {
      this.fetchProjectList()
    }
  },
}
</script>

<style scoped>
@import '@/assets/css/info.css';
</style>
