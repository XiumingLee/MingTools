import {get} from "../../common/request/request.js";

export default {
    name: '404',
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    mounted() {
        console.log("我是mounted函数")
    },
    methods: {
        test() {
            console.log("我是test方法")
            get("/test/hello").then(res => {
                this.message = res;
                console.log(res)
            })
        }
    },
    template: `<h3 v-on:click="test"> 404页面 {{ message }} </h3>`
}
