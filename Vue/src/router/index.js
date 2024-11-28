import Vue from 'vue'
import VueRouter from 'vue-router'
import Cookies from 'js-cookie'
// 导入需要的路由组件
const Login = () => import('@/views/Login/Login.vue')
const Register = () => import('@/views/Register/Register.vue')
const Welcome = () => import('@/views/Welcome/Welcome.vue')
const Home = () => import('@/views/Home/Home.vue')
const Index = () => import('@/components/reception/Index.vue')
const Detail = () => import('@/components/reception/Detail.vue')
//const Detail = () => import('@/components/reception/Try.vue')


const Customize = () => import('@/components/reception/Customize.vue')
const My = () => import('@/components/reception/My.vue')
const New = () => import('@/components/reception/Now.vue')

const User = () => import('@/components/system/User.vue')
const Other = () => import('@/components/reception/Other.vue')

const Book = () => import('@/components/system/Book.vue')

const BookAdd = () => import('@/components/system/BookAdd.vue')
const BookChange = () => import('@/components/system/BookChange.vue')

const Search = () => import('@/components/reception/Search.vue')

const Read = () => import('@/components/reception/Read.vue')


const SystemLogin = () => import('@/views/Home/SystemLogin.vue')


const originalPush = VueRouter.prototype.push


VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  // 带有 name 名称的路由规则，叫做"命名路由"
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login, name: 'login', meta: { title: '登录' } },
  // 注册页面
  { path: '/register', component: Register, name: 'register', meta: { title: '注册' } },
  {
    path: '/welcome', component: Welcome, name: 'welcome', redirect: '/welcome/index',
    children: [
      { path: 'index', component: Index, name: 'index', meta: { title: '首页' } },
      { path: 'customize', component: Customize, name: 'customize', meta: { title: '推荐' } },
      { path: 'my', component: My, name: 'my', meta: { title: '我的' } },
      { path: 'new', component: New, name: 'new', meta: { title: '全部' } },
      { path: 'other', component: Other, name: 'other', meta: { title: 'ta的空间' } },
    ]
  },


  { path: '/detail', component: Detail, name: 'detail', meta: { title: '详情' } },

  { path: '/search', component: Search, name: 'search', meta: { title: '搜索' } },

  { path: '/read', component: Read, name: 'read', meta: { title: '阅读' } },

  //{ path: '/bookSection', component: BookSection, name: 'bookSection', meta: { title: '阅读' } },

  //管理端

  { path: '/systemLogin', component: SystemLogin, name: 'systemLogin', meta: { title: '管理员登陆' } },


  {
    path: '/home', component: Home, redirect: '/home/user',
    children: [
      { path: 'user', component: User, name: 'user', meta: { title: '用户管理' } },
      { path: 'book', component: Book, name: 'book', meta: { title: '图书管理' } },
      { path: 'bookAdd', component: BookAdd, name: 'bookAdd', meta: { title: '图书添加' } },
      { path: 'BookChange', component: BookChange, name: 'bookChange', meta: { title: '图书修改' } },
      
    ]
  },




]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  const paths = ['/welcome/index', '/welcome/customize', '/welcome/my', '/detail',]
  if (to.meta.title) {
    document.title = to.meta.title
  }



  if (Cookies.get('userID') == null || Cookies.get('userID') == '') {

    if (to.path == '/login' || to.path == '/welcome/index' || to.path == '/detail' || to.path == '/search' || to.path == '/systemLogin' || to.path == '/home'|| to.path == '/register') {
      next();
    } else {
      next({ path: '/login' })

    }
  }

  if (Cookies.get('userID') == 32 ) {

    if (to.path == '/login' || to.path == '/welcome/index' || to.path == '/detail' || to.path == '/search' || 
    to.path == '/systemLogin' || to.path == '/home'|| to.path == '/register' ||to.path == '/welcome/new'||to.path == '/welcome/my') {
      next();
    } else {


      var x;
      var r=confirm("游客用户暂无权限访问此功能，请先登录！");
      if(r==true){
        x="前往登录"
        next({ path: '/login' })
      }else{
        x="取消"
        router.go(0)
      }

      //document.write(x)

      // confirm("游客用户暂无权限访问此功能，请先登录！");

      // alert("游客用户暂无权限访问此功能，请先登录！");
      // next({ path: '/login' })
      
    }
  }
 

  paths.forEach(item => {
    if (to.path === item) {
      const state = JSON.parse(localStorage.getItem('state'))
      if (state.tokenInfo) {
        next()
      } else {
        next('/login')
      }
    } else next()
  })


})










export default router
