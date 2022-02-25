const tagAndTagSpacing = 4 // tagAndTagSpacing
export default {
    template: `
  <el-scrollbar ref="scrollContainer" :vertical="false" class="scroll-container" @wheel.native.prevent="handleScroll">
    <slot />
  </el-scrollbar>
    `,
    name: 'ScrollPane',
    data() {
        return {
            left: 0
        }
    },
    computed: {
        scrollWrapper() {
            return this.$refs.scrollContainer.$refs.wrap
        }
    },
    mounted() {
        this.scrollWrapper.addEventListener('scroll', this.emitScroll, true)
    },
    beforeDestroy() {
        this.scrollWrapper.removeEventListener('scroll', this.emitScroll)
    },
    methods: {
        handleScroll(e) {
            const eventDelta = e.wheelDelta || -e.deltaY * 40
            const $scrollWrapper = this.scrollWrapper
            $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft + eventDelta / 4
        },
        emitScroll() {
            this.$emit('scroll')
        },
        moveToTarget(currentTag) {
            const $container = this.$refs.scrollContainer.$el
            const $containerWidth = $container.offsetWidth
            const $scrollWrapper = this.scrollWrapper
            const tagList = this.$parent.$refs.tag

            let firstTag = null
            let lastTag = null

            // find first tag and last tag
            if (tagList.length > 0) {
                firstTag = tagList[0]
                lastTag = tagList[tagList.length - 1]
            }

            if (firstTag === currentTag) {
                $scrollWrapper.scrollLeft = 0
            } else if (lastTag === currentTag) {
                $scrollWrapper.scrollLeft = $scrollWrapper.scrollWidth - $containerWidth
            } else {
                // find preTag and nextTag
                const currentIndex = tagList.findIndex(item => item === currentTag)
                const prevTag = tagList[currentIndex - 1]
                const nextTag = tagList[currentIndex + 1]

                // the tag's offsetLeft after of nextTag
                const afterNextTagOffsetLeft = nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing

                // the tag's offsetLeft before of prevTag
                const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing

                if (afterNextTagOffsetLeft > $scrollWrapper.scrollLeft + $containerWidth) {
                    $scrollWrapper.scrollLeft = afterNextTagOffsetLeft - $containerWidth
                } else if (beforePrevTagOffsetLeft < $scrollWrapper.scrollLeft) {
                    $scrollWrapper.scrollLeft = beforePrevTagOffsetLeft
                }
            }
        }
    }
}
