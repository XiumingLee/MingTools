import {isExternal} from '../../../utils/validate.js';
import Item from './item.js'
import AppLink from './link.js'

export default {
    template: `
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children,item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)&&!item.alwaysShow">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path, onlyOneChild.query)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown':!isNest}">
          <item :icon="onlyOneChild.meta.icon||(item.meta&&item.meta.icon)" :title="onlyOneChild.meta.title" />
        </el-menu-item>
      </app-link>
      <template v-else>
         <a>不显示233</a>
      </template>
    </template>

    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-submenu>
  </div>
    `,
    name: 'SidebarItem',
    components: {Item, AppLink},
    props: {
        // route object
        item: {
            type: Object,
            required: true
        },
        isNest: {
            type: Boolean,
            default: false
        },
        basePath: {
            type: String,
            default: ''
        }
    },
    computed: {
        isExternal() {
            return isExternal(this.to)
        },
        type() {
            if (this.isExternal) {
                return 'a'
            }
            return 'router-link'
        }
    },
    data() {
        this.onlyOneChild = null
        return {}
    },
    methods: {
        hasOneShowingChild(children = [], parent) {
            console.log(children);
            console.log(parent);
            if (!children) {
                children = [];
            }
            const showingChildren = children.filter(item => {
                if (item.hidden) {
                    return false
                } else {
                    // Temp set(will be used if only has one showing child)
                    this.onlyOneChild = item
                    return true
                }
            })

            // When there is only one child router, the child router is displayed by default
            if (showingChildren.length === 1) {
                return true
            }

            // Show parent if there are no child router to display
            if (showingChildren.length === 0) {
                this.onlyOneChild = {...parent, path: '', noShowingChildren: true}
                return true
            }

            return false
        },
        resolvePath(routePath, routeQuery) {
            console.log(routePath);
            console.log(routeQuery);
            console.log("----------------------------------------------");
            if (isExternal(routePath)) {
                return routePath
            }
            if (isExternal(this.basePath)) {
                return this.basePath
            }

            // todo; 以下需要进行path解析
            if (routeQuery) {
                let query = JSON.parse(routeQuery);
                console.log(this.basePath);
                console.log(routePath);
                console.log(query);
                //return { path: path.resolve(this.basePath, routePath), query: query }
                return {path: routePath, query: query}
            }
            //return path.resolve(this.basePath, routePath)
            console.log("--------------this.basePath：----------");
            console.log(this.basePath);
            return routePath;
        }
    }
}
