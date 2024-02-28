<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 270px; padding: 20px; border-radius: 5%;">
      <div style="margin: 30px 0; text-align: center; font-size: 24px"><b>Login</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">

        <el-form-item prop="username">
          <el-input style="margin: 5px 0" size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input prop="password" style="margin: 5px 0" size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item style="margin: 20px 0; text-align: center">
          <el-button type="primary" size="small" autocomplete="off" @click="login">Log in</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">Register</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";

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
          ]
        },
      }
    },

    methods: {
      login() {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            this.request.post("/user/login", this.user).then(res => {
              if (res.code === '200') {
								localStorage.setItem("user", JSON.stringify(res.data))
								localStorage.setItem("menus", JSON.stringify(res.data.menus))

								setRoutes()
								this.$message.success("Login successfully")

								if (res.data.role === "ROLE_STUDENT") {
									this.$router.push('/front/home')
								} else {
									this.$router.push('/')
								}

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