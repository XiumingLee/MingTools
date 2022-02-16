// 导航卫士
import router from './router.js'

// 白名单
const whiteList = ['/login', 'search']
// 这里是在路由跳转之前，还有其他的方式，参考官方文档
router.beforeEach((to, from, next) => {

    // 当 path: "/" && `query: {redirect: '404'}`这种情况时，进行页面重定向。
    if (to.path === "/" && to.query['redirect']) {
        next({path: to.query['redirect'], replace: true})
        return;
    }

    // 在白名单中，直接进入
    if (whiteList.indexOf(to.path) !== -1) {
        next();
    }

    // 其他处理...
    console.log(`前往的Url：${to.path}`);
    next();

    // 动态添加路由.添加的和index.js格式一样即可。路由数组
    // router.addRoutes(routes: RouteConfig[]);

})
