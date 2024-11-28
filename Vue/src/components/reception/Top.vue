<template>
  <div class="top-content">
    <el-menu
      router
      :default-active="myPath"
      class="el-menu-welcome"
      mode="horizontal"
      background-color="#596275"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <div class="logo" @click="gotoWelcome">图书推荐</div>
      <el-menu-item route="/welcome/index" index="/welcome/index" class="item"
        >首页</el-menu-item
      >
      <el-menu-item route="/welcome/customize" index="/welcome/customize" class="item"
        >推荐</el-menu-item
      >
      <el-menu-item route="/welcome/new" index="/welcome/new" class="item"
        >全部推荐</el-menu-item
      >
      <el-menu-item route="/welcome/my" index="/welcome/my" class="item"
        >我的</el-menu-item
      >
      <el-menu-item v-if="this.id!=32">当前用户ID:{{ id }}</el-menu-item>
      <el-menu-item v-else-if="this.id==32">游客账户</el-menu-item>
      <el-menu-item>当前用户姓名:{{ name }}</el-menu-item>

      <el-tag @click="goSearch">搜索图书</el-tag>

      
    </el-menu>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import Search from "./Search.vue";
import Now from "./Now.vue";
export default {
  name: "BlogAside",
  components: { Search, Now },
  data() {
    return {
      myPath: "/welcome/index",
      isShow: true,
      id: Cookies.get("userID"),
      name: Cookies.get("username"),
    };
  },
  methods: {
    // 去首页
    gotoWelcome() {
      this.$router.push({ path: "/welcome" });
    },
    goSearch(){
      window.location.href="http://localhost:8080/search"
    },
   
  },
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
  
};
</script>

<style lang="scss" scope>
.el-menu-welcome {
  border-bottom: none !important;
  z-index: 9999;
  width: 100%;
  position: fixed !important;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 60px;
  .logo {
    cursor: pointer;
    position: absolute;
    left: 6%;
    font-size: 1.5rem;
  }
  .system {
    cursor: pointer;
    position: absolute;
    color: #fff;
    right: 5%;
    font-size: 1.125rem;
  }
}
</style>
