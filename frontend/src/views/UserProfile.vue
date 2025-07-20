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

          <!-- 프로필 -->
          <div class="row-box px-5 row mb-5 g-0">
            <div class="col-md d-md-flex justify-content-md-center position-relative photo">
              <img
                class="img-profile"
                :src="previewUrl"
                :alt="profile?.fileSverNm ? 'profileUseY' : 'profileUseN'"
              />
            </div>
            <ProfileTable v-model:user="userInfo.user" />
          </div>
          <!-- 프로필 끝 -->
          <hr />
          <!-- 학력 -->
          <div class="mb-5 g-0">
            <header class="description mb-md-3">학력</header>
            <EduTable v-model:edu="userInfo.edu" />
          </div>
          <!-- 학력 끝 -->
          <hr />
          <!-- 자격사항 -->
          <div class="row mb-5 g-0">
            <header class="description mb-md-3">자격사항</header>
            <QualificationTable v-model:qualification="userInfo.qualification" />
          </div>
          <!-- 자격사항 끝 -->
          <hr />
          <!-- 근무경력 -->
          <div class="row mb-5 g-0">
            <header class="description mb-md-3">
              <div class="left-group">
                근무경력 <span>{{ formattedCareerSummary(this.calcWork) }}</span>
              </div>
            </header>
            <WorkTable v-model:experiences="userInfo.work" />
          </div>
          <!-- 근무경력 끝 -->
          <hr />
          <!-- 수행경력 -->
          <div class="mb-5 g-0">
            <header class="description mb-md-3">
              <div class="left-group">
                수행경력
                <span>{{ formattedCareerSummary(this.calcProject) }}</span>
              </div>
            </header>
            <ProjectTable v-model:project="userInfo.project" />
          </div>
          <!-- 수행경력 끝 -->
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
                class="img-profile"
                :src="previewUrl"
                :alt="profile?.fileSverNm ? 'profileUseY' : 'profileUseN'"
              />
              <label for="imgFile" class="psitAb">
                <div class="img-profile-btn do-hyeon-regular">+</div>
              </label>
              <input
                type="file"
                id="imgFile"
                name="imgFile"
                class="d-none"
                accept=".jpg, .png, .jpeg"
                @change="onFileChange"
              />
            </div>
            <ProfileTable v-model:user="userInfo.user" mode="edit" />
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
            <EduTable v-model:edu="userInfo.edu" mode="edit" />
          </div>
          <hr />
          <!-- 자격사항 -->
          <div class="row mb-5 g-0">
            <header class="description mb-md-3">
              자격사항
              <div class="action-buttons">
                <button class="btn btn-add" @click="addQaulItem">
                  <span>+ 추가</span>
                </button>
                <button class="btn btn-remove" @click="clearQaulItem">
                  <span>- 제거</span>
                </button>
              </div>
            </header>
            <QualificationTable v-model:qualification="userInfo.qualification" mode="edit" />
          </div>
          <hr />
          <!-- 근무경력 -->
          <div class="row mb-5 g-0">
            <header class="description mb-md-3">
              <div class="left-group">
                근무경력 <span>{{ formattedCareerSummary(this.calcWork) }}</span>
              </div>
              <div class="action-buttons">
                <button class="btn btn-add" @click="addWorkItem">
                  <span>+ 추가</span>
                </button>
                <button class="btn btn-remove" @click="clearWorkItem">
                  <span>- 제거</span>
                </button>
              </div>
            </header>
            <WorkTable v-model:experiences="userInfo.work" mode="edit" />
          </div>
          <!-- 근무경력 끝 -->
          <hr />
          <!-- 수행경력 -->
          <div class="mb-5 g-0">
            <header class="description mb-md-3">
              <div class="left-group">
                수행경력 <span>{{ formattedCareerSummary(this.calcProject) }}</span>
              </div>
              <div class="action-buttons">
                <button class="btn btn-add" @click="addProjectItem">
                  <span>+ 추가</span>
                </button>
                <button class="btn btn-remove" @click="clearProjectItem">
                  <span>- 제거</span>
                </button>
              </div>
            </header>
            <ProjectTable v-model:project="userInfo.project" mode="edit" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileTable from './common/ProfileTable.vue'
import EduTable from './common/EduTable.vue'
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
  methods: {
    async loadDataSequentially() {
      try {
        await this.getInfo()
        await this.getCodeNm()
        await this.getFile()
        await this.getExcel()
        await this.calc()
      } catch (error) {
        console.error('초기 데이터 로딩 실패:', error)
      }
    },
    async getInfo() {
      try {
        const response = await apiClient.get(`/profile/${this.userId}`)
        this.userInfo.user = response.data.data.profileInfo
        this.userInfo.edu = response.data.data.educationInfo
        this.userInfo.qualification = response.data.data.qualificationInfo
        this.userInfo.project = response.data.data.projectInfo
        this.userInfo.work = response.data.data.experienceInfo
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

    formatDate(str) {
      if (!str) return ''
      const s = str.replaceAll('-', '').replaceAll('.', '')
      return `${s.slice(0, 4)}-${s.slice(4, 6)}-${s.slice(6)}`
    },

    onFileChange(e) {
      const fileInfo = e.target.files[0]
      const maxSize = 1024 * 1024 * 2 // 2MB
      const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg']

      // 선택 취소
      if (!fileInfo) {
        this.previewUrl = this.basicProfile
        this.selectedFile = null
        return
      }

      // 파일명 검사
      if (fileInfo.name.length > 20 || fileInfo.name.includes(' ')) {
        this.$alertMsg('파일명은 공백을 제외한 20자 이내로 작성해주세요.')
        return
      }

      // 파일 크기 검사
      if (fileInfo.size > maxSize) {
        this.$alertMsg('이미지 사진의 용량이 2MB를 초과합니다.')
        return
      }

      // 파일 타입 검사
      if (!allowedTypes.includes(fileInfo.type)) {
        this.$alertMsg('이미지 파일만 업로드 가능합니다.')
        return
      }

      // 미리보기
      const reader = new FileReader()
      reader.onload = (e) => {
        this.previewUrl = e.target.result
      }
      reader.readAsDataURL(fileInfo)

      this.selectedFile = fileInfo
    },

    formattedCareerSummary(month) {
      const totalMonth = Number(month)
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

    /* 항목 추가 */
    addEduItem() {
      this.userInfo.edu.push({
        schoolGubun: '', // 학교구분 코드
        schoolNm: '', // 학교명
        major: '', // 전공명
        schoolStartDate: '', // 입학일자
        schoolEndDate: '', // 졸업일자
        totalGrade: '', // 학점
        gradStatus: '', // 졸업상태 코드
      })
    },

    addQaulItem() {
      this.userInfo.qualification.push({
        qualificationNm: '', // 자격증명
        issuer: '', // 발행기관
        acquisitionDate: '', // 취득일자
        expiryDate: '', // 만료일자
      })
    },

    addWorkItem() {
      this.userInfo.work.push({
        workPlace: '', // 회사명
        workPosition: '', // 직급
        workAssignedTask: '', // 담당업무
        workStartDate: '', // 입사일자
        workEndDate: '', // 퇴사일자
      })
    },

    addProjectItem() {},

    /* 항목 제거 */
    clearEduItem() {
      this.userInfo.edu.splice(-1, 1)
    },

    clearQaulItem() {
      this.userInfo.qualification.splice(-1, 1)
    },

    clearWorkItem() {
      this.userInfo.work.splice(-1, 1)
    },

    goToSave() {
      this.$confirmMsg('저장하시겠습니까?', () => this.goToSaveReq())
    },

    async goToSaveReq() {
      const payload = {
        profileInfo: this.userInfo.user,
        educationInfo: this.userInfo.edu,
        qualificationInfo: this.userInfo.qualification,
        experienceInfo: this.userInfo.work,
        projectInfo: this.userInfo.project,
      }
      try {
        const formData = new FormData()
        formData.append(
          'payload',
          new Blob([JSON.stringify(payload)], { type: 'application/json' }),
        )

        // 이미지가 선택된 경우만 추가
        if (this.selectedFile) {
          formData.append('imgFile', this.selectedFile)
        }
        const response = await apiClient.post(`/profile/${this.userId}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        if (response.data.success) {
          this.$alertMsg('저장되었습니다.', () => this.loadDataSequentially())
        }
      } catch (error) {
        console.error('저장 오류:', error)
        this.$alertMsg('오류가 발생했습니다. 시스템관리자에게 문의바랍니다.')
      }
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
      alert('기능 구현중')
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
  name: 'UserProfile',
  components: {
    ProfileTable,
    InfoBox,
    SelectBox,
    EduTable,
    WorkTable,
    ProjectTable,
    QualificationTable,
  },
  data() {
    return {
      basicProfile,
      previewUrl: '', // 미리보기용 이미지 URL
      selectedFile: null, // 선택된 파일 객체
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

  watch: {
    'profile.fileSverNm'(newVal) {
      this.previewUrl = newVal ? `/uploads/images/${newVal}` : this.basicProfile
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
