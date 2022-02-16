import Logo from "./logo.js";
export default {
    template: `
<div>
    <logo :collapse="false" />
</div>`,
    name: 'Sidebar',
    components: { Logo },
    data: function () {
        return {
            message: 'Hello Vue!'
        }
    },
    mounted() {
    },
    methods: {
        test() {}
    }
}
