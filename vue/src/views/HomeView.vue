<template>
    <el-container style="min-height: 100vh">
        <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(195, 212, 235);">
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
        </el-aside>

        <el-container>

            <el-header style="font-szie: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
                <div style="flex: 1; font-size: 20px">
                    <span :class="collapseBtnClass" style="cursor: pointer" @click="collpase"></span>
                </div>
                <el-dropdown style="width: 100px; cursor: pointer">
                    <span>Ria</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>Personal Information</el-dropdown-item>
                        <el-dropdown-item>exit</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>

            <el-main>

                <div style="margin-bottom: 30px">
                    <el-breadcrumb seperator="/">
                        <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
                        <el-breadcrumb-item>Manage</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>

                <div style="margin: 10px 0">
                    <el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="username"></el-input>
                    <el-input style="width: 200px" placeholder="Email" suffix-icon="el-icon-message" v-model="email" class="ml-5"></el-input>
                    <el-input style="width: 200px" placeholder="Address" suffix-icon="el-icon-map-location" v-model="address" class="ml-5"></el-input>
                    <el-button class="ml-5" type="primary" @click="load()">Search</el-button>
                    <el-button type="warning" @click="reset()">Reset</el-button>
                </div>

                <div style="margin: 10px 0">
                    <el-button type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
                    <el-popconfirm
                                class="ml-5"
                                confirm-button-text='Confirm'
                                cancel-button-text='Do not delete'
                                icon="el-icon-info"
                                icon-color="red"
                                title="Confirm to delete these data?"
                                @confirm="delBatch"
                            >
                        <el-button type="danger" slot="reference">Muti-Delete <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                    <el-button type="primary" class="ml-5">Import <i class="el-icon-bottom"></i></el-button>
                    <el-button type="primary">Export <i class="el-icon-top"></i></el-button>
                </div>

                <el-table :data="tableData" border strip header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="ID" width="80"></el-table-column>
                    <el-table-column prop="username" label="username" width="140"></el-table-column>
                    <el-table-column prop="nickname" label="nickname" width="120"></el-table-column>
                    <el-table-column prop="email" label="email"></el-table-column>
                    <el-table-column prop="phone" label="phone"></el-table-column>
                    <el-table-column prop="address" label="address"></el-table-column>
                    <el-table-column label="Action">
                        <template slot-scope="scope">
                            <el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>
                            <el-popconfirm
                                class="ml-5"
                                confirm-button-text='Confirm'
                                cancel-button-text='Do not delete'
                                icon="el-icon-info"
                                icon-color="red"
                                title="Confirm to delete?"
                                @confirm="handleDelete(scope.row.id)"
                            >
                                <el-button type="danger" slot="reference">Delete <i class="el-icon-remove-outline"></i></el-button>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="padding: 10px 0">
                    <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[2, 5, 10, 20]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                    </el-pagination>
                </div>

                <el-dialog title="User Information" :visible.sync="dialogFormVisible" width="30%">
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
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="save">Confirm</el-button>
                    </div>
                </el-dialog>

            </el-main>

        </el-container>
    </el-container>
</template>

<script>

import request from '@/utils/request'

export default {
  name: 'Home',
  data() {
    return {
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 5,
        username: "",
        email: "",
        address: "",
        collapseBtnClass: 'el-icon-s-fold',
        isCollapsed: false,
        sideWidth: 200,
        dialogFormVisible: false,
        form: {},
        multipleSelection: []
    }
  },

  created() {
    this.load()
  },

  methods: {
    collpase() {
        this.isCollapsed = !this.isCollapsed
        if (this.isCollapsed) {
            this.sideWidth = 64
            this.collapseBtnClass = 'el-icon-s-unfold'
        } else {
            this.sideWidth = 200
            this.collapseBtnClass = 'el-icon-s-fold'
        }
    },

    handleSizeChange(pageSize) {
        console.log(pageSize)
        this.pageSize = pageSize
        this.load()
    },

    handleCurrentChange(pageNum) {
        console.log(pageNum)
        this.pageNum = pageNum
        this.load()
    },

    handleAdd() {
        this.dialogFormVisible = true
        this.form = {}
    },

    handleEdit(row){
        this.form = Object.assign({}, row)  // In order not to modify before clicking cancel
        this.dialogFormVisible = true
    },

    handleDelete(id){
        request.delete("/user/" + id).then(res =>{
            if (res) {
                this.$message.success("Delete successed")
                this.load()
            } else {
                this.$message.error("Delete failed")
            }
        })
    },

    handleSelectionChange(val){
        console.log(val)
        this.multipleSelection = val
    },

    delBatch() {
        let ids = this.multipleSelection.map(v =>v.id)
        request.post("/user/del/batch", ids).then(res =>{
            if (res) {
                this.$message.success("Delete successed")
                this.load()
            } else {
                this.$message.error("Delete failed")
            }
        })
    },

    save() {
        this.request.post("/user", this.form).then(res => {
            if (res) {
                this.$message.success("Save successed")
                this.dialogFormVisible = false
                this.load()
            } else {
                this.$message.error("Save failed")
            }
        })
    },

    load() {
        this.request.get("/user/page", {
            params: {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                username: this.username,
                email: this.email,
                address: this.address
            }
        }).then(res => {
            console.log(res)

            this.tableData = res.records
            this.total = res.total
        })
    },

    reset(){
        this.username = ""
        this.email = ""
        this.address = ""
        this.load()
    }
  }
}
</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>
