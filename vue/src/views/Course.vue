<template>
	<div>
		<div style="margin: 10px 0">
			<el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="name"></el-input>
			<el-button class="ml-5" type="primary" @click="load()">Search</el-button>
			<el-button type="warning" @click="reset()">Reset</el-button>
		</div>

		<div style="margin: 10px 0">
			<el-button type="primary"
			  @click="handleAdd" v-if="user.role === 'ROLE_ADMIN'">Add <i class="el-icon-circle-plus-outline"></i></el-button>
			<el-popconfirm
				class="ml-5"
				confirm-button-text='Confirm'
				cancel-button-text='Do not delete'
				icon="el-icon-info"
				icon-color="red"
				title="Confirm to delete these data?"
				@confirm="delBatch"
			>
				<el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">Muti-Delete <i class="el-icon-remove-outline"></i></el-button>
			</el-popconfirm>
		</div>

		<el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID" width="80"></el-table-column>
			<el-table-column prop="name" label="course name"></el-table-column>
			<el-table-column prop="credit" label="course credit" width="120"></el-table-column>
			<el-table-column prop="times" label="course times"></el-table-column>
			<el-table-column prop="teacher" label="teacher"></el-table-column>

			<el-table-column label="state">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
				</template>
			</el-table-column>

			<el-table-column label="Action" width="230">
				<template slot-scope="scope">

					<!-- Select course -->
					<el-button type="success" @click=selectCourse(scope.row.id) v-if="user.role === 'ROLE_STUDENT'">Select <i class="el-icon-edit"></i></el-button>

					<el-button type="success" @click="handleEdit(scope.row)" v-if="user.role === 'ROLE_ADMIN'">Edit <i class="el-icon-edit"></i></el-button>

					<el-popconfirm
						class="ml-5"
						confirm-button-text='Confirm'
						cancel-button-text='Do not delete'
						icon="el-icon-info"
						icon-color="red"
						title="Confirm to delete?"
						@confirm="handleDelete(scope.row.id)"
					>
						<el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">Delete <i class="el-icon-remove-outline"></i></el-button>
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

		<!-- Course info -->
		<el-dialog title="Course Information" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="100px" size="small">
				<el-form-item label="name">
					<el-input v-model="form.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="credit">
					<el-input v-model="form.credit" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="times">
					<el-input v-model="form.times" autocomplete="off"></el-input>
				</el-form-item>

				<el-form-item label="teacher">
						<el-select clearable v-model="form.teacherId" placeholder="Please select" style="width: 100%">
							<el-option
								v-for="item in teachers"
								:key="item.id"
								:label="item.nickname"
								:value="item.id">{{ item.nickname }}
							</el-option>
						</el-select>
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
	name: "Course",
	data() {
		return {
			form: {},
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			name: "",
			multipleSelection: [],
			dialogFormVisible: false,
			teachers: [],
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
		}
	},

	created() {
		this.load()
	},

	methods: {
		load() {
			this.request.get("/course/page", {
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					name: this.name,
				}
			}).then(res => {
				this.tableData = res.data.records
				this.total = res.data.total
			})

			this.request.get("/user/role/ROLE_TEACHER").then(res => {
				this.teachers = res.data
			})
		},

		selectCourse(courseId) {
			this.request.post("/course/studentCourse/" + this.user.id + '/' + courseId).then(res => {
				if (res.code === '200') {
					this.$message.success("Select successed")
				} else {
					this.$message.error(res.msg)
				}
			})
		},

		handleAdd() {
			this.dialogFormVisible = true
			this.form = {}
		},

		handleEdit(row){
			this.form = Object.assign({}, row)  // In order not to modify before clicking cancel
			this.dialogFormVisible = true
		},

		save() {
			this.request.post("/course", this.form).then(res => {
				if (res.code === '200') {
					this.$message.success("Save successed")
					this.dialogFormVisible = false
					this.load()
				} else {
					this.$message.error("Save failed")
				}
			})
		},

		handleSizeChange(pageSize) {
			this.pageSize = pageSize
			this.load()
		},

		handleCurrentChange(pageNum) {
			this.pageNum = pageNum
			this.load()
		},

		handleDelete(id){
			this.request.delete("/course/" + id).then(res =>{
				if (res.code === '200') {
					this.$message.success("Delete successed")
					this.load()
				} else {
					this.$message.error("Delete failed")
				}
			})
		},

		handleSelectionChange(val){
			this.multipleSelection = val
		},

		delBatch() {
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/course/del/batch", ids).then(res =>{
				if (res.data) {
					this.$message.success("Delete successed")
					this.load()
				} else {
					this.$message.error("Delete failed")
				}
			})
		},

		changeEnable(row) {
			this.request.post("/course", row).then(res => {
				if (res.code === '200') {
					this.$message.success("Update successfully")
				}
			})
		},

		reset(){
				this.name = ""
				this.load()
		},

		download(url) {
			window.open(url)
		}
	}
}

</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>