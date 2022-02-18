const getters = {
    sidebar: state => state.app.sidebar,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
    sidebarRouters:state => state.menu.sidebarRouters,
}
export default getters
