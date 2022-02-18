import ScrollPane from './scrollPane.js'
import { pathResolve } from '../../../utils/path.js'
import AppConfig from '../../../config/config.js'
export default {
    template: `
  <div id="tags-view-container" class="tags-view-container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper" @scroll="handleScroll">
      <router-link
        v-for="tag in visitedViews"
        ref="tag"
        :key="tag.path"
        :class="isActive(tag)?'active':''"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
        tag="span"
        class="tags-view-item"
        :style="activeStyle(tag)"
        @click.middle.native="!isAffix(tag)?closeSelectedTag(tag):''"
        @contextmenu.prevent.native="openMenu(tag,$event)"
      >
        {{ tag.title }}
        <span v-if="!isAffix(tag)" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)" />
      </router-link>
    </scroll-pane>
    <ul v-show="visible" :style="{left:left+'px',top:top+'px'}" class="contextmenu">
      <li @click="refreshSelectedTag(selectedTag)"><i class="el-icon-refresh-right"></i> 刷新页面</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)"><i class="el-icon-close"></i> 关闭当前</li>
      <li @click="closeOthersTags"><i class="el-icon-circle-close"></i> 关闭其他</li>
      <li v-if="!isFirstView()" @click="closeLeftTags"><i class="el-icon-back"></i> 关闭左侧</li>
      <li v-if="!isLastView()" @click="closeRightTags"><i class="el-icon-right"></i> 关闭右侧</li>
      <li @click="closeAllTags(selectedTag)"><i class="el-icon-circle-close"></i> 全部关闭</li>
    </ul>
  </div>
    `,
    components: { ScrollPane },
    data() {
        return {
            visible: false,
            top: 0,
            left: 0,
            selectedTag: {},
            affixTags: []
        }
    },
    // todo: 修改 $store相关内容 ，使用 $store 搜索
    computed: {
        visitedViews() {
            return this.$store.state.tagsView.visitedViews;
        },
        routes() {
            return this.$store.state.menu.routes
        },
        theme() {
            return AppConfig.style.theme;
        }
    },
    watch: {
        $route() {
            this.addTags()
            this.moveToCurrentTag()
        },
        visible(value) {
            if (value) {
                document.body.addEventListener('click', this.closeMenu)
            } else {
                document.body.removeEventListener('click', this.closeMenu)
            }
        }
    },
    mounted() {
        this.initTags()
        this.addTags()
    },
    methods: {
        isActive(route) {
            return route.path === this.$route.path
        },
        activeStyle(tag) {
            if (!this.isActive(tag)) return {};
            return {
                "background-color": this.theme,
                "border-color": this.theme
            };
        },
        isAffix(tag) {
            return tag.meta && tag.meta.affix
        },
        isFirstView() {
            try {
                return this.selectedTag.fullPath === this.visitedViews[1].fullPath || this.selectedTag.fullPath === '/index'
            } catch (err) {
                return false
            }
        },
        isLastView() {
            try {
                return this.selectedTag.fullPath === this.visitedViews[this.visitedViews.length - 1].fullPath
            } catch (err) {
                return false
            }
        },
        filterAffixTags(routes, basePath = '/') {
            let tags = []
            routes.forEach(route => {
                if (route.meta && route.meta.affix) {
                    const tagPath = pathResolve(basePath, route.path)
                    tags.push({
                        fullPath: tagPath,
                        path: tagPath,
                        name: route.name,
                        meta: { ...route.meta }
                    })
                }
                if (route.children) {
                    const tempTags = this.filterAffixTags(route.children, route.path)
                    if (tempTags.length >= 1) {
                        tags = [...tags, ...tempTags]
                    }
                }
            })
            return tags
        },
        initTags() {
            const affixTags = this.affixTags = this.filterAffixTags(this.routes)
            for (const tag of affixTags) {
                // Must have tag name
                if (tag.name) {
                    this.$store.dispatch('tagsView/addVisitedView', tag)
                }
            }
        },
        addTags() {
            const { name } = this.$route
            if (name) {
                this.$store.dispatch('tagsView/addView', this.$route)
            }
            return false
        },
        moveToCurrentTag() {
            const tags = this.$refs.tag
            this.$nextTick(() => {
                for (const tag of tags) {
                    if (tag.to.path === this.$route.path) {
                        this.$refs.scrollPane.moveToTarget(tag)
                        // when query is different then update
                        if (tag.to.fullPath !== this.$route.fullPath) {
                            this.$store.dispatch('tagsView/updateVisitedView', this.$route)
                        }
                        break
                    }
                }
            })
        },
        refreshSelectedTag(view) {
            this.$tab.refreshPage(view);
        },
        closeSelectedTag(view) {
            console.log("关闭标签页！")
            this.$tab.closePage(view).then(({ visitedViews }) => {
                if (this.isActive(view)) {
                    this.toLastView(visitedViews, view)
                }
            })
        },
        closeRightTags() {
            this.$tab.closeRightPage(this.selectedTag).then(visitedViews => {
                if (!visitedViews.find(i => i.fullPath === this.$route.fullPath)) {
                    this.toLastView(visitedViews)
                }
            })
        },
        closeLeftTags() {
            this.$tab.closeLeftPage(this.selectedTag).then(visitedViews => {
                if (!visitedViews.find(i => i.fullPath === this.$route.fullPath)) {
                    this.toLastView(visitedViews)
                }
            })
        },
        closeOthersTags() {
            this.$router.push(this.selectedTag).catch(()=>{});
            this.$tab.closeOtherPage(this.selectedTag).then(() => {
                this.moveToCurrentTag()
            })
        },
        closeAllTags(view) {
            this.$tab.closeAllPage().then(({ visitedViews }) => {
                if (this.affixTags.some(tag => tag.path === this.$route.path)) {
                    return
                }
                this.toLastView(visitedViews, view)
            })
        },
        toLastView(visitedViews, view) {
            const latestView = visitedViews.slice(-1)[0]
            if (latestView) {
                this.$router.push(latestView.fullPath)
            } else {
                // now the default is to redirect to the home page if there is no tags-view,
                // you can adjust it according to your needs.
                if (view.name === 'Dashboard') {
                    // to reload home page
                    this.$router.replace({ path: '/redirect' + view.fullPath })
                } else {
                    this.$router.push('/')
                }
            }
        },
        openMenu(tag, e) {
            const menuMinWidth = 105
            const offsetLeft = this.$el.getBoundingClientRect().left // container margin left
            const offsetWidth = this.$el.offsetWidth // container width
            const maxLeft = offsetWidth - menuMinWidth // left boundary
            const left = e.clientX - offsetLeft + 15 // 15: margin right

            if (left > maxLeft) {
                this.left = maxLeft
            } else {
                this.left = left
            }

            this.top = e.clientY
            this.visible = true
            this.selectedTag = tag
        },
        closeMenu() {
            this.visible = false
        },
        handleScroll() {
            this.closeMenu()
        }
    }
}
