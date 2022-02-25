export default {
    template: `
<div class="app-container home">
        <h2> {{ message }} </h2>
</div>
`,
    name: 'Home',
    data: function () {
        return {
            message: 'Hello MingTools!'
        }
    },
    mounted() {
        navigator.clipboard
            .read()
            .then((v) => {
                console.log("获取剪贴板成功：", v);
            })
            .catch((v) => {
                console.log("获取剪贴板失败: ", v);
            });
    },
    methods: {
    }
}

