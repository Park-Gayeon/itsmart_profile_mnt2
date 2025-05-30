<template>
  <div class="content">
    <div class="container-md pt-3 mb-md-5">
      <h2>직원 프로필관리</h2>
      <div class="input-group pb-2 justify-content-md-end">
        <select v-model="searchType" class="search-type ps-3">
          <option value="userNm">이름</option>
          <option value="userId">ID</option>
          <option value="userDepartment">소속</option>
        </select>
        <div class="search-group">
          <input
            v-model="searchText"
            @keydown.enter.prevent="goFind"
            type="text"
            class="search-input px-sm-3"
            maxlength="30"
          />
          <button @click="goFind" class="btn btn-secondary"><span>검색</span></button>
        </div>
      </div>

      <header class="description mb-sm-2 basic-medium">
        총 {{ totalCount }}건
        <div class="primary-btn">
          <button class="btn btn-secondary" @click="downloadExcel"><span>엑셀</span></button>
          <button class="btn btn-warning" @click="openRegister"><span>신규등록</span></button>
        </div>
      </header>

      <div class="container-info mb-5">
        <div class="table-responsive">
          <table class="table table-hover mb-5">
            <thead>
              <tr>
                <th>NO</th>
                <th>이름</th>
                <th>소속</th>
                <th>직급</th>
                <th>발주처</th>
                <th>사업명</th>
                <th>사업기간</th>
                <th>수행경력</th>
                <th>자격증</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(info, index) in infoList"
                :key="info.userId"
                @click="goDetail(info.userId)"
                style="cursor: pointer"
              >
                <td>{{ totalCount - ((pagination.curPage - 1) * pagination.pageSize + index) }}</td>
                <td>{{ info.userNm }}</td>
                <td>{{ info.userDepartmentNm }}</td>
                <td>{{ info.userPositionNm }}</td>
                <td>{{ info.projectClient }}</td>
                <td>{{ info.projectNm }}</td>
                <td>{{ formatProjectPeriod(info.projectStartDate, info.projectEndDate) }}</td>
                <td>{{ convertToString(info.projectTotalMonth) }}</td>
                <td class="text-center">{{ info.qualificationYn === 1 ? 'Y' : '' }}</td>
              </tr>
            </tbody>
          </table>

          <!-- Pagination -->
          <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
              <li class="page-item" :class="{ disabled: pagination.curRange === 1 }">
                <a class="page-link" href="#" @click.prevent="fnPaging(pagination.startPage - 1)"
                  >&laquo;</a
                >
              </li>

              <li
                class="page-item"
                v-for="pageNum in pageNumbers"
                :key="pageNum"
                :class="{ active: pageNum === pagination.curPage }"
              >
                <a class="page-link" href="#" @click.prevent="fnPaging(pageNum)">{{ pageNum }}</a>
              </li>

              <li
                class="page-item"
                :class="{ disabled: pagination.curRange === pagination.rangeCnt }"
              >
                <a class="page-link" href="#" @click.prevent="fnPaging(pagination.endPage + 1)"
                  >&raquo;</a
                >
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router/index.js'
import apiClient from '@/utils/axios'

export default {
  name: 'ProfileInfoList',
  data() {
    return {
      searchType: 'userNm',
      searchText: '',
      infoList: [],
      totalCount: 0,
      pagination: {
        curPage: 1,
        pageSize: 10,
        curRange: 1,
        startPage: 1,
        endPage: 1,
        rangeCnt: 1,
      },
      userRole: [],
    }
  },
  mounted() {
    // 초기 데이터 로드
    this.userRole = this.getUserRole()
    if (this.userRole[0] === 'ROLE_USER') {
      alert('접근권한이 없습니다')
      router.push({ name: 'about' })
    }

    this.getList()
  },
  methods: {
    getUserRole() {
      // 실제 로그인 세션 정보에서 추출
      return ['ROLE_ADMIN'] // 예시
    },
    async getList() {
      try {
        const response = await apiClient.get('/profile/list')
        this.infoList = response.data.data
      } catch (error) {
        console.error('목록 조회 실패:', error)
        router.push({ name: 'Login' })
      }
    },
    goDetail(userId) {
      this.$router.push({ name: 'Profile', params: { userId } })
    },
    goFind() {
      this.pagination.curPage = 1
      this.getList()
    },
    fnPaging(pageNum) {
      this.pagination.curPage = pageNum
      this.getList()
    },
    openRegister() {
      const url = '/profile/info/register'
      const features = 'width=1000,height=450,resizable=yes,scrollbars=yes'
      window.open(url, 'registerUser', features)
    },
    formatProjectPeriod(start, end) {
      if (!start || !end) return ''
      const format = (date) => {
        return `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6)}`
      }
      return `${format(start)} ~ ${format(end)}`
    },
    convertToString(months) {
      if (!months || isNaN(months)) return ''
      const year = Math.floor(months / 12)
      const month = months % 12
      return year > 0 ? `${year}년 ${month}개월` : `${month}개월`
    },
    downloadExcel() {
      fetch('/excel/info/download', { method: 'POST' })
        .then((res) => {
          if (!res.ok) throw new Error('엑셀 다운로드 실패')
          return res.blob()
        })
        .then((blob) => {
          const url = URL.createObjectURL(blob)
          const a = document.createElement('a')
          a.href = url
          a.download = 'profile_list.xlsx'
          document.body.appendChild(a)
          a.click()
          URL.revokeObjectURL(url)
        })
        .catch((err) => {
          alert(err.message)
        })
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/info.css';
</style>
