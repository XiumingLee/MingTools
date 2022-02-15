export default {
    template: `
<div>
    布局页
    <p>
        <router-link to="/redirect/register">注册</router-link>
    </p>
    <!-- 以下存放二级页面 -->
    <section class="app-main">
        <transition name="fade-transform" mode="out-in">
          <router-view :key="key" />
        </transition>
    </section>
</div>
    `,
    name: 'Layout',
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
