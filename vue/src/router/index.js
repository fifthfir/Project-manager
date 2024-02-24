import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manager.vue'),
    redirect: "/home",
    children: [
        { path: 'home', name: 'Home', component: () => import('../views/Home.vue')},
        { path: 'person', name: 'Person', component: () => import('../views/Person.vue')},
        { path: 'user', name: 'User management', component: () => import('../views/User.vue')},
        { path: 'role', name: 'Role management', component: () => import('../views/Role.vue')},
        { path: 'menu', name: 'Menu management', component: () => import('../views/Menu.vue')},
				{ path: 'file', name: 'File management', component: () => import('../views/MyFile.vue')}
    ]
  },

  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import('../views/About.vue')
  // },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
	{
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)
    store.commit("setPath")
    next()
})

export default router
