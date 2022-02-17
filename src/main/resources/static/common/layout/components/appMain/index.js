export default {
    template: `
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
    `,
    name: 'AppMain',
    computed: {
        cachedViews() {
            // todo : this.$store.state.tagsView.cachedViews
            //return this.$store.state.tagsView.cachedViews
            return {}
        },
        key() {
            return this.$route.path
        }
    }
}
