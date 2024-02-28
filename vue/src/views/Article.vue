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
			<el-table-column prop="name" label="Title"></el-table-column>
			<el-table-column prop="content" label="Content" width="300">
				<template slot-scope="scope">
					<el-button @click="view(scope.row.content)" type="primary">View</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="user" label="Aurther"></el-table-column>
			<el-table-column prop="time" label="Post time"></el-table-column>

			<el-table-column label="Action" width="230">
				<template slot-scope="scope">

					<!-- Edit -->
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

		<!-- Article info -->
		<el-dialog title="Article Information" :visible.sync="dialogFormVisible" width="60%">
			<el-form label-width="100px" size="small">
				<el-form-item label="Title">
					<el-input v-model="form.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="Content">
					<mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
				</el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">Cancel</el-button>
				<el-button type="primary" @click="save">Confirm</el-button>
			</div>
		</el-dialog>

		<!-- Article view -->
		<el-dialog title="Article Overview" :visible.sync="articleViewVisible" width="60%">
			<el-card>
				<div>
					<mavon-editor
						class="md"
						:value="content"
						:subfield="false"
						:defaultOpen="'preview'"
						:toolbarsFlag="false"
						:editable="false"
						:scrollStyle="true"
						:ishljs="true"
					 />
				</div>
			</el-card>
		</el-dialog>

	</div>
</template>

<script>
import axios from 'axios'

export default {
	name: "Article",


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
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
			content: '',
			articleViewVisible: false
		}
	},

	created() {
		this.load()
	},

	methods: {
		load() {
			this.request.get("/article/page", {
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

		view(content) {
			this.content = content
			this.articleViewVisible = true

		},

		imgAdd(pos, $file) {
			let $vm = this.$refs.md
			const formData = new FormData();
			formData.append('file', $file);
			axios({
				url: 'http://localhost:8148/file/upload',
				method: 'post',
				data: formData,
				headeres: {'Content-Type': 'multipart/form-data'},
			}).then(res => {
				$vm.$img2Url(pos, res.data);
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
			this.request.post("/article", this.form).then(res => {
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
			this.request.delete("/article/" + id).then(res =>{
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
			this.request.post("/article/del/batch", ids).then(res =>{
				if (res.data) {
					this.$message.success("Delete successed")
					this.load()
				} else {
					this.$message.error("Delete failed")
				}
			})
		},

		changeEnable(row) {
			this.request.post("/article", row).then(res => {
				if (res.code === '200') {
					this.$message.success("Update successfully")
				}
			})
		},

		reset(){
				this.name = ""
				this.load()
		},
	}
}

</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>