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

	</div>
</template>

<script>

export default {
	name: "Article",
	data() {
		return {
			article: {},
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
		}
	},

	created() {
		this.load()
	},

	methods: {
		load() {
			const id = this.$route.query.id
			this.request.get("/article/" + id).then(res => {
				this.article = res.data
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