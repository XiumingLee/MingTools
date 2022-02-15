import {get} from "../common/request/request.js";

export default {
    name: 'login',
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
                console.log(res)
            })
        }
    },
    template: `<button v-on:click="test"> {{ message }} </button>`
}

