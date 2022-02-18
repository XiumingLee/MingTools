import  '../js/vue-router@3.5.3.js'
import Layout from '../layout/index.js'
// 静态路由
export const constantRoutes = [
    {
        path: '',
        component: Layout,
        redirect: 'index',
        children: [
            {
                path: '',
                component: () => import('/views/home/index.js'),
                name: 'Index',
                meta: { title: '首页', icon: 'dashboard', affix: true }
            }
        ]
    },
    //{
    //    name: "System",
    //    path: "/system",
    //    hidden: false,
    //    redirect: "noRedirect",
    //    component: Layout,
    //    alwaysShow: true,
    //    children: [
    //        {
    //            path: 'user',
    //            component: () => import('/views/register.js'),
    //            name: 'Register',
    //            meta: { title: '注册', icon: 'dashboard', affix: true }
    //        }
    //    ]
    //},
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
        component: () => import('/views/error/404/index.js'),
        hidden: true
    },

]

export default new VueRouter({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})
