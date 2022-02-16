import  '../js/vue-router@3.5.3.js'
import Layout from '../layout/index.js'
// 静态路由
const constantRouterMap = [
    {
        path: '',
        component: Layout,
        redirect: 'index',
        children: [
            {
                path: '',
                component: () => import('../../views/login.js'),
                name: 'Index',
                meta: { title: '首页', icon: 'dashboard', affix: true }
            }
        ]
    },
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path(.*)',
                component: () => import('../../views/redirect.js')
            }
        ]
    },
    {
        path: '/404',
        component: () => import('../../views/error/404.js'),
        hidden: true
    },
    {
        path:'/login',
        component:()=>import('../../views/login.js'),
        meta:{ title:'首页' },
        hidden: true
    },
    {
        path:'/register',
        component:()=>import('../../views/register.js'),
        meta:{ title:'request' },
        hidden: true
    },

]

export default new VueRouter({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
})
