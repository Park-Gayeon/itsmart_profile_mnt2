<template>
  <div class="content">
    <form @submit.prevent="goFind">
      <div class="container-md pt-3 mb-md-5">
        <h2>프로젝트 일정관리</h2>

        <div class="input-group pb-2 justify-content-md-end">
          <div class="search-group gap-2">
            <span class="align-content-center">시작일자</span>
            <input type="date" v-model="searchParams.project_start_date" class="dateFmt" />
            <span class="align-content-center">종료일자</span>
            <input type="date" v-model="searchParams.project_end_date" class="dateFmt" />
            <button type="button" class="btn btn-secondary" @click="goFind">
              <span>검색</span>
            </button>
          </div>
        </div>

        <header class="description mb-sm-2 basic-medium">총 {{ totalCount }}건</header>

        <div class="container-info mb-5">
          <div class="table-responsive">
            <table class="table table-hover mb-5">
              <colgroup>
                <col style="width: 5%" />
                <col style="width: auto" />
                <col style="width: 20%" />
                <col style="width: 10%" />
                <col style="width: 10%" />
              </colgroup>
              <thead>
                <tr>
                  <th>NO</th>
                  <th>사업명</th>
                  <th>발주처</th>
                  <th>사업시작일</th>
                  <th>사업종료일</th>
                </tr>
              </thead>
              <tbody class="table-group-divider">
                <tr
                  v-for="(item, index) in list"
                  :key="item.project_nm"
                  style="cursor: pointer"
                  @click="goPopup(item.project_nm)"
                >
                  <td>{{ rowNumber(index) }}</td>
                  <td>{{ item.project_nm }}</td>
                  <td>{{ item.project_client }}</td>
                  <td>{{ formatDate(item.project_start_date) }}</td>
                  <td>{{ formatDate(item.project_end_date) }}</td>
                </tr>
              </tbody>
            </table>

            <hr />

            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: curRange === 1 }">
                  <a class="page-link" href="#" @click.prevent="goPage(startPage - 1)"> &laquo; </a>
                </li>

                <li
                  class="page-item"
                  v-for="pageNum in pageRange"
                  :key="pageNum"
                  :class="{ active: curPage === pageNum }"
                >
                  <a class="page-link" href="#" @click.prevent="goPage(pageNum)">
                    {{ pageNum }}
                  </a>
                </li>

                <li class="page-item" :class="{ disabled: curRange === rangeCnt }">
                  <a class="page-link" href="#" @click.prevent="goPage(endPage + 1)"> &raquo; </a>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import router from '@/router/index.js'
import apiClient from '@/utils/axios'

export default {
  name: 'ScheduleList',
  data() {
    return {
      searchParams: {
        project_start_date: '',
        project_end_date: '',
      },
      list: [], // 프로젝트 리스트
      curPage: 1,
      pageSize: 10,
      totalCount: 0,
      startPage: 1,
      endPage: 1,
      curRange: 1,
      rangeCnt: 1,
    }
  },
  computed: {
    pageRange() {
      const pages = []
      for (let i = this.startPage; i <= this.endPage; i++) {
        pages.push(i)
      }
      return pages
    },
  },
  mounted() {
    this.checkPermission()
    this.fetchList()
  },
  methods: {
    checkPermission() {
      const role = window.userRole?.[0]
      if (role === 'ROLE_USER') {
        alert('접근권한이 없습니다')
        this.$router.push('/home')
      }
    },
    fetchList() {
      // 이 부분은 백엔드 연동 필요. Axios 등으로 서버 요청
      // 예시:
      // axios.get('/schedule/list', { params: { ...this.searchParams, curPage: this.curPage } })
      //      .then(response => {
      //          this.list = response.data.list;
      //          this.totalCount = response.data.cnt;
      //          ...페이징 정보 세팅
      //      })
    },
    formatDate(dateString) {
      if (!dateString) return ''
      return `${dateString.substring(0, 4)}-${dateString.substring(4, 6)}-${dateString.substring(6, 8)}`
    },
    rowNumber(index) {
      return this.totalCount - ((this.curPage - 1) * this.pageSize + index)
    },
    goPopup(projectName) {
      const url = '/schedule/info/' + encodeURIComponent(projectName)
      const features = `width=700,height=200,scrollbars=yes`
      window.open(url, 'usrInfo', features)
    },
    goFind() {
      this.curPage = 1
      this.fetchList()
    },
    goPage(pageNum) {
      this.curPage = pageNum
      this.fetchList()
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/info.css';
</style>
