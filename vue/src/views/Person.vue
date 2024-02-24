<template>
  <el-card style="width: 500px; padding: 20px;">
    <el-form label-width="80px" size="small">

			<el-upload
				class="avatar-uploader"
				action="http://localhost:8148/file/upload"
				:show-file-list="false"
				:on-success="handleAvatarSuccess"
				>
				<img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
				<i v-else class="el-icon-plus avatar-uploader-icon"></i>
			</el-upload>

			<el-form-item label="username">
					<el-input v-model="form.username" autocomplete="off" :disabled="true"></el-input>
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

		handleAvatarSuccess(res) {
			this.form.avatarUrl = res
		}
	}
}
</script>

<style>
	.headerBg {
			background: #f5f5f5!important;
	}

	.avatar-uploader {
		text-align: center;
		padding-bottom: 10px;
	}

	.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 158px;
    height: 158px;
    line-height: 158px;
    text-align: center;
  }
  .avatar {
    width: 158px;
    height: 158px;
    display: block;
  }

</style>
