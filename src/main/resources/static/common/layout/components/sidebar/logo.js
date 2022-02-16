import Config from '../../../config/config.js';
export default {
    template: `
  <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: themeStyle.menuBackground  }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 v-else class="sidebar-title" :style="{ color: themeStyle.menuColor }">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 class="sidebar-title" :style="{color: themeStyle.menuColor }">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
    `,
    name: 'SidebarLogo',
    props: {
        collapse: {
            type: Boolean,
            required: true
        }
    },
    computed: {
        themeStyle() {
            return Config.style;
        },
    },
    data: function () {
        return {
            title: 'MingTools工具集',
            logo: '/common/assets/logo/logo.png',
        }
    },
    mounted() {
    },
    methods: {
    }
}
