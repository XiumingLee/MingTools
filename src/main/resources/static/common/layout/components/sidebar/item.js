export default {
    template: `
<div>
    <svg-icon :icon-class='icon'/>
    <span slot='title'>{{title}}</span>
</div>
    `,
    name: 'MenuItem',
    props: {
        icon: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        }
    }
}
