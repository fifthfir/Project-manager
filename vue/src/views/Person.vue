<template>
  <el-card style="width: 500px; padding: 20px;">
    <el-form label-width="80px" size="small">
			<el-form-item label="username">
					<el-input v-model="form.username" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="nickname">
					<el-input v-model="form.nickname" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="email">
					<el-input v-model="form.email" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="phone">
					<el-input v-model="form.phone" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="address">
					<el-input v-model="form.address" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="save">Confirm</el-button>
			</el-form-item>
		</el-form>
  </el-card>
</template>

<script>
export default {
	name: "Person",
	data() {
		return {
			form: {},
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
		}
	},
	created() {
		this.request.get("/user/username/" + this.user.username).then(res => {
			if (res.code === '200') {
				this.user = res.data
			}
		})
	},
	methods: {
		save() {
			this.request.post("/user", this.form).then(res => {
					if (res.data) {
							this.$message.success("Save successed")
					} else {
							this.$message.error("Save failed")
					}
				})
			}
		}
}
</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>
