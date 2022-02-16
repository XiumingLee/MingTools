import {isExternal} from '../../../utils/validate.js';

export default {
    template: `
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
    `,
    name: 'Link',
    props: {
        to: {
            type: [String, Object],
            required: true
        }
    },
    computed: {
        isExternal() {
            return isExternal(this.to)
        },
        type() {
            if (this.isExternal) {
                return 'a'
            }
            return 'router-link'
        }
    },
    methods: {
        linkProps(to) {
            if (this.isExternal) {
                return {
                    href: to,
                    target: '_blank',
                    rel: 'noopener'
                }
            }
            return {
                to: to
            }
        }
    }
}
