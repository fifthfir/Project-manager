<template>
	<div>
		<div style="margin: 20px 0">
			<div class="pd-10" style="font-size: 20px; color: #3f5efb" cursor: pointer>{{ this.article.name }}</div>
			<div>
				<i class="el-icon-user-solid"></i><span style="font-size: 15px; margin: 10px"> {{ this.article.user }}</span>
				<span style="font-size: 12px; margin: 10px"><i class="el-icon-time"></i> {{ this.article.time }}</span>
			</div>
		</div>

		<div style="margin: 20px 0">
			<mavon-editor
				class="md"
				:value="this.article.content"
				:subfield="false"
				:defaultOpen="'preview'"
				:toolbarsFlag="false"
				:editable="false"
				:scrollStyle="true"
				:ishljs="true"
				/>
		</div>

		<!-- Comment header -->
		<div style="margin: 30px 0">
			<div style="margin: 10px 0">
				<div style="border-bottom: 1px solid orangered; padding: 10px 0; font-size: 20px">Comment</div>
				<div style="padding: 10px 0">
				<el-input size="small" type="textarea" v-model="commentForm.content"></el-input>
			</div>

			<div class="pd-10" style="text-align: right">
				<el-button type="primary" size="small" @click="save">Comment</el-button>
			</div>
		</div>

		<!-- Comment -->
		<div>
			<div v-for="item in comments" :key="item.id" style="border-bottom: 1px solid #ccc; padding: 10px 0;">
				<div style="display: flex">

					<!-- Avatar -->
					<div style="width: 100px; text-align: center">
						<el-image :src="item.avatarUrl" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
					</div>

					<!-- Content -->
					<div style="flex: 1; font-size: 14px; padding: 5px 0; line-height: 25px;">
						<b>{{ item.nickname }}:  </b>
						<span>{{ item.content }}</span>

						<div style="display: flex; font-size: 12px; color: #9f9f9f; line-height: 20px; margin-top: 5px">

							<!-- Time -->
							<div style="width: 200px">
								<i class="el-icon-time"/>
								<span style="margin-left: 10px; margin-right: 20px;">{{ item.time }}</span>
							</div>

							<!-- Reply & delete -->
							<div style="text-align: right; flex: 1">
								<el-button style="margin-left: 5px; font-size: 12px" type="text" @click="handleReply(item.id)">Reply</el-button>
								<el-button style="color: red; font-size: 12px" type="text" @click="del(item.id)" v-if="user.id === item.userId">Delete</el-button>
							</div>
						</div>
					</div>

				</div>

				<!-- Reply -->
				<div  v-if="item.children.length" style="padding-left: 200px;">
					<div v-for="subItem in item.children" :key="subItem.id" style="border-bottom: 1px solid #ccc; background-color: #eef; padding-right: 10px 20px; display: flex;">

						<!-- Avatar -->
						<div style="width: 100px; text-align: center; padding-top: 10px">
							<el-image :src="subItem.avatarUrl" style="width: 40px; height: 40px; border-radius: 50%;"></el-image>
						</div>

						<!-- Content -->
						<div style="flex: 1; font-size: 14px; padding: 5px 0; line-height: 25px;">
							<b>{{ subItem.nickname }} </b>
							<span>Reply</span>
							<span style="color:#3a8ee6">@{{ subItem.pnickname }}: </span>
							<span>{{ subItem.content }}</span>

							<div style="display: flex; font-size: 12px; color: #9f9f9f; line-height: 20px; margin-top: 5px">
								<!-- Time -->
								<div style="width: 200px">
									<i class="el-icon-time"/>
									<span style="margin-left: 10px; margin-right: 20px;">{{ subItem.time }}</span>
								</div>

								<!-- Reply & delete -->
								<div style="text-align: right; flex: 1">
									<el-button style="margin-left: 5px; font-size: 12px" type="text" @click="handleReply(subItem.id)">Reply</el-button>
									<el-button style="color: red; font-size: 12px" type="text" @click="del(subItem.id)" v-if="user.id === subItem.userId">Delete</el-button>
								</div>
							</div>
						</div>

					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- Article view -->
	<el-dialog title="Reply" :visible.sync="replyFormVisible" width="60%">

		<el-form label-width="80px" size="small">
			<el-form-item label="Content">
				<el-input type="textarea" v-model="commentForm.contentReply"></el-input>
			</el-form-item>
		</el-form>

		<div slot="footer" class="dialog-footer">
			<el-button @click="replyFormVisible = false">Cancel</el-button>
			<el-button type="primary" @click="save">Reply</el-button>
		</div>

	</el-dialog>

	</div>
</template>

<script>

export default {
	name: "Article",
	data() {
		return {
			id: this.$route.query.id,
			article: {},
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
			comments: [],
			commentForm: {},
			replyFormVisible: false
		}
	},

	created() {
		this.load(),
		this.loadComment()
	},

	methods: {
		load() {
			this.request.get("/article/" + this.id).then(res => {
				this.article = res.data
			})
		},

		loadComment() {
			this.request.get("/comment/tree/" + this.id).then(res => {
				this.comments = res.data
			})
		},

		handleReply(pid) {
			this.commentForm = {}
			this.replyFormVisible = true
			this.commentForm.pid = pid;
		},

		save() {
			if (!this.user.id) {
				this.$message.warning("Please login first")
				return
			}

			if(!this.commentForm.content) {
				this.commentForm.content = this.commentForm.contentReply
				this.commentForm.contentReply = ''
			}

			this.commentForm.articleId = this.id
			this.commentForm.userId = this.user.id
			this.request.post("/comment", this.commentForm).then(res => {
				if (res.code === '200') {
					this.$message.success("Comment successed")
					this.commentForm = {}
					this.loadComment()
				} else {
					this.$message.error("res.msg")
				}
			})

			this.replyFormVisible = false
		},

		del(id) {
			this.request.delete("/comment/" + id).then(res =>{
					if (res.code === '200') {
							this.$message.success("Delete successed")
							this.loadComment()
					} else {
							this.$message.error("Delete failed")
					}
			})
		},
	}
}

</script>

<style>
.headerBg {
    background: #f5f5f5!important;
}
</style>