import Hamburger from './hamburger/index.js'
import Breadcrumb from './breadcrumb/index.js'
import Github from './github/index.js'

import AppConfig from '../../../config/config.js'

export default {
    template: `
<div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebarOpened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container"/>

    <div class="right-menu">
      <template>
        
        <el-tooltip content="源码地址" effect="dark" placement="bottom">
          <github id="github" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
</div>
    `,
    name: 'Navbar',
    components: {Hamburger,Breadcrumb,Github},
    data: function () {
        return {
            avatar: '/common/assets/image/avatar.jpg'
        }
    },
    computed: {
        sidebarOpened() {
            return AppConfig.settings.sidebar.opened;
        }
    },
    mounted() {},
    methods: {
        toggleSideBar() {
            AppConfig.settings.sidebar.opened = !AppConfig.settings.sidebar.opened;
            console.log("点击了toggleSideBar！！")
        },
        async logout() {
            this.$confirm('确定注销并退出系统吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                console.log("退出系统了，嘎嘎！")
            }).catch(() => {});
        }
    },
}
