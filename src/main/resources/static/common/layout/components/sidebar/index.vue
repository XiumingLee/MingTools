<template>
  <div :class="{'has-logo':showLogo}" :style="{  backgroundColor: themeStyle.menuBackground }">
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
</template>

<script>
import {mapGetters, mapState} from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";

export default {
  components: {SidebarItem, Logo},
  computed: {
    sidebarRouters() {
      return {}
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
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    themeStyle() {
      return {};
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  }
};
</script>
