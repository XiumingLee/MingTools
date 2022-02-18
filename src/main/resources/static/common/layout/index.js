import {Sidebar,Navbar,TagsView,AppMain} from './components/index.js'
import AppConfig from '../config/config.js'
//import store from '../store/index.js'
import  '../js/vuex@3.6.2.js'

export default {
    template: `
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
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
        ...Vuex.mapState({
            sidebar: state => state.app.sidebar,
        }),
        theme() {
            return AppConfig.style.theme;
        },
        classObj() {
            return {
                hideSidebar: !this.sidebar.opened,
                openSidebar: this.sidebar.opened,
            }
        }
    }
}
