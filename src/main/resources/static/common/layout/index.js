import {Sidebar} from './components/index.js'

export default {
    template: `
<div>
    <sidebar class="sidebar-container"/>
    <!-- 以下存放二级页面 -->
    <section class="app-main">
        <transition name="fade-transform" mode="out-in">
          <router-view :key="key" />
        </transition>
    </section>
</div>
    `,
    name: 'Layout',
    components: {Sidebar},
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
        console.log("我是mounted函数layout")
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
