import {Sidebar,Navbar} from './components/index.js'

export default {
    template: `
<div>
    <sidebar class="sidebar-container"/>
    <navbar />
    <!-- 以下存放二级页面 -->
    <section class="app-main">
        <transition name="fade-transform" mode="out-in">
          <router-view :key="key" />
        </transition>
    </section>
</div>
    `,
    name: 'Layout',
    components: {Sidebar,Navbar},
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    computed: {
        key() {
            return this.$route.path
        }
    },
    mounted() {

    },
    methods: {
        test() {
            console.log("我是test方法")
            get("/test/hello").then(res => {
                console.log(res)
            })
        }
    },
}
