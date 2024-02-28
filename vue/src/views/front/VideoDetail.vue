<template>
	<div>
		<div class="demo">
			<video-player class="video-player vjs-custom-skin"
										ref="videoPlayer"
										:playsinline="true"
										:options="playerOptions">

			</video-player>
		</div>
	</div>
</template>

<script>
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'

export default {
	name: "ViderDetail",

	components: {
		videoPlayer
	},

	data() {
		return {
			video: {},
			playerOptions: {
				playbackRates: [0.5, 1.0, 1.5, 2.0],
				autoplay: true,
				munted: false,
				loop: false,
				preload: 'auto',
				aspectRatio: '16:9',
				fluid: true,
				sources: [{
					type: "video/mp4",
					src: ''
				}],
				poster: '',
				notSupportMessage: 'Not support video type',
				controlBar: {
					timeDriver: true,
					durationDisplay: true,
					remainingTimeDisplay: true,
					fullscreenToggle: true
				}
			}

		}
	},

	created() {
		let id = this.$route.query.id

		this.request("/file/detail/" + id).then(res => {
			this.playerOptions.sources[0].src = res.data.url
		})
	},

	methods: {

	}
}
</script>