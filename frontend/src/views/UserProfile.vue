<template>
  <div class="content">
    <div class="container-md pt-3 mb-md-5" v-if="userInfo">
      <div v-if="mode === 'view'">
        <!-- 프로필 헤더 -->
        <h2 class="header">
          프로필
          <div class="description d-md-flex">
            <button v-if="isAdmin" class="btn btn-success" @click="goToList">
              <span>목록가기</span>
            </button>
            <button v-if="isAdmin && !isOwnProfile" class="btn btn-danger" @click="usrDelete">
              <span>삭제</span>
            </button>
            <button class="btn btn-dark" @click="openExcelTemplatePop">
              <span>엑셀템플릿업로드</span>
            </button>
            <select
              class="form-select"
              v-model="selectedTemplate"
              style="width: 150px; float: left"
            >
              <option value="0">template.xlsx</option>
              <option v-for="file in excel" :key="file.fileSeq" :value="file.fileSeq">
                {{ file.fileOriNm }}
              </option>
            </select>
            <button class="btn btn-secondary" @click="downloadExcel"><span>엑셀</span></button>
            <button v-if="isOwnProfile" class="btn btn-warning" @click="goModify">
              <span>수정</span>
            </button>
          </div>
        </h2>

        <!-- 인적사항 -->
        <div class="container-info row-box row mb-5 g-0">
          <header class="description mb-md-3">
            인적사항
            <div v-if="isAdmin" class="right-group">
              <a
                href="#"
                @click.prevent="initPw"
                class="nav-link text-danger"
                style="font-size: 12px"
                >비밀번호초기화</a
              >
            </div>
          </header>

          <div class="row-box px-5 row mb-5 g-0">
            <!-- 사진 -->
            <div class="col-md d-md-flex justify-content-md-center position-relative photo">
              <img
                v-if="profile?.fileSverNm"
                class="img-profile"
                :src="profile.fileSverNm"
                alt="profile"
              />
              <img v-else class="img-profile" :src="basicProfile" />
            </div>

            <!-- 개인정보 -->
            <div class="col-md-10 g-0">
              <div class="row mb-2 g-0">
                <InfoBox label="이름" :modelValue="userInfo.user.userNm" :readonly="true" />
                <InfoBox
                  label="생년월일"
                  :modelValue="formatDate(userInfo.user.userBirth)"
                  :readonly="true"
                />
                <InfoBox label="휴대전화" :modelValue="userInfo.user.userPhone" :readonly="true" />
                <InfoBox
                  label="이메일"
                  :modelValue="`${userInfo.user.userId}@itsmart.co.kr`"
                  :readonly="true"
                />
              </div>
              <div class="row mb-2 g-0">
                <InfoBox
                  label="소속"
                  :modelValue="userInfo.user.userDepartmentNm"
                  :readonly="true"
                />
                <InfoBox
                  label="직위/직급"
                  :modelValue="userInfo.user.userPositionNm"
                  :readonly="true"
                />
                <InfoBox
                  label="입사일자"
                  :modelValue="formatDate(userInfo.user.hireDate)"
                  :readonly="true"
                />
                <InfoBox label="주소" :modelValue="userInfo.user.userAddress" :readonly="true" />
              </div>
            </div>
          </div>

          <!-- 학력 -->
          <hr />
          <div class="mb-5 g-0">
            <header class="description mb-md-3">학력</header>
            <div class="col-md px-5 g-0">
              <template v-if="userInfo.edu?.length">
                <div v-for="(edu, i) in userInfo.edu" :key="i" class="row mb-2 g-0">
                  <SelectBox label="졸업상태" v-model="edu.schoolGubun" :codeId="'SCHL'" disabled />
                  <InfoBox label="학교명" :value="edu.schoolNm" />
                  <InfoBox label="전공명" :value="edu.major" />
                  <InfoBox label="입학일자" :value="formatDate(edu.schoolStartDate)" />
                  <InfoBox label="졸업일자" :value="formatDate(edu.schoolEndDate)" />
                  <InfoBox label="학점" :value="edu.totalGrade" />
                  <SelectBox label="졸업상태" v-model="edu.gradStatus" :codeId="'STUT'" disabled />
                </div>
              </template>
              <p v-else class="text-center">작성된 내용이 없습니다</p>
            </div>
          </div>
          <hr />
          <!-- 자격사항 -->
          <div class="row mb-5 g-0">
            <QualificationTable :qualification="userInfo.qualification" />
          </div>
          <hr />
          <!-- 근무경력 -->
          <div class="row mb-5 g-0">
            <WorkTable :experiences="userInfo.work" :careerSummary="calcWork" />
          </div>
          <!-- 근무경력 끝 -->
          <hr />
          <!-- 수행경력 -->
          <div class="mb-5 g-0">
            <ProjectTable :project="userInfo.project" :careerSummary="calcProject" />
          </div>
        </div>
      </div>
      <div v-if="mode === 'edit'">
        <!-- 프로필 헤더 -->
        <h2 class="header">
          프로필
          <div class="description d-md-flex">
            <button class="btn btn-secondary" @click="goToDetail">뒤로가기</button>
            <button class="btn btn-dark" @click="excelUpload">일괄업로드</button>
            <button class="btn btn-success" @click="goToSave">저장</button>
          </div>
        </h2>

        <!-- 인적사항 -->
        <div class="container-info row-box row mb-5 g-0">
          <header class="description mb-md-3">인적사항</header>
          <div class="row-box px-5 row mb-5 g-0">
            <!-- 사진 -->
            <div class="col-md d-md-flex justify-content-md-center position-relative photo">
              <img
                v-if="profile?.fileSverNm"
                class="img-profile"
                :src="profile.fileSverNm"
                alt="profileUseY"
              />
              <img v-else class="img-profile" :src="basicProfile" alt="profileUseN" />
              <label for="imgFile" class="psitAb">
                <div class="img-profile-btn do-hyeon-regular">+</div>
              </label>
              <input
                type="file"
                id="imgFile"
                name="imgFile"
                class="d-none"
                accept=".jpg, .png, .jpeg"
              />
            </div>

            <!-- 개인정보 -->
            <div class="col-md-10 g-0">
              <div class="row mb-2 g-0">
                <InfoBox label="이름" :modelValue="userInfo.user.userNm" :readonly="true" />
                <InfoBox label="생년월일" v-model="userInfo.user.userBirth" :readonly="false" />
                <InfoBox label="휴대전화" v-model="userInfo.user.userPhone" :readonly="false" />
                <InfoBox
                  label="이메일"
                  :modelValue="`${userInfo.user.userId}@itsmart.co.kr`"
                  :readonly="true"
                />
              </div>
              <div class="row mb-2 g-0">
                <SelectBox label="소속" v-model="userInfo.user.userDepartment" :codeId="'ORG'" />
                <SelectBox
                  label="직위/직급"
                  v-model="userInfo.user.userPosition"
                  :codeId="'PSIT'"
                />
                <InfoBox
                  label="입사일자"
                  :modelValue="formatDate(userInfo.user.hireDate)"
                  :readonly="true"
                />
                <InfoBox label="주소" v-model="userInfo.user.userAddress" :readonly="false" />
              </div>
            </div>
          </div>

          <!-- 학력 -->
          <hr />
          <div class="mb-5 g-0">
            <header class="description mb-md-3">
              학력
              <div class="action-buttons">
                <button class="btn btn-add" @click="addEduItem">
                  <span>+ 추가</span>
                </button>
                <button class="btn btn-remove" @click="clearEduItem">
                  <span>- 제거</span>
                </button>
              </div>
            </header>
            <div class="col-md px-5 g-0">
              <template v-if="userInfo.edu?.length">
                <div v-for="(edu, i) in userInfo.edu" :key="i" class="row mb-2 g-0">
                  <SelectBox label="학교구분" :value="edu.schoolGubun" :codeId="'SCHL'" />
                  <InfoBox label="학교명" v-model="edu.schoolNm" :readonly="false" />
                  <InfoBox label="전공명" v-model="edu.major" :readonly="false" />

                  <InfoBox label="입학일자" v-model="edu.schoolStartDate" :readonly="false" />
                  <InfoBox label="졸업일자" v-model="edu.schoolEndDate" :readonly="false" />
                  <InfoBox label="학점" v-model="edu.totalGrade" :readonly="false" />
                  <SelectBox label="졸업상태" :value="edu.gradStatus" :codeId="'STUT'" />
                </div>
              </template>
            </div>
          </div>
          <hr />
          <!-- 자격사항 -->
          <div class="row mb-5 g-0">
            <QualificationTable :qualification="userInfo.qualification" />
          </div>
          <hr />
          <!-- 근무경력 -->
          <div class="row mb-5 g-0">
            <WorkTable :experiences="userInfo.work" :careerSummary="calcWork" />
          </div>
          <!-- 근무경력 끝 -->
          <hr />
          <!-- 수행경력 -->
          <div class="mb-5 g-0">
            <ProjectTable :project="userInfo.project" :careerSummary="calcProject" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InfoBox from './common/InfoBox.vue'
import SelectBox from './common/SelectBox.vue'
import ProjectTable from './common/ProjectTable.vue'
import WorkTable from './common/WorkTable.vue'
import QualificationTable from './common/QualificationTable.vue'
import basicProfile from '@/assets/images/etc/image.png'

import router from '@/router/index.js'
import apiClient from '@/utils/axios'
import { useAuthStore } from '@/stores/auth'

export default {
  name: 'UserProfile',
  components: { InfoBox, SelectBox, WorkTable, ProjectTable, QualificationTable },
  data() {
    return {
      basicProfile,
      mode: 'view',
      selectedTemplate: '0',
      today: new Date().toISOString().slice(0, 10),
      userInfo: {
        user: {
          userPositionNm: '',
          userDepartmentNm: '',
        },
        edu: [],
        qualification: [],
        project: [],
        work: [],
      },
      profile: {},
      excel: [],
      calcProject: '',
      calcWork: '',
    }
  },
  created() {
    this.authStore = useAuthStore()
  },
  computed: {
    loginUser() {
      return this.authStore.username
    },
    userRole() {
      return this.authStore.authorities
    },
    userId() {
      return this.$route.params.userId
    },
    isAdmin() {
      console.log('isAdmin ? ', this.userRole)
      return this.userRole?.includes('ROLE_ADMIN')
    },
    isOwnProfile() {
      console.log('isOwnProfile ? ', this.loginUser, this.userId)
      return this.loginUser === this.userId
    },
  },
  mounted() {
    this.loadDataSequentially()
  },
  methods: {
    async loadDataSequentially() {
      try {
        await this.getInfo()
        await this.getCodeNm()
        this.getFile()
        this.getExcel()
        this.calc()
      } catch (error) {
        console.error('초기 데이터 로딩 실패:', error)
      }
    },
    formatDate(str) {
      if (!str) return ''
      const s = str.replaceAll('-', '').replaceAll('.', '')
      return `${s.slice(0, 4)}-${s.slice(4, 6)}-${s.slice(6)}`
    },
    async getInfo() {
      try {
        const response = await apiClient.get(`/profile/${this.userId}`)
        this.userInfo.user = response.data.data[0]
        this.userInfo.edu = response.data.data[1]
        this.userInfo.qualification = response.data.data[2]
        this.userInfo.project = response.data.data[3]
        this.userInfo.work = response.data.data[4]
      } catch (error) {
        console.error('프로필 조회 실패:', error)
      }
    },
    async getFile() {
      try {
        const response = await apiClient.get(`/file/profile/${this.userId}`)

        if (response.data.data) {
          this.profile = response.data.data
        }
      } catch (error) {
        console.error('파일 조회 실패:', error)
      }
    },
    async getExcel() {
      try {
        const response = await apiClient.get(`/file/excel/${this.userId}`)
        if (response.data.data) {
          this.excel = response.data.data
        }
      } catch (error) {
        console.error('엑셀 목록 조회 실패:', error)
      }
    },
    async calc() {
      try {
        const [project, work] = await Promise.all([
          apiClient.get(`/project/calc/${this.userId}`),
          apiClient.get(`/work/calc/${this.userId}`),
        ])

        this.project = project.data.data
        this.work = work.data.data
        console.log('project 경력 : ', this.project)
        console.log('work 경력 : ', this.work)
      } catch (error) {
        console.error('경력 조회 실패:', error)
      }
    },
    async getCodeNm() {
      const payload = [
        { codeGroupId: 'PSIT', codeId: this.userInfo.user.userPosition },
        { codeGroupId: 'ORG', codeId: this.userInfo.user.userDepartment },
      ]
      try {
        const response = await apiClient.post('/common/codeNm', payload)
        const codeMap = response.data.data

        this.userInfo.user.userPositionNm = this.getValueByGroupId(codeMap, 'PSIT')
        this.userInfo.user.userDepartmentNm = this.getValueByGroupId(codeMap, 'ORG')
      } catch (error) {
        console.error('API 호출 실패:', error)
      }
    },
    getValueByGroupId(dataMap, groupId) {
      const key = Object.keys(dataMap).find((k) => k.startsWith(`${groupId}:`))
      return key ? dataMap[key] : ''
    },
    addEduItem() {
      this.userInfo.edu.push({
        schoolGubun: '', // 학교구분 코드
        schoolName: '', // 학교명
        major: '', // 전공명
        schoolStartDate: '', // 입학일자
        schoolEndDate: '', // 졸업일자
        grade: '', // 학점
        gradStatus: '', // 졸업상태 코드
      })
    },
    clearEduItem() {
      this.userInfo.edu.splice(-1, 1)
    },
    goToSave() {
      alert('기능 구현중')
    },
    goToList() {
      router.push({ name: 'ProfileList' })
    },
    goModify() {
      this.mode = 'edit'
    },
    goToDetail() {
      this.mode = 'view'
    },
    usrDelete() {
      alert('기능 구현중')
    },
    openExcelTemplatePop() {
      alert('기능 구현중')
    },
    downloadExcel() {
      // 엑셀 다운로드 로직
    },
    excelUpload() {
      alert('기능 구현중')
    },
    initPw() {
      this.$confirmMsg('비밀번호를 초기화하겠습니까?', () => this.actionInitPw())
    },

    async actionInitPw() {
      try {
        const response = await apiClient.post(`/auth/password?flag=1`, {
          userId: this.userId,
          userPw: '',
        })
        if (response.data.success) {
          this.$alertMsg('비밀번호가 초기화되었습니다.', () => this.closeModal())
        }
      } catch (error) {
        console.error('비밀번호 초기화 오류:', error)
      }
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/detail.css';

.btn {
  min-width: 50px;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
  align-items: center;
}

.btn:hover {
  transform: translateY(-1px);
  opacity: 0.9;
}

.action-buttons {
  display: flex;
  gap: 2px;
  justify-content: flex-end;
  align-items: center;
}

.btn-add {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
}

.btn-add:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.btn-remove {
  background: #f8f9fa;
  color: #6c757d;
  border: 1px solid #e9ecef;
}

.btn-remove:hover {
  background: #fff5f5;
  color: #dc3545;
  border-color: #f5c6cb;
}

@media (max-width: 768px) {
  .action-buttons {
    justify-content: center;
  }
}
</style>
