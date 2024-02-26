import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },

	{
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },

	{
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const resetRouter = () => {
	router.matcher = new VueRouter({
		mode: 'history',
		base: process.env.BASE_URL,
		routes
	})
}


export const setRoutes = () => {
	const storeMenus = localStorage.getItem("menus");

	if (storeMenus) {

		const manageRoute = {
			path: '/',
			name: 'Manage',
			component: () => import('../views/Manage.vue'),
			redirect: "/home",
			children: [
				{
					path: 'person',
					name: 'Person',
					component: () => import('../views/Person.vue')
				},

				{
					path: 'password',
					name: 'Password',
					component: () => import('../views/Password.vue')
				},
			]
		}

		const menus = JSON.parse(storeMenus)

		menus.forEach(item => {
			if (item.path) {
			let itemMenu = {
				path: item.path.replace("/", ""),
				name: item.name,
				component: () => import('../views/' + item.pagePath + '.vue'),
			}
			manageRoute.children.push(itemMenu)
			} else if (item.children.length) {
				item.children.forEach(item => {
					if (item.path) {
						let itemMenu = {
							path: item.path.replace("/", ""),
							name: item.name,
							component: () => import('../views/' + item.pagePath + '.vue'),
						}
						manageRoute.children.push(itemMenu)
					}
				})
			}
		})

		const currentRouteNames = router.getRoutes().map(v => v.name)
		if (!currentRouteNames.includes('Manage')) {
			router.addRoute(manageRoute)
		}
	}
}

setRoutes()

router.beforeEach((to, from, next) => {
	localStorage.setItem("currentPathName", to.name)
	store.commit("setPath")

	const storeMenus = localStorage.getItem("menus")
	if (!to.matched.length) {
		if (storeMenus) {
			next("/404")
		} else {
			next("/login")
		}
	}
	next()
})

export default router
