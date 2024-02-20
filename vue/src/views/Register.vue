<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 330px; padding: 20px; border-radius: 5%;">
      <div style="margin: 30px 0; text-align: center; font-size: 24px"><b>Register</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">

        <el-form-item prop="username">
          <el-input placeholder="Please enter your usrname" style="margin: 5px 0" size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="Please enter your password" style="margin: 5px 0" prop="password" size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

				<el-form-item prop="confirmPassword">
          <el-input placeholder="Please confirm your password" style="margin: 5px 0" prop="confirmPassword" size="medium" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>

        <el-form-item style="margin: 20px 0; text-align: center">
          <el-button type="primary" size="small" autocomplete="off" @click="login">Register</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">Back</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
export default {
    name: "Login",
    data() {
      return {
        user: {},

        rules: {
          username: [
            { required: true, message: 'Please enter username', trigger: 'blur' },
            { min: 3, max: 10, message: 'Length between 3 to 10 characters', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'Please enter password', trigger: 'blur' },
            { min: 3, max: 10, message: 'Length between 3 to 10 characters', trigger: 'blur' }
          ],
					confirmPassword: [
            { required: true, message: 'Please confirm password', trigger: 'blur' },
            { min: 3, max: 10, message: 'Length between 3 to 10 characters', trigger: 'blur' }
          ]
        },
      }
    },

    methods: {
      login() {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
						if (this.user.password !== this.user.confirmPassword) {
							this.$message.error("Different passwords")
							return false
						}
            this.request.post("/user/register", this.user).then(res => {
              if (res.code === '200') {
								this.$message.success("Register successfully")
              } else {
                this.$message.error(res.msg)
              }
            })
          }
        });
      }
    }
}

</script>

<style>
  .wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
    overflow: hidden;
  }
</style>