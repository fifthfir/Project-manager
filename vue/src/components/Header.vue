<template>
    <div style="line-height: 60px; display: flex">

        <div style="flex: 1;">
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px;" @click="collpase"></span>
            <el-breadcrumb style="display: inline-block; margin-left: 7px;">
                <el-breadcrumb-item>Home</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <el-dropdown style="width: 100px; cursor: pointer">
            <span>Admin</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>Personal Information</el-dropdown-item>
                <el-dropdown-item>exit</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
export default {
    name: "Header",
    props: {
        collapseBtnClass: String,
        collpase: Function
    },

    computed: {
        currentPathName() {
            if (this.$store.state.currentPathName == "Home") {
                return null
            } else {
                return this.$store.state.currentPathName;
            }
        }
    }
}

</script>