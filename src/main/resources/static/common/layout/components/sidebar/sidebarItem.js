import {isExternal} from '../../../utils/validate.js';
import {pathResolve} from '../../../utils/path.js';
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
            if (isExternal(routePath)) {
                return routePath
            }
            if (isExternal(this.basePath)) {
                return this.basePath
            }
            if (routeQuery) {
                let query = JSON.parse(routeQuery);
                //return { path: path.resolve(this.basePath, routePath), query: query }
                return { path: pathResolve(this.basePath, routePath), query: query };
            }
            //return path.resolve(this.basePath, routePath)
            return pathResolve(this.basePath, routePath);

        }
    }
}