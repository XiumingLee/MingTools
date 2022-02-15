import  '../js/vue-router@3.5.3.js'

// 静态路由
const constantRouterMap = [
    {
        path:'/login',
        component:()=>import('../../module/login.js'),
        meta:{ title:'首页' },
        hidden: true
    },
    {
        path:'/register',
        component:()=>import('../../module/register.js'),
        meta:{ title:'request' },
        hidden: true
    },

]

export default new VueRouter({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
})
