export default {
    template: `
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
    `,
    name: 'Git',
    data() {
        return {
            url: 'https://github.com/XiumingLee/MingTools'
        }
    },
    methods: {
        goto() {
            window.open(this.url)
        }
    }
}
