import Logo from "./logo.js";
import SidebarItem from "./sidebarItem.js";
import Config from '../../../config/config.js';
import '../../../js/vuex@3.6.2.js'

export default {
    template: `
  <div class="has-logo" :style="{ backgroundColor: themeStyle.menuBackground }">
    <logo :collapse="isCollapse"/>
    <el-scrollbar :class="themeStyle.sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
          :default-active="activeMenu"
          :collapse="isCollapse"
          :background-color="themeStyle.menuBackground"
          :text-color="themeStyle.menuColor"
          :unique-opened="true"
          :active-text-color="themeStyle.theme"
          :collapse-transition="false"
          mode="vertical"
      >
        <sidebar-item
            v-for="(route, index) in sidebarRouters"
            :key="route.path  + index"
            :item="route"
            :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
`,
    name: 'Sidebar',
    components: {Logo, SidebarItem},
    computed: {
        // 要展示的路由
        ...Vuex.mapGetters(["sidebarRouters","sidebar"]),
        activeMenu() {
            const route = this.$route;
            const {meta, path} = route;
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            return path;
        },
        themeStyle() {
            return Config.style;
        },
        isCollapse() {
            return !this.sidebar.opened;
        }
    },
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    mounted() {
    },
    methods: {
        test() {
        }
    }
}
