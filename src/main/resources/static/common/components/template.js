export default {
    template: `
<div>
    <h1>我是模板文件！！！</h1>
</div>
    `,
    name: 'Template',
    components: {},
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
    }
}

/**
 export default {} 中this的使用

 // 作用域1
 export default {
    getA: () => {
    	// 作用域2
        this.getC() // this无法使用
    },
    getB() {
    	// 作用域1
        this.getC() // this正常使用
    },
    getC: () => {
    	// 作用域3
        console.log('function getC')
    }
}
 */
