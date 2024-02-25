<template>
	<el-menu :default-openeds="opens" style="height: 100vh; overflow: hidden"
		active-text-color="#099fff"
		:collapse-transition="false"
		:collapse="isCollapsed"
		router
		>
		<div style="height: 60px; line-height: 60px; text-align: center">
				<img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
				<b v-show="!isCollapsed" style=" margin-left: 5px;">Dashboard</b>
		</div>

		<div v-for="item in menus" :key="item.id">
			<div v-if="item.path">
				<el-menu-item :index="item.path">
					<i :class="item.icon"></i>
					<span slot="title">{{ item.name }}</span>
				</el-menu-item>
			</div>

			<div v-else>
				<el-submenu :index="item.id + ''">
					<template slot="title">
						<i :class="item.icon"></i>
						<span slot="title" v-show="!isCollapsed">{{ item.name }}</span>
					</template>

					<div v-for="subItem in item.children" :key="subItem.id">
						<el-menu-item :index="subItem.path">
							<i :class="subItem.icon"></i>
							<span slot="title">{{ subItem.name }}</span>
						</el-menu-item>
					</div>
				</el-submenu>
			</div>
		</div>
	</el-menu>
</template>

<script>
export default {
	name: "Aside",
	props: {
			isCollapsed: Boolean
	},

	data() {
		return {
			menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
			opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : [],
		}
	},

	methods: {
		handleSelect(index) {
		}
	}
}
</script>