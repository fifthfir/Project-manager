<template>
  <el-card style="width: 500px; padding: 20px;">
    <el-form label-width="80px" size="small">

			<el-form-item label="New Password">
					<el-input v-model="form.password" autocomplete="off"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="save">Confirm</el-button>
			</el-form-item>
		</el-form>
  </el-card>
</template>

<script>
export default {
	name: "Password",
	data() {
		return {
			form: {},
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
		}
	},
	created() {
		this.getUser().then(res => {
			console.log(res)
			this.form = res
		})
	},
	methods: {
		async getUser() {
			return (await this.request.get("/user/username/" + this.user.username)).data
		},

		save() {
			this.logout()
			this.request.post("/user", this.form).then(res => {
			if (res.code ==='200') {
					this.$message.success("Save successed")

					this.$emit("refreshUser")

					this.getUser().then(res => {
						res.token = JSON.parse(localStorage.getItem("user")).token
						localStorage.setItem("user", JSON.stringify(res))
					})



				} else {
					this.$message.error("Save failed")
			}
			})
		},

		logout() {
			this.$store.commit("logout")
			this.$message.success("Log out successfully")
		}
	}
}
</script>
