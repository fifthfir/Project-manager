<template>
	<div>

		<!-- Head -->
		<div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #ccc;">
			<div style="width: 300px; display: flex; padding-left: 30px;">
				<img src="../../assets/logo2.png" alt="" style="width: 30px; height: 30px; position: relative; top: 15px; right: 6px">
				<span style="sidth: 60px">Welcome to the front</span>
			</div>
			<div style="flex: 1">
				<!-- Navigation -->
				<el-menu default-active="1" class="el-menu-demo" mode="horizontal" router>
					<el-menu-item index="/front/home">Home</el-menu-item>
					<el-menu-item index="/front/video">VideoPlay</el-menu-item>
					<el-menu-item index="/front/article">Articles</el-menu-item>
					<el-submenu index="2">
						<template slot="title">我的工作台</template>
						<el-menu-item index="/front/item1">Item 1</el-menu-item>
						<el-menu-item index="2-2">选项2</el-menu-item>
						<el-menu-item index="2-3">选项3</el-menu-item>
						<el-submenu index="2-4">
							<template slot="title">选项4</template>
							<el-menu-item index="2-4-1">选项1</el-menu-item>
							<el-menu-item index="2-4-2">选项2</el-menu-item>
							<el-menu-item index="2-4-3">选项3</el-menu-item>
						</el-submenu>
					</el-submenu>
					<el-menu-item index="3" disabled>消息中心</el-menu-item>
					<el-menu-item index="4"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
				</el-menu>

			</div>

			<div style="width: 200px">
				<div v-if="!user.username" style="text-align: right; padding-right: 30px;">
					<el-button ><a href="/login">Login</a></el-button>
					<el-button ><a href="/register">Register</a></el-button>
				</div>
				<div v-else>
					<el-dropdown style="width: 150px; cursor: pointer; text-align: right">
						<div style="display: inline-block">
							<img :src="user.avatarUrl" alt=""
								style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
							<span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
						</div>
						<el-dropdown-menu slot="dropdown" style="width: 70px; text-align: center">
								<el-dropdown-item style="font-size: 14px; padding: 5px 0">
									<router-link to="/front/password" style="text-decoration: none">Modify Password</router-link>
								</el-dropdown-item>
								<el-dropdown-item style="font-size: 14px; padding: 5px 0">
									<router-link to="/front/person" style="text-decoration: none">Person</router-link>
								</el-dropdown-item>
								<el-dropdown-item style="font-size: 14px; padding: 5px 0">
									<span style="text-decoration: none" @click="logout">Exit</span>
								</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</div>
			</div>
		</div>

		<div style="width: 1200px; margin: 0 auto">
			<router-view />
		</div>

	</div>
</template>

<script>
export default {
	name: "FrontHome",
	data() {
		return {
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

		}
	},
	created() {

	},

	methods: {
		logout() {
			this.$store.commit("logout")
			this.$message.success("Log out successfully")
		}
	}
}
</script>

<style>
.item{
	display: inline-block;
	width: 240px;
	color: #ffffff;
	text-align: center;
	cursor: pointer
}

.item a {
	color: #ffffff;
	text-underline-offset: 5px;
}

.item:hover {
	background-color: #fcb91c;
}
</style>