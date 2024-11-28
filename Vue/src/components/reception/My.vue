<template>
  <div class="my-content">
    <!--<img :src="avater" alt="" height="100px" style="border-radius: 50px;margin-left: 606px;"  />-->

    <template>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content ep-bg-purple" />
        </el-col>
        <el-col :span="6">
          <img :src="avater" alt="" height="150px" width="150px"
            style="border-radius: 150px;margin-left:150px;margin-top: 10px;clip: rect(0px,250px,200px,50px);" />



        </el-col>
        <el-col :span="6">
          <div>
            <form action="http://localhost:8888/users/add/avatar" enctype="multipart/form-data" method="post"
              target="iframe">

              <div>

                <el-row :gutter="20">
                  <el-col :span="10" style="text-align: center;">
                    <input type="file" name="file" @click="change" style="height:150px;width:150px;display:none"
                      id="input_c">
                    <input type="text" name="user_id" v-model="id" style="display: none;">
                    <div
                      style="text-align: center;height:150px;width:150px;vertical-align: middle;border:3px dashed #666;margin-top: 10px;"
                      @click="input_c()" id="imgshow">
                      <div id="replace">
                        <br><br><br>
                        <span style="margin-top: 75px;font-size: 20px;">+</span>

                      </div>

                    </div>
                    <input type="submit" name="submit" value="修改头像" id="submit"
                      style="width:100px;margin-top: 75px;border-radius: 50px;background-color: azure;margin-bottom: 45px;"
                      v-if="flag">
                  </el-col>
                  <el-col :span="10">

                  </el-col>
                </el-row>



              </div>
            </form>
            <!--
            <iframe src="" name="iframe" frameborder="0" style="display:none">{{result}}</iframe>
            -->
            <br>
            <br>
            <iframe src="" name="iframe" frameborder="0" style="display: none;"></iframe>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content ep-bg-purple" />
        </el-col>
      </el-row>
    </template>


    <!-- 个人信息 -->
    <el-card class="user-info">
      <el-descriptions class="margin-top" title="我的信息" :column="3" border>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"></i>用户id</template>
          <span v-if="userInfo.id != 32">{{ userInfo.id }}</span>
          <span v-if="userInfo.id == 32">游客账户</span>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"></i>用户名</template>
          {{ userInfo.username }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"></i>性别</template>
          {{ userInfo.sex }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-mobile-phone"></i>手机号</template>
          {{ userInfo.phone }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-location-outline"></i>邮箱</template>
          {{ userInfo.email }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-tickets"></i>身份</template>
          <el-tag size="small" v-if="userInfo.id != 32&&userInfo.identity==1">管理员</el-tag>
          <el-tag size="small" v-if="userInfo.id != 32&&userInfo.identity==2">普通用户</el-tag>
          <el-tag size="small" v-if="userInfo.id == 32">游客</el-tag>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label"><i class="el-icon-location-outline"></i>简介</template>
          {{ userInfo.info }}
        </el-descriptions-item>
      </el-descriptions>
      <span v-if="userInfo.id == 32" style="font-size: 14px;font-style: italic;">提示：当前用户为游客用户！部分功能将无法使用！</span>
    </el-card>

    <!-- 操作部分 -->
    <el-card class="edit">
      <div class="el-descriptions__header">
        <div class="el-descriptions__title">操作</div>
      </div>
      <div class="main">
        <el-tag class="item" @click="logout">退出登录</el-tag>
        <el-tag @click="goEnd">前往后台</el-tag>
      </div>

      <!--
      <div class="main" v-if="userInfo.identity == 2">
        <el-tag class="item" @click="goAdmin">前往管理员端</el-tag>
      </div>
      -->
    </el-card>

    <el-card class="edit">
      <div class="el-descriptions__title">修改密码</div>
      <br />
      <form action="" method="post">
        新密码：<input v-model="input" style="width: 240px" placeholder="Please input" />
        <br /><br />
        <el-tag class="item" @click="changepassword">修改密码</el-tag>
      </form>
    </el-card>

    <el-card class="edit">
      <div class="el-descriptions__title">修改信息</div>
      <br />
      <form action="" method="get">
        <span>  性  别：<input v-model.trim="sex" style="width: 240px" placeholder="Please input" /></span>
        
        <span>  邮  箱：<input v-model.trim="email" style="width: 240px" placeholder="Please input" /></span>
        <br />
        <span>  手  机：<input v-model.trim="phone" style="width: 240px" placeholder="Please input" /></span>
        
        <span>  简  介：<input v-model.trim="info" style="width: 240px" placeholder="Please input" /></span>
        <br /><br />
        <div style="margin-left: 43px;">
          <el-tag class="item" @click="changeinfo">修改信息</el-tag>
          <el-tag class="item" @click="resetinfo">重置信息</el-tag>
        </div>
        
      </form>
    </el-card>

    <el-card class="edit">
      <h4 class="section-title">我推荐的书籍</h4>
      <div class="book-list">
        <div v-for="(rating, index) in ratings" :key="index" class="book-item"
          style="margin: 20px; border: 1px black soild">
          <el-row>
            <el-col :span="8">
              <div class="book-details">
                <div @click="goToBookDetail((num = rating.book.bookId))">
                  <img :src="rating.book.coverImage" class="book-cover" style="width: 107px; height: 150px" />
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <p class="book-title">书名：{{ rating.book.bookName }}</p>
              <p class="book-author">作者: {{ rating.book.author }}</p>
              <span style="font-size: small; font-style: italic">评分：</span><el-rate v-model="rating.rating" :max="5"
                :texts="['oops', 'disappointed', 'normal', 'good', 'great']" show-text />
            </el-col>

            <el-col :span="8">
              <el-tag class="item" style="margin-top: 60px; margin-left: 20px"
                @click="deleteRating(rating.ratingId)">删除</el-tag>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-card>



    <!--我的评论-->
    <el-card class="edit">
      <h4 class="section-title">我的所有评论</h4>
      <div class="book-list">
        <div v-for="(comment, index) in comments" :key="index" class="book-item"
          style="margin: 20px; border: 1px black soild">



          <el-row>
            <el-col :span="30">
              <el-card>
                <el-col :span="8"><img :src="comment.book.coverImage" class="book-cover" style="width: 160px; height: 225px"
                  @click="goToBookDetail((num = comment.bookId))" /><br></el-col>
                  <el-col :span="15">
                    <div style="margin-left: 120px;">
                      <p>图书：{{ comment.book.bookName }}</p>
                      <p> 评论ID:{{ comment.commentId }}</p>
                      <p> 评论内容：{{ comment.content }}</p>
                      <p> 评论时间：{{ comment.createTime }}</p>
                    </div>
                    <div>
                      <el-tag class="item" style="margin-left: 120px;margin-bottom: 25px;"
                      @click="deleteComment(comment.commentId)">删除 </el-tag>
                    </div>
                    
                 
                  </el-col>
              </el-card>
            </el-col>
          </el-row>


        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import CryptoJS from "crypto-js";
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: "My",
  data() {
    return {
      userInfo: Array,
      comments: Array,
      ratings: Array,
      src: '',
      input: "",
      sex: "",
      email: "",
      phone: "",
      info: "",
      avater: '',
      id: '',
      flag: false,
      isShowImgPop: false,
      head: '',
    };
  },
  methods: {

    loadAvater() {
      var user_id = Cookies.get('userID')
      axios
        .post("http://localhost:8888/users/checkAvatar", {
          user_id: user_id
        })
        .then(result => {
          if (result.data == 1) {
            this.avater = 'http://localhost:8888/static/avatar/' + user_id + '.jpg';
          } else {
            this.avater = 'http://localhost:8888/static/avatar/avatar.jpg';
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    async change() {
      this.flag = !this.flag;
      var user_id = Cookies.get("userID");
      this.id = user_id;
      console.log('aaaaaa' + this.id);
      await this.sleep(3000);
      this.loadAvater();
    },

    async input_c() {
      var img = document.getElementById('input_c');
      img.click();
      console.log(img)
      //获取当前位置的元素方便替换
      var imgshow = document.getElementById('imgshow');
      var replace = document.getElementById('replace');
      var imgfile = document.getElementById('input_c')[0];
      imgshow.style.display = 'none';

      //this.flag=!this.flag;
    },

    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve.ms))
    },





    // 退出登录
    logout() {
      localStorage.setItem("state", JSON.stringify({}));
      this.$router.push("/login");
    },
    //修改密码
    changepassword() {
      var input = this.input
      console.log(input)
      var user_id = Cookies.get("userID");
      var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/
      var re = new RegExp(reg)


      if (re.test(input) != true) {
        alert('密码长度在6到20个字符！')
        return
      }

      if (user_id == 32) {
        this.$message("游客用户无法修改信息！");
        return;
      }

      var password = this.input;
      console.log("加密前，" + password);
      password = CryptoJS.SHA256(password).toString();
      console.log("加密后" + password);
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post("http://localhost:8888/users/alertPassword", {
          id: user_id,
          password: password,
        })
        .then(result => {
          console.log(result.data)
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    //更改信息
    changeinfo() {

      var user_id = Cookies.get("userID");
      var email = Cookies.get("email");
      var phone = Cookies.get("phone");
      var sex = Cookies.get("sex");
      var info = Cookies.get("info");

      if (user_id == 32) {
        this.$message("游客用户无法修改信息！");
        return;
      }


      if (this.sex != null && this.sex != "") {
        sex = this.sex;
      }
      if (this.email != null && this.email != "") {
        email = this.email;
      }
      if (this.phone != null && this.phone != "") {
        phone = this.phone;
      }
      if (this.info != null && this.info != "") {
        info = this.info;
      }
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post("http://localhost:8888/users/alertInfo", {
          id: user_id,
          email: email,
          phone: phone,
          sex: sex,
          info: info,
        })
        .then((result) => console.log(result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
      console.log(this.userInfo);
    },

    resetinfo() {
      var user_id = Cookies.get("userID");

      if (user_id == 32) {
        this.$message("游客用户无法修改信息！");
        return;
      }

      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post("http://localhost:8888/users/resetInfo", {
          id: user_id,
          email: '',
          phone: '',
          sex: '',
          info: '',
        })
        .then((result) => console.log(result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
      console.log(this.userInfo);
    },


    goEnd() {
      window.location.href = "http://localhost:8080/systemLogin"
    },

    getInfo() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id = Cookies.get("userID");
      console.log("?????????????" + user_id);
      axios
        .post("http://localhost:8888/users/user/info", {
          user_id: user_id,
        })
        .then((result) => {
          console.log(result);
          this.userInfo = result.data;
          console.log(this.userInfo);
          this.sex = this.userInfo.sex;
          this.email = this.userInfo.email;
          this.phone = this.userInfo.phone;
          this.info = this.userInfo.info;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    loadBook() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id = Cookies.get("userID");
      console.log("???" + user_id);
      axios
        .post("http://localhost:8888/rating/get/user/ratingAndBook", {
          user_id: user_id,
        })
        .then((result) => (this.ratings = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    deleteRating(ratingId) {
      console.log("!!!!!!" + ratingId);
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post("http://localhost:8888/rating/remove", {
          rating_id: ratingId,
        })
        .then((result) => console.log("返回" + result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },



    loadComment() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id = Cookies.get("userID");
      axios
        .post("http://localhost:8888/comment/my", { user_id: user_id })
        .then((result) => (this.comments = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },

    deleteComment(commentId) {
      console.log("!!!!!!" + commentId);
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post("http://localhost:8888/comment/my/delete", {
          comment_id: commentId,
        })
        .then((result) => console.log("返回" + result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    goToBookDetail(num) {
      console.log("书ID" + num);
      /*
      this.$router.push({ name: "detail", params: { book_id: bookId } });
      */
      Cookies.set("bookId", num);
      window.location.href = "http://localhost:8080/detail";
    },
    /*
    goAdmin() {
      window.location.href = "http://localhost:8080/home";
    },
    */
  },
  created() {
    this.getInfo();
    this.loadBook();
    this.loadComment();

    this.loadAvater();
  },
};
</script>

<style lang="scss" scope>
.my-content {
  margin-top: 60px;

  .user-info,
  .edit {
    width: 80%;
    height: 200%;
    margin: 0.9375rem auto;

    .item {
      cursor: pointer;
      margin: 0 0.625rem;
    }
  }
}

input {
  outline-style: none;
  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 13px 14px;
  width: 620px;
  font-size: 14px;
  font-family: "Microsoft soft";
}













.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
