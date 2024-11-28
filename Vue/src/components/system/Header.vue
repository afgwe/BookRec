<template>
  <div class="header-content">
    <div class="des">
      
      <!--
        <span>后台管理系统</span>
        -->
      <el-tooltip
        class="box-item"
        effect="customized"
        content='欢迎你，管理员用户!'
        raw-content
        placement="right"
      >
        <span>后台管理系统</span>
      </el-tooltip>
      
    </div>
    <el-button @click="outlogin" size="small">退出登录</el-button>
  </div>
</template>

<script>
import Cookies from 'js-cookie'

export default {
  name: 'Header',
  methods: {
    outlogin() {
      // 删除token
      //localStorage.setItem('state', {})
      //Cookies.remove('spc')
      //Cookies.remove('spcID')
      
      // 跳页面
      this.$router.push('/systemLogin')
    }
  },
  created(){
    if(!true){
      window.location.href = "http://localhost:8080/systemLogin"
    }
  }
}
</script>

<style>
.header-content {
  padding: 0 10px;
  background-color: #8395a7;
  line-height: 60px;
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
}











.tooltip-base-box {
  width: 600px;
}
.tooltip-base-box .row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.tooltip-base-box .center {
  justify-content: center;
}
.tooltip-base-box .box-item {
  width: 110px;
  margin-top: 10px;
}

</style>