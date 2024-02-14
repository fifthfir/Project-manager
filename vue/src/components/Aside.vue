<template>
    <el-menu :default-openeds="['1', '3']" style="height: 100vh; overflow: hidden"
                active-text-color="#099fff"
                :collapse-transition="false"
                :collapse="isCollapsed"
                >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
            <b v-show="!isCollapsed" style=" margin-left: 5px;">Dashboard</b>
        </div>
        <el-submenu index="1">
            <template slot="title">
                <i class="el-icon-message"></i>
                <span slot="title">Navi 1</span>
            </template>
            <el-menu-item-group title="Group 2">
            <el-menu-item index="1-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
            <template slot="title">Option 4</template>
            <el-menu-item index="1-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-message"></i>
                <span slot="title">Navi 2</span>
            </template>
            <el-submenu index="2-4">
            <template slot="title">Option 4</template>
            <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-message"></i>
                <span slot="title">Navi 3</span>
            </template>
            <el-submenu index="3-4">
                <template slot="title">Option 4</template>
                <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapsed: Boolean
    }
}
</script>