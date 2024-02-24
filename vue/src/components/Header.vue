<template>
    <div style="line-height: 60px; display: flex">

        <div style="flex: 1;">
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px;" @click="collpase"></span>
            <el-breadcrumb style="display: inline-block; margin-left: 7px;">
                <el-breadcrumb-item>Home</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <el-dropdown style="width: auto; cursor: pointer; text-align: right">
					<div style="display: inline-block">
						<img :src="user.avatarUrl" alt=""
						  style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
            <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
					</div>
            <el-dropdown-menu slot="dropdown" style="width: 70px; text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
									<router-link to="/person" style="text-decoration: none">Person</router-link>
								</el-dropdown-item>
								<el-dropdown-item style="font-size: 14px; padding: 5px 0">
									<span style="text-decoration: none" @click="logout">Exit</span>
								</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
export default {
	name: "Header",
	props: {
			collapseBtnClass: String,
			collpase: Function,
			user: Object
	},

	computed: {
		currentPathName() {
			if (this.$store.state.currentPathName == "Home") {
				return null
			} else {
				return this.$store.state.currentPathName;
			}
		}
	},
	data() {
		return {
			// user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
		}
	},
	methods: {
		logout() {
			this.$router.push("/login")
			localStorage.removeItem("user")
			this.$message.success("Log out successfully")
		}
	}
}

</script>