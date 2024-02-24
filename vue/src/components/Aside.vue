<template>
    <el-menu :default-openeds="['1', '3']" style="height: 100vh; overflow: hidden"
                active-text-color="#099fff"
                :collapse-transition="false"
                :collapse="isCollapsed"
                router
                >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
            <b v-show="!isCollapsed" style=" margin-left: 5px;">Dashboard</b>
        </div>

        <el-menu-item index="/">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span>Home</span>
            </template>
        </el-menu-item>

        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span>System Manage</span>
            </template>
            <el-menu-item index="user">
                <i class="el-icon-s-custom"></i>
                <span>User Manage</span>
            </el-menu-item>
						<el-menu-item index="role">
                <i class="el-icon-s-custom"></i>
                <span>Role Manage</span>
            </el-menu-item>
						<el-menu-item index="menu">
                <i class="el-icon-s-custom"></i>
                <span>Menu Manage</span>
            </el-menu-item>
						<el-menu-item index="file">
                <i class="el-icon-document"></i>
                <span>File Manage</span>
            </el-menu-item>
        </el-submenu>

    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapsed: Boolean
    },

    methods: {
        handleSelect(index) {

        }
    }
}
</script>