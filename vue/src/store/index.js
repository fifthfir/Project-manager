import Vue from 'vue'
import Vuex from 'vuex'
import router, { resetRouter } from "@/router";

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		currentPathName: ''
	},
	mutations: {
		setPath (state) {
			state.currentPathName = localStorage.getItem("currentPathName")
		},

		logout() {
			// clear storage
			localStorage.removeItem("user")
			localStorage.removeItem("menus")
			router.push("/login")

			// reset routes
			resetRouter()
		}
	}
})

export default store