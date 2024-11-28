<template>
  <div class="aside-content">
    <el-menu background-color="#576574" text-color="#fff" active-text-color="#54a0ff" :default-active="myPath"
      class="el-menu-vertical-demo" router unique-opened>

      <!-- <el-menu-item route="/home/view" index="/home/view">
        <i class="el-icon-s-platform" style="font-size: 24px;"></i>
        <span>视图</span>
      </el-menu-item> -->
      <el-menu-item route="/home/user" index="/home/user">
        <i class="el-icon-user" style="font-size: 24px"></i>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item route="/home/book" index="/home/book">
        <i class="el-icon-position" style="font-size: 24px"></i>
        <span>图书管理</span>
      </el-menu-item>
      <el-menu-item route="/home/bookAdd" index="/home/bookAdd">
        <i class="el-icon-position" style="font-size: 24px"></i>
        <span>图书添加</span>
      </el-menu-item>

      <el-menu-item route="/welcome" index="/">
        <i class="el-icon-position" style="font-size: 24px"></i>
        <span>前往前台</span>
      </el-menu-item>
      <!--
      <el-menu-item>
        
        <span>当前用户ID:</span>
        <span @click="getUserId()">{{ ID }}</span><br>
      </el-menu-item>
      -->
    </el-menu>

  </div>
</template>

<script>
import Cookies from "js-cookie";

export default {
  name: "Aside",
  data() {
    return {
      myPath: "",

    };
  },
  methods: {
    getUserId() {

    },

    checkStatus() {
      var spcID = Cookies.get('spcID')
      var spc = Cookies.get('spc')

      console.log(Cookies.get('spc') + '' + Cookies.get('spcID') )

      if (spc == '管理员' ) {
        alert('欢迎你，管理员ID:' + spcID + '!')
      } else {
        alert('请登录管理员账号！')
        window.location.href = "http://localhost:8080/systemLogin";
      }
    },

  },
  created() {
    //this.checkStatus();
  }
  /*
  watch: {
    $route: function () {
      this.myPath = this.$route.matched[1]
        ? this.$route.matched[1].path
        : this.$route.matched[0].path;
    },
  },
  created() {
    this.myPath = this.$route.matched[1]
      ? this.$route.matched[1].path
      : this.$route.matched[0].path;
  },
  */
};
</script>

<style lang="scss" scope>
.aside-content {
  width: 200px;
  height: 100%;
  background-color: #576574;
  color: #333;
  text-align: center;

  .el-menu {
    border: 0;
  }
}
</style>
