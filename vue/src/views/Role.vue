<template>
    <div>
			<div style="margin: 10px 0">
				<el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="name"></el-input>
				<el-button class="ml-5" type="primary" @click="load()">Search</el-button>
				<el-button type="warning" @click="reset()">Reset</el-button>
			</div>

			<!-- Add -->
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

			</div>

			<el-table :data="tableData" border stripe
								@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>

				<el-table-column prop="id" label="ID" width="80"></el-table-column>
				<el-table-column prop="name" label="Name"></el-table-column>
				<el-table-column prop="description" label="Description"></el-table-column>
				<el-table-column prop="flag" label="Flag"></el-table-column>

				<el-table-column label="Action">
					<template slot-scope="scope">

						<!-- Set menu -->
						<el-button type="info" @click="selectMenu(scope.row.id)">Set menu <i class="el-icon-menu"></i></el-button>

						<!-- edit -->
						<el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>

						<!-- delete -->
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

			<!-- Page settings -->
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

			<!-- Menu info -->
			<el-dialog title="Menu Setting" :visible.sync="menuDialogVisible" width="30%" style="padding: 0 50px">
				<el-tree
					:props="props"
					:data="menuData"
					show-checkbox
					node-key="id"
					ref="tree"
					:default-expanded-keys="expands"
					:default-checked-keys="checks">

					<span class="custom-tree-node" slot-scope="{ node, data}">
						<span><i :class="data.icon" />  {{ data.name }}</span>
					</span>
				</el-tree>

				<div slot="footer" class="dialog-footer">
						<el-button @click="dialogFormVisible = false">Cancel</el-button>
						<el-button type="primary" @click="saveRoleMenu">Confirm</el-button>
				</div>
			</el-dialog>

			<!-- Role info -->
			<el-dialog title="Role Information" :visible.sync="dialogFormVisible" width="30%">
				<el-form label-width="100px" size="small">
						<el-form-item label="name">
							<el-input v-model="form.name" autocomplete="off"></el-input>
						</el-form-item>
						<el-form-item label="description">
							<el-input v-model="form.description" autocomplete="off"></el-input>
						</el-form-item>
						<el-form-item label="flag">
							<el-input v-model="form.flag" autocomplete="off"></el-input>
						</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
						<el-button @click="dialogFormVisible = false">Cancel</el-button>
						<el-button type="primary" @click="save">Confirm</el-button>
				</div>
			</el-dialog>

    </div>
</template>

<script>
import { registerTheme } from 'echarts';

export default {
	name: "User",
	data() {
		return {
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			name: "",
			dialogFormVisible: false,
			menuDialogVisible: false,
			form: {},
			multipleSelection: [],
			menuData:[],
			props: {
				label: "name",
			},
			expands: [],
			checks: [],
			roleId: 0
		}
	},

	created() {
		this.load()
	},

	methods: {
		load() {
			this.request.get("/role/page", {
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					name: this.name,
				}
			}).then(res => {
				this.tableData = res.data.records
				this.total = res.data.total
			})
		},

		saveRoleMenu() {
			console.log("hi")
			this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
				if (res.code === '200') {
					this.$message.success("Set successfully")
					this.menuDialogVisible = false
				} else {
					this.$message.error("res.msg")
				}
			})
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
			this.request.delete("/role/" + id).then(res =>{
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
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/role/del/batch", ids).then(res =>{
				if (res.data) {
					this.$message.success("Delete successed")
					this.load()
				} else {
					this.$message.error("Delete failed")
				}
			})
		},

		save() {
			this.request.post("/role", this.form).then(res => {
				if (res.code === '200') {
					this.$message.success("Save successed")
					this.dialogFormVisible = false
					this.load()
				} else {
					this.$message.error("Save failed")
				}
			})
		},

		reset(){
			this.name = ""
			this.load()
		},

		selectMenu(id) {
			this.menuDialogVisible = true
			this.roleId = id

			this.request.get("/menu").then(res => {
				this.menuData = res.data
				this.expands = this.menuData.map(v => v.id)
			})

			this.request.get("/role/roleMenu/" + this.roleId,).then(res => {
				this.checks = res.data
			})
		},
	}
}
</script>

<style>
/* .headerBg {
    background: #f5f5f5!important;
} */
</style>