export default {
    template: `
  <div class="wscn-http404-container">
    <div class="wscn-http404">
      <div class="pic-404">
        <img class="pic-404__parent" src="/common/assets/image/404_images/404.png" alt="404">
        <img class="pic-404__child left" src="/common/assets/image/404_images/404_cloud.png" alt="404">
        <img class="pic-404__child mid" src="/common/assets/image/404_images/404_cloud.png" alt="404">
        <img class="pic-404__child right" src="/common/assets/image/404_images/404_cloud.png" alt="404">
      </div>
      <div class="bullshit">
        <div class="bullshit__oops">
          404错误!
        </div>
        <div class="bullshit__headline">
          {{ message }}
        </div>
        <div class="bullshit__info">
          对不起，您正在寻找的页面不存在。尝试检查URL的错误，然后按浏览器上的刷新按钮或尝试在我们的应用程序中找到其他内容。
        </div>
        <router-link to="/" class="bullshit__return-home">
          返回首页
        </router-link>
      </div>
    </div>
  </div>
    `,
    name: 'Page404',
    computed: {
        message() {
            return '找不到网页！'
        }
    }
}
