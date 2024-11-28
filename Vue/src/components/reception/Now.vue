<template>
  <div class="welcome-content">
    <!-- 头部 -->
    <div>
      <top></top>
    </div>
    <!-- 主体 -->
    <el-main class="home-main-body">
      <el-card class="edit">
        <h4 class="section-title">全部推荐</h4>
        <div class="book-list">
          <el-card
            v-for="(rating, index) in rating"
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
                <span
                  style="font-size: small; font-style: italic"
                  v-if="rating.rating > 0"
                  >评分：</span
                ><el-rate v-model="rating.rating" :max="5" show-text />
                <p class="book-author" @click="otherSpace((id = rating.userId))">
                  推荐用户ID: {{ rating.userId }}
                </p>
              </el-col>
            </el-row>
          </el-card>
        </div>
      </el-card>
    </el-main>
    <!-- 底部备案 -->
    <div>
      <bottom></bottom>
    </div>
  </div>
</template>

<script>
import Top from "@/components/reception/Top.vue";
import Bottom from "@/components/reception/Bottom.vue";
import axios from "axios";
import Cookies from "js-cookie";
export default {
  name: "New",
  data() {
    return {
      rating: Array,
    };
  },
  components: {
    components: { Top, Bottom },
  },
  methods: {
    loadBook() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .get("http://localhost:8888/rating/get/ratingAndBook")
        .then((result) => (this.rating = result.data))
        .catch(function (error) {
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
    otherSpace(id) {
      Cookies.set("other", id);
      window.location.href = "http://localhost:8080/welcome/other";
    },
  },
  created() {
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
