import  '../js/vuex@3.6.2.js'
import app from './modules/app.js'
import tagsView from './modules/tagsView.js'
import getters from "./getters.js";
import menu from "./modules/menu.js";
import settings from "./modules/settings.js";

const store = new Vuex.Store({
    modules: {
        app,
        tagsView,
        menu,
        settings
    },
    getters
})

export default store
