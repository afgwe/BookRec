<template>
  <div class="welcome-content">
    <!-- 主体 -->
    <el-main class="home-main-body" style="margin-top: 30px;">
      <keep-alive>
        <div class="my-content">
          <!-- 信息部分 -->
          <el-card class="user-info">
            <el-descriptions class="margin-top" title="ta的信息" :column="3" border>
              <el-descriptions-item>
                <template slot="label"><i class="el-icon-user"></i>用户id</template>
                {{ userInfo.id }}
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
                <template slot="label"
                  ><i class="el-icon-mobile-phone"></i>手机号</template
                >
                {{ userInfo.phone }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label"
                  ><i class="el-icon-location-outline"></i>邮箱</template
                >
                {{ userInfo.email }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label"><i class="el-icon-tickets"></i>身份</template>
                <el-tag size="small">{{ userInfo.identity }}</el-tag>
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label"
                  ><i class="el-icon-location-outline"></i>简介</template
                >
                {{ userInfo.info }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          
          <el-card class="edit">
            <h4 class="section-title">ta推荐的书籍</h4>
            <div class="book-list">
              <div
                v-for="(rating, index) in ratings"
                :key="index"
                class="book-item"
                style="margin: 20px; border: 1px black soild"
              >
                <el-row>
                  <el-col :span="8">
                    <div class="book-details">
                      <div @click="goToBookDetail((num = rating.book.bookId))">
                        <img
                          :src="rating.book.coverImage"
                          class="book-cover"
                          style="width: 160px; height: 225px"
                        />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="8">
                    <p class="book-title">书名：{{ rating.book.bookName }}</p>
                    <p class="book-author">作者: {{ rating.book.author }}</p>
                    <span style="font-size: small; font-style: italic">评分：</span
                    ><el-rate
                      v-model="rating.rating"
                      :max="5"
                      :texts="['oops', 'disappointed', 'normal', 'good', 'great']"
                      show-text
                    />
                  </el-col>
    
                </el-row>
              </div>
            </div>
          </el-card>



          <!--我的-->
        </div>
      </keep-alive>
    </el-main>
    <!-- 底部备案 -->
  </div>
</template>

<script>
import Top from "@/components/reception/Top.vue";
import Bottom from "@/components/reception/Bottom.vue";
import axios from "axios";
import Cookies from "js-cookie";

export default {
  name: "Other",
  components: { Top, Bottom },
  data() {
    return {
      userInfo: Array,
      ratings:Array,
    };
  },
  methods: {
    async loadBook() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id = Cookies.get("other");
      console.log("???" + user_id);
      await axios
        .post("http://localhost:8888/rating/get/user/ratingAndBook", {
          user_id: user_id,
        })
        .then((result) => (this.ratings = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    async loadInfo() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id1 = Cookies.get("other");
      console.log("???" + user_id1);
      await axios
        .post("http://localhost:8888/users/user/info", {
          user_id: user_id1,
        })
        .then((response) => {
          this.userInfo = response.data;
          console.log(this.userInfo);
        })
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
  },
  created() {
    this.loadInfo();
    this.loadBook();
  },
};
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
