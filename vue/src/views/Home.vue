<template>
	<div>
		<el-row :gutter=10 style="margin-bottom: 80px; margin-top: 20px">
		  <el-col :span="6">
				<el-card style="color: #409eff ;">
					<div>
							<i class="el-icon-user-solid" /> USERS
					</div>
					<div style="padding: 5px 0; text-align: center; font-weight: bold; font-size: 20px;">
						100
					</div>
				</el-card>
			</el-col>

			<el-col :span="6">
				<el-card style="color: #f56c6c; ">
					<div>
						<i class="el-icon-money" /> SALES
					</div>
					<div style="padding: 5px 0; text-align: center; font-weight: bold; font-size: 20px;">
						2,000,000
					</div>
				</el-card>
			</el-col>

			<el-col :span="6">
				<el-card style="color: #67c23a; ">
					<div>
						<i class="el-icon-bank-card" /> PROFITS
					</div>
					<div style="padding: 5px 0; text-align: center; font-weight: bold; font-size: 20px;">
						1,000,000
					</div>
				</el-card>
			</el-col>

			<el-col :span="6">
				<el-card style="color: #e6a23c; ">
					<div>
						<i class="el-icon-s-shop" /> LOCATIONS
					</div>
					<div style="padding: 5px 0; text-align: center; font-weight: bold; font-size: 20px;">
						200
					</div>
				</el-card>
			</el-col>
		</el-row>

		<el-row>
			<el-col :span="12">
				<div id="main" style="width: 500px; height: 500px; margin-left: 40px;"></div>
			</el-col>

			<el-col :span="12">
				<div id="pie" style="width: 500px; height: 500px"></div>
			</el-col>
		</el-row>

	</div>
</template>

<script>
import * as echarts from 'echarts'

export default {
	name: "Home",
	data() {
		return {

		}
	},

	mounted() {
		var option = {
			title: {
				text: "Quarterly Customers",
				subtext: "Trends",
				left: 'center'
			},
			tooltip: {
				trigger: 'item'
			},
			legend: {
				orient: 'vertical',
				left: 'left'
			},
			xAxis: {
				type: 'category',
				data: ["q1", "q2", "q3", "q4"]
			},
			yAxis: {
				type: 'value'
			},
			series: [
				{
					name: "123",
					data: [],
					type: 'line'
				},
				{
					name: "456",
					data: [],
					type: 'bar'
				},
				{
					name: "678",
					data: [],
					type: 'bar'
				},
			]
		};

		var pieOption = {
			title: {
				text: 'Quarterly Customers',
				subtext: 'Percentage',
				left: 'center'
			},
			tooltip: {
				trigger: 'item'
			},
			legend: {
				orient: 'vertical',
				left: 'left'
			},
			series: [
				{
					name: "123",
					type: 'pie',
					radius: '60%',
					// center: ['25%', '50%'],

					label: {
						normal: {
							show: true,
							position: 'inner',
							textStyle: {
								fontWeight: 300,
								fontSize: 14,
								color: "#fff"
							},
							formatter: '{d}%'
						}
					},

					data: [],
					emphasis: {
						itemStyle: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				},
				{
					name: "456",
					type: 'pie',
					radius: '30%',
					// center: ['50%', '50%'],

					label: {
						normal: {
							show: true,
							position: 'inner',
							textStyle: {
								fontWeight: 300,
								fontSize: 14,
								color: "#fff"
							},
							formatter: '{c} / {d}%'
						}
					},

					data: [],
					emphasis: {
						itemStyle: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};

		var chartDom = document.getElementById('main');
		var myChart = echarts.init(chartDom);

		var chartDom = document.getElementById('pie');
		var pieChart = echarts.init(chartDom);

		this.request.get("/echarts/members").then(res => {
			option.series[0].data = res.data
			option.series[1].data = res.data
			option.series[2].data = [4, 7, 2, 5]
			console.log(res.data.y)

			myChart.setOption(option);

			pieOption.series[0].data = [
				{name: "quarter 1", value: res.data[0]},
				{name: "quarter 2", value: res.data[1]},
				{name: "quarter 3", value: res.data[2]},
				{name: "quarter 4", value: res.data[3]},
			]

			pieOption.series[1].data = [3, 9, 5, 7]

			pieChart.setOption(pieOption);
		})
	}
}

</script>

<style scoped>
</style>