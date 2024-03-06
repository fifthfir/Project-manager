<template>
	<div>
		<div style="margin: 10px 0">
			<el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="name"></el-input>
			<el-button class="ml-5" type="primary" @click="load()">Search</el-button>
			<el-button type="warning" @click="reset()">Reset</el-button>
		</div>

		<div style="margin: 10px 0">
			<div style="padding: 20px 0; border-bottom: 1px dashed #ccc" v-for="item in tableData" :key="item.id">
				<div class="pd-10" style="font-size: 20px; color: #3f5efb; cursor: pointer"
				@click="$router.push('/front/articleDetail?id=' + item.id)">{{ item.name }}</div>
				<div>
					<i class="el-icon-user-solid"></i><span style="font-size: 15px; margin: 10px"> {{ item.user }}</span>
					<span style="font-size: 12px; margin: 10px"><i class="el-icon-time"></i> {{ item.time }}</span>
				</div>
			</div>
		</div>

		<div style="padding: 10px 0">
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page="pageNum"
				:page-sizes="[2, 5, 10, 20]"
				:page-size="pageSize"
				layout="total, prev, pager, next"
				:total="total">
			</el-pagination>
		</div>

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