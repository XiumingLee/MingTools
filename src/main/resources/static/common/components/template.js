// 模板文件
import {Sidebar} from './components/index.js'

export default {
    template: `
<div>
    <h1>我是模板文件！！！</h1>
</div>
    `,
    name: 'Template',
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
    mounted() {},
    methods: {
        test() {
            console.log("我是test方法")
            get("/test/hello").then(res => {
                console.log(res)
            })
        }
    },
}
