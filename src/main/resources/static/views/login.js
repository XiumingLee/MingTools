import {get} from "../common/request/request.js";

export default {
    template: `<button v-on:click="test"> {{ message }} </button>`,
    name: 'login',
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    mounted() {
        console.log("我是mounted函数login")
    },
    methods: {
        test() {
            console.log("我是test方法")
            get("/test/hello").then(res => {
                this.message = res;
                console.log(res)
            })
        }
    }
}

