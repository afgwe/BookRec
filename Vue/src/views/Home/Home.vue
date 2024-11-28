<template>
  <div class="home-content">
    <!-- 头部区域组件 -->
    <my-header></my-header>

    <!-- 下部区域 -->
    <div class="home-main-box">
      <!-- 左侧边栏组件 -->
      <div>
        <my-aside></my-aside>
      </div>
      <!-- 右侧主体组件 -->
      <el-main class="home-main-body">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </el-main>
    </div>
  </div>
</template>

<script>
import MyHeader from '@/components/system/Header.vue'
import MyAside from '@/components/system/Aside.vue'
import Cookies from 'js-cookie'

export default {
  name: 'Home',
  components: {
    'my-header': MyHeader,
    'my-aside': MyAside
  },

  methods: {
    async check() {

      try {

        //await this.jiance();

      } catch (error) {
        console.log(error)
      }


    },

    jiance() {
      var userID = Cookies.get(userID)
      var spc = Cookies.get('spc')

      console.log(Cookies.get('spc') + '' + Cookies.get('userID') + '' + Cookies.get('username'))



      if (spc == '管理员' ) {
        alert('欢迎你，管理员ID:' + userID + '!')
      } else {
        console.log('....' + Cookies.get('spc') + '' + Cookies.get('userID') + '' + Cookies.get('username'))
        alert('请登录管理员账号！')
        window.location.href = "http://localhost:8080/systemLogin";
      }
    }


  },



  mounted() {
    //this.check();
  }
}
</script>

<style lang="scss" scope>
.home-content {
  height: 100%;
  display: flex;
  flex-direction: column;

  .home-main-box {
    height: 100%;
    display: flex;

    .home-main-body {
      padding: 15px;
      flex: 1;

      .top {
        margin-bottom: 1.875rem;
      }

      .el-pagination {
        margin-top: 0.625rem;
      }
    }
  }

  ::-webkit-scrollbar {
    display: none;
  }
}
</style>