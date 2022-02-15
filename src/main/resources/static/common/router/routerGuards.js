// 导航卫士
import router from './router.js'

// 白名单
const whiteList = ['/login','search']
// 这里是在路由跳转之前，还有其他的方式，参考官方文档
router.beforeEach((to, from, next) => {
    console.log(`前往的Url：${to.path}`);
    if (whiteList.indexOf(to.path) !== -1) { // 在白名单中，直接进入
        next();
    }
    // 其他处理...
    console.log(`前往的Url：${to.path}`);
    next();

    // 动态添加路由.添加的和index.js格式一样即可。路由数组
    // router.addRoutes(routes: RouteConfig[]);

})
