export default {
    name: 'MenuItem',
    functional: true,
    props: {
        icon: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        }
    },
    render(h, context) {
        const { icon, title } = context.props
        const vnodes = []

        if (icon) {
            // TODO: 添加，使页面支持svg-icon之类的标签
            //vnodes.push(<svg-icon icon-class={icon}/>)
            console.log("icon-class：" + icon)
        }

        if (title) {
            //vnodes.push(<span slot='title'>{(title)}</span>)
            console.log("slot='title'：" + title)
        }
        return vnodes
    }
}
