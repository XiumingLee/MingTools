import {Sidebar,Navbar,TagsView,AppMain} from './components/index.js'
import AppConfig from '../config/config.js'

export default {
    template: `
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="sidebarOpened" class="drawer-bg" @click="handleClickOutside"/>
    <sidebar class="sidebar-container"/>
    <div class="hasTagsView" class="main-container">
      <div class="fixed-header">
        <navbar />
        <tags-view />
      </div>
      <app-main />
    </div>
  </div>
    `,
    name: 'Layout',
    components: {Sidebar,Navbar,TagsView,AppMain},
    computed: {
        theme() {
            return AppConfig.style.theme;
        },
        sidebarOpened() {
            return AppConfig.settings.sidebar.opened;
        },
        classObj() {
            return {
                hideSidebar: false,
                openSidebar: true,
                withoutAnimation: false,
            }

            // TODO：sidebar 设置处理 $store
            //return {
            //    hideSidebar: !this.sidebar.opened,
            //    openSidebar: this.sidebar.opened,
            //    withoutAnimation: this.sidebar.withoutAnimation,
            //}
        }
    },
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    methods: {
        handleClickOutside() {
            // TODO： $store处理
            //this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
            console.log("点击了handleClickOutside");
        }
    }
}
