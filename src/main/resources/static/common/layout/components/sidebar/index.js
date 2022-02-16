import Logo from "./logo.js";
import SidebarItem from "./sidebarItem.js";
import Config from '../../../config/config.js';
import {Routers} from '../../../mock/data.js';

export default {
    template: `
  <div :class="{'has-logo':showLogo}" :style="{ backgroundColor: themeStyle.menuBackground }">
    <logo v-if="showLogo" :collapse="isCollapse"/>
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
        sidebarRouters() {
            return Routers;
        },
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
        showLogo() {
            return true;
        },
        isCollapse() {
            return false;
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
