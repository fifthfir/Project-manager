<template>
    <div>
			<div style="margin: 10px 0">
				<el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="name"></el-input>
				<el-button class="ml-5" type="primary" @click="load()">Search</el-button>
				<el-button type="warning" @click="reset()">Reset</el-button>
			</div>

			<div style="margin: 10px 0">
				<el-button type="primary" @click="handleAdd()">Add <i class="el-icon-circle-plus-outline"></i></el-button>
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
								row-key="id"
								default-expanded-all
								@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>

				<el-table-column prop="id" label="ID" width="80"></el-table-column>
				<el-table-column prop="name" label="Name"></el-table-column>
				<el-table-column prop="path" label="Path"></el-table-column>

				<el-table-column prop="icon" label="Icon">
					<template slot-scope="scope">
						<i :class="scope.row.icon"  style="font-size: 17px"/>
					</template>
				</el-table-column>

				<el-table-column prop="description" label="Description"></el-table-column>


				<el-table-column label="Action" width="400px">
					<template slot-scope="scope">

						<!-- Add -->
						<el-button type="primary" @click="handleAdd(scope.row.id)"
											 v-if="!scope.row.pid && !scope.row.path"
								>Add child
							<i class="el-icon-edit"></i>
						</el-button>

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

			<el-dialog title="Menu Information" :visible.sync="dialogFormVisible" width="30%">
				<el-form label-width="100px" size="small">
						<el-form-item label="name">
							<el-input v-model="form.name" autocomplete="off"></el-input>
						</el-form-item>
						<el-form-item label="path">
							<el-input v-model="form.path" autocomplete="off"></el-input>
						</el-form-item>

						<!-- Icon -->
						<el-form-item label="icon">
								<el-select clearable v-model="form.icon" placeholder="Please select" style="width: 100%">
									<el-option
										v-for="item in options"
										:key="item.name"
										:label="item.name"
										:value="item.value">
										<i :class="item.value" /> {{ item.name }}
									</el-option>
								</el-select>
						</el-form-item>

						<el-form-item label="description">
							<el-input v-model="form.description" autocomplete="off"></el-input>
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
	data() {
		return {
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			name: "",
			dialogFormVisible: false,
			form: {},
			multipleSelection: [],
			options: []
		}
	},

	created() {
		this.load()
	},

	methods: {
		load() {
				this.request.get("/menu", {
						params: {
								name: this.name,
						}
				}).then(res => {
					this.tableData = res.data

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

		handleAdd(pid) {
			this.dialogFormVisible = true
			this.form = {}
			if (pid) {
				this.form.pid = pid
			}
		},

		handleEdit(row){
				this.form = Object.assign({}, row)  // In order not to modify before clicking cancel
				this.dialogFormVisible = true

				this.request.get("/menu/icons").then(res => {

					this.options = res.data
					console.log(this.options)

				})
		},

		handleDelete(id){
				this.request.delete("/menu/" + id).then(res =>{
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
				this.request.post("/menu/del/batch", ids).then(res =>{
						if (res.data) {
								this.$message.success("Delete successed")
								this.load()
						} else {
								this.$message.error("Delete failed")
						}
				})
		},

		save() {
				this.request.post("/menu", this.form).then(res => {
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
				window.open("http://localhost:8148/menu/export")
		},

		handleExcelImportSuccess() {
				this.$message.success("Import successfully")
				this.load()
		}
	}
}
</script>

<style>
</style>