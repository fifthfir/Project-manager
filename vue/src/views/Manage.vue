<template>
	<el-container style="min-height: 100vh">

		<el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(195, 212, 235);">
			<Aside :isCollapsed="isCollapsed"/>
		</el-aside>

		<el-container>
			<el-header style="border-bottom: 1px solid #ccc;">
				<Header :collapseBtnClass="collapseBtnClass" :collpase="collpase" :user="user" />
			</el-header>

			<el-main>
				<router-view @refreshUser="getUser" />
			</el-main>

		</el-container>
	</el-container>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: 'Home',
  data() {
    return {
			collapseBtnClass: 'el-icon-s-fold',
			isCollapsed: false,
			sideWidth: 200,
			user: {},
    }
  },

  components: {
    Aside,
    Header
  },

	created() {
		this.getUser()
	},

  methods: {
    collpase() {
        this.isCollapsed = !this.isCollapsed
        if (this.isCollapsed) {
            this.sideWidth = 64
            this.collapseBtnClass = 'el-icon-s-unfold'
        } else {
            this.sideWidth = 200
            this.collapseBtnClass = 'el-icon-s-fold'
        }
    },

		getUser() {
			let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
			this.request.get("/user/username/" + username).then(res => {
				this.user = res.data
			})
		},
  }
}
</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>
