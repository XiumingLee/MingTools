import {constantRoutes} from "../../router/router.js";
import {Routers} from '../../menu/index.js'
import Layout from '../../layout/index.js'

const menu = {
    state: {
        routes: [],
        addRoutes: [],
        sidebarRouters: []
    },
    mutations: {
        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes
            state.routes = constantRoutes.concat(routes)
        },
        SET_SIDEBAR_ROUTERS: (state, routes) => {
            state.sidebarRouters = routes
        },
    },
    actions: {
        // 生成路由
        GenerateRoutes({commit}) {
            return new Promise(resolve => {
                // 向后端请求路由数据
                const sdata = JSON.parse(JSON.stringify(Routers))
                const rdata = JSON.parse(JSON.stringify(Routers))
                const sidebarRoutes = filterAsyncRouter(sdata)
                const rewriteRoutes = filterAsyncRouter(rdata, false, true)
                rewriteRoutes.push({path: '*', redirect: '/404', hidden: true})
                commit('SET_ROUTES', rewriteRoutes)
                //commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))
                commit('SET_SIDEBAR_ROUTERS', sidebarRoutes);
                resolve(rewriteRoutes)
            })
        }
    }
}


// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
    return asyncRouterMap.filter(route => {
        if (type && route.children) {
            route.children = filterChildren(route.children)
        }
        if (route.component) {
            // Layout ParentView 组件特殊处理
            if (route.component === 'Layout') {
                route.component = Layout
            } else {
                route.component = loadView(route.component)
            }
        }
        if (route.children != null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children, route, type)
        } else {
            delete route['children']
            delete route['redirect']
        }
        return true
    })
}

function filterChildren(childrenMap, lastRouter = false) {
    let children = [];
    childrenMap.forEach((el, index) => {
        if (el.children && el.children.length) {
            if (el.component === 'ParentView' && !lastRouter) {
                el.children.forEach(c => {
                    c.path = el.path + '/' + c.path
                    if (c.children && c.children.length) {
                        children = children.concat(filterChildren(c.children, c))
                        return
                    }
                    children.push(c)
                })
                return
            }
        }
        if (lastRouter) {
            el.path = lastRouter.path + '/' + el.path
        }
        children = children.concat(el)
    })
    return children
}


function loadView(view) {
    // 使用 import 实现生产环境的路由懒加载
    return () => import(`${view}`)
}

export default menu
