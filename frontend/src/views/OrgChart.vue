<template>
  <div class="content">
    <div class="container py-md-5 my-md-5">
      <div class="container_org">
        <h2 class="text-center mb-5">조직도</h2>
        <div class="mb-sm-5 ps-sm-3">
          <div
            v-for="org in orgChart"
            :key="org.code_id"
            class="org-node"
            :style="{ paddingLeft: org.level * 50 + 'px' }"
            :data-level="org.level"
            :data-codeid="org.code_id"
            :data-parentid="org.parent_id"
            v-show="isVisible(org)"
          >
            <div class="org-title basic-bold" :title="org.full_path" @click="toggleOrg(org)">
              <img class="toggle-icon" :src="getToggleIcon(org)" alt="toggle-icon" />
              {{ org.hierarchy_value }}
            </div>
            <div
              class="user-list"
              :class="{
                hidden: isUserListHidden(org.code_id),
                visible_usr: !isUserListHidden(org.code_id),
              }"
            >
              <div
                v-for="user in users.filter((u) => u.user_department === org.code_id)"
                :key="user.user_id"
                class="user-item"
              >
                <span class="basic-medium">{{ user.user_nm }}</span>
                <span class="basic-thin">{{ user.user_position_nm }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OrgChart',
  props: {
    orgChart: Array,
    users: Array,
  },
  data() {
    return {
      hiddenNodes: new Set(), // 숨긴 노드 ID
      hiddenUserLists: new Set(), // 숨긴 유저리스트용 부서 ID
    }
  },
  methods: {
    isVisible(org) {
      // 부모가 숨겨져 있으면 나도 숨김
      let current = org
      while (current.parent_id) {
        if (this.hiddenNodes.has(current.parent_id)) return false
        current = this.orgChart.find((o) => o.code_id === current.parent_id)
        if (!current) break
      }
      return true
    },
    isUserListHidden(deptId) {
      return this.hiddenUserLists.has(deptId)
    },
    getToggleIcon(org) {
      const hasChildren = this.orgChart.some((o) => o.parent_id === org.code_id)
      const hasUsers = this.users.some((u) => u.user_department === org.code_id)
      const isHidden = this.hiddenUserLists.has(org.code_id)

      if (hasChildren) {
        return isHidden ? '/images/caret-down-fill.svg' : '/images/caret-down.svg'
      } else if (hasUsers) {
        return isHidden ? '/images/caret-right-fill.svg' : '/images/caret-right.svg'
      } else {
        return '/images/caret-right.svg'
      }
    },
    toggleOrg(org) {
      const deptId = org.code_id

      // 유저 리스트 toggle
      if (this.hiddenUserLists.has(deptId)) {
        this.hiddenUserLists.delete(deptId)
      } else {
        this.hiddenUserLists.add(deptId)
      }

      const toggleChildren = (parentId, hide) => {
        this.orgChart.forEach((node) => {
          if (node.parent_id === parentId) {
            if (hide) {
              this.hiddenNodes.add(node.code_id)
              this.hiddenUserLists.add(node.code_id)
            } else {
              this.hiddenNodes.delete(node.code_id)
              this.hiddenUserLists.delete(node.code_id)
            }
            toggleChildren(node.code_id, hide) // 재귀적으로 처리
          }
        })
      }

      const hideChildren = !this.hiddenNodes.has(deptId)
      toggleChildren(deptId, hideChildren)
    },
  },
}
</script>

<style scoped>
@import '@/assets/css/org.css';
</style>
