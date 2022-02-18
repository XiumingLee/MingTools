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
    },
    methods: {
    }
}

