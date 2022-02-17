import {isExternal} from '../../utils/validate.js';
export default {
    template: `
<template>
  <div v-if="isExternal" :style="styleExternalIcon" class="svg-external-icon svg-icon" v-on="$listeners" />
  <svg v-else :class="svgClass" aria-hidden="true" v-on="$listeners">
    <use :xlink:href="iconName" />
  </svg>
</template>
    `,
    name: 'SvgIcon',
    props: {
        iconClass: {
            type: String,
            required: true
        },
        className: {
            type: String,
            default: ''
        }
    },
    computed: {
        isExternal() {
            return isExternal(this.iconClass)
        },
        iconName() {
            return `/common/assets/svg/svg-symbols.svg#icon-${this.iconClass}`
        },
        svgClass() {
            if (this.className) {
                return 'svg-icon ' + this.className
            } else {
                return 'svg-icon'
            }
        },
        styleExternalIcon() {
            return {
                mask: `url(${this.iconClass}) no-repeat 50% 50%`,
                '-webkit-mask': `url(${this.iconClass}) no-repeat 50% 50%`
            }
        }
    }
}
