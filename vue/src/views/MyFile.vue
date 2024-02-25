<template>
	<div>
		<div style="margin: 10px 0">
				<el-input style="width: 200px" placeholder="Name" suffix-icon="el-icon-search" v-model="name"></el-input>
				<el-button class="ml-5" type="primary" @click="load()">Search</el-button>
				<el-button type="warning" @click="reset()">Reset</el-button>
		</div>

		<div style="margin: 10px 0">
			<el-upload
					action="http://localhost:8148/file/upload"
					:show-file-list="false"
					:on-success="handleFileUploadSuccess"
					style="display: inline-block">
				<el-button type="primary">Upload File <i class="el-icon-top"></i></el-button>
			</el-upload>
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

		<el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID" width="80"></el-table-column>
			<el-table-column prop="name" label="file name"></el-table-column>
			<el-table-column prop="type" label="file type" width="120"></el-table-column>
			<el-table-column prop="size" label="file size"></el-table-column>
			<el-table-column label="download">
				<template slot-scope="scope">
					<el-button type="primary" @click="window.open(scope.row.url)">Download</el-button>
				</template>
			</el-table-column>
			<el-table-column label="enable">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
				</template>
			</el-table-column>
			<el-table-column label="Action">
				<template slot-scope="scope">
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

	</div>
</template>

<script>
export default {
	name: "File",
	data() {
		return {
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			name: "",
			multipleSelection: [],
			window: window
		}
	},

	created() {
		this.load()
	},

	methods: {
		handleFileUploadSuccess(res) {
			console.log(res)
			this.$message.success("Upload successfully")
			this.load()
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

		handleDelete(id){
				this.request.delete("/file/" + id).then(res =>{
						if (res.code === '200') {
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
				this.request.post("/file/del/batch", ids).then(res =>{
						if (res.data) {
								this.$message.success("Delete successed")
								this.load()
						} else {
								this.$message.error("Delete failed")
						}
				})
		},

		load() {
			console.log("Loading data...");
			this.request.get("/file/page", {
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

		changeEnable(row) {
			this.request.post("/file/update", row).then(res => {
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