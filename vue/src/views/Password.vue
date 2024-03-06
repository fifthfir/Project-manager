<template>
  <el-card style="width: 500px; padding: 20px;">
    <el-form label-width="120px" size="small" :model="form" :rules="rules" ref="pass">

			<el-form-item label="Old Password" prop="password">
					<el-input v-model="form.password" autocomplete="off" show-password></el-input>
			</el-form-item>

			<el-form-item label="New Password" prop="newPassword">
					<el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
			</el-form-item>

			<el-form-item label="Confirm Password" prop="confirmPassword">
					<el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
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
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
			rules: {
				password: [
					{ required: true, message: "Please enter your original password", trigger: 'blur' },
					{ min: 3, message: "No shorter than 3 digits", trigger: 'blur' }
				],
				newPassword: [
					{ required: true, message: "Please set your new password", trigger: 'blur' },
					{ min: 3, message: "No shorter than 3 digits", trigger: 'blur' }
				],
				confirmPassword: [
					{ required: true, message: "Please confirm your new password", trigger: 'blur' },
					{ min: 3, message: "No shorter than 3 digits", trigger: 'blur' }
				],
			}
		}
	},
	created() {
		this.form.username = this.user.username
	},
	methods: {
		save() {
			this.$refs.pass.validate((valid) => {
				if (valid) {
					if (this.form.newPassword !== this.form.confirmPassword) {
						this.$message.error("Different new passwords")
						return false
					}

					this.request.post("/user/password", this.form).then(res => {
						if (res.code ==='200') {
							this.$message.success("Modify successed")
							this.logout()
						} else {
							this.$message.error(res.msg)
						}
					})
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
