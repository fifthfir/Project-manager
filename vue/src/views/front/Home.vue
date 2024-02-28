<template>
	<div>

		<!-- Images -->
		<div style="margin: 10px 0">
			<el-carousel height="550px" :interval="3000">
				<el-carousel-item v-for="item in imgs" :key="item">
					<img :src="item" alt="" style="width: 100%">
				</el-carousel-item>
   		</el-carousel>
		</div>

		<div style="margin: 10px 0">
			<el-row :gutter="10">
				<el-col :span="6" v-for="item in files" :key="item.id" style="margin-bottom: 10px">
					<img :src="item.url" alt="" style="width: 100%">
					<div style="padding: 10px 0; border: 1px solid #ccc; text-align: center">
						<span style="color: #b3a9a9; font-size: 12px; padding: 10px 0">{{ item.name }}</span>
						<div style="padding: 10px"><el-button type="primary">Buy</el-button></div>
					</div>
				</el-col>

				<el-col :span="6">

				</el-col>

				<el-col :span="6">

				</el-col>

				<el-col :span="6">

				</el-col>
			</el-row>
		</div>


	</div>
</template>

<script>
export default {
	name: "FrontHome",
	data() {
		return {
			imgs: [
				"https://cdn.cloudflare.steamstatic.com/steam/apps/367520/header.jpg?t=1695270428",
				"https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_801/b_white/f_auto/q_auto/ncom/software/switch/70010000003208/4643fb058642335c523910f3a7910575f56372f612f7c0c9a497aaae978d3e51",
			],
			files: []
		}
	},

	created() {
		this.request.get("/echarts/file/front/all").then(res => {
			console.log(res.data)
			this.files = res.data.filter(v => v.type === 'png' || v.type === 'jpg')
		})

	},

	methods: {

	}
}
</script>