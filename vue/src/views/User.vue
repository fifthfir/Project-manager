<template>
	<div>
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

				<el-upload
						action="http://localhost:8148/user/import"
						:show-file-list="false"
						accept=".xlsx"
						:on-success="handleExcelImportSuccess"
						style="display: inline-block">
				<el-button type="primary" class="ml-5">Import <i class="el-icon-bottom"></i></el-button>
				</el-upload>

				<el-button type="primary" @click="exp" class="ml-5">Export <i class="el-icon-top"></i></el-button>
		</div>

		<el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column prop="id" label="ID" width="80"></el-table-column>
				<el-table-column prop="username" label="username" width="140"></el-table-column>
				<el-table-column prop="nickname" label="nickname" width="120"></el-table-column>
				<el-table-column prop="email" label="email"></el-table-column>
				<el-table-column prop="phone" label="phone"></el-table-column>
				<el-table-column prop="address" label="address"></el-table-column>
				<el-table-column prop="role" label="role"></el-table-column>

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

				<!-- Role select -->
				<el-form-item label="role">
					<el-select clearable v-model="form.role" placeholder="Please select" style="width: 100%">
						<el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag">
						</el-option>
					</el-select>
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

	</div>
</template>

<script>
export default {
	name: "Header",
	data() {
		return {
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			username: "",
			email: "",
			address: "",
			dialogFormVisible: false,
			form: {},
			multipleSelection: [],
			roles: []
		}
	},

	created() {
		this.load()
	},

	methods: {
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
				this.tableData = res.data.records
				this.total = res.data.total
			})

			this.request.get("/role").then(res => {
				this.roles = res.data
				console.log("roles:")
				console.log(this.roles)
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
				this.request.delete("/user/" + id).then(res =>{
						if (res.code === '200') { // ??
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
				this.request.post("/user/del/batch", ids).then(res =>{
						if (res.code === '200') {
								this.$message.success("Delete successed")
								this.load()
						} else {
								this.$message.error("Delete failed")
						}
				})
		},

		save() {
				this.request.post("/user", this.form).then(res => {
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

		exp() {
				window.open("http://localhost:8148/user/export")
		},

		handleExcelImportSuccess() {
				this.$message.success("Import successfully")
				this.load()
		}
	}
}
</script>

<style>
/* .headerBg {
    background: #f5f5f5!important;
} */
</style>