import tab from './tab.js'

export default {
    install(Vue) {
        // 页签操作
        Vue.prototype.$tab = tab
    }
}
