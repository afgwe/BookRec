<template>
  <div class="welcome-content1">
    <!-- 头部 -->
    <div>
      <top></top>
    </div>
    <!-- 主体 -->
    <el-main class="home-main-body1">
      <keep-alive>
        <div>
          <div>
            <div class="search">
              <el-input v-model.trim="input" :changeValue="changeValue" placeholder="请输入书名" />
            </div>
            <div v-for="(book, index) in books" :key="index" class="card" @click="goToBookDetail(book.bookId)">
              <el-card v-if="input != ''" class="content1">
                <el-row>
                  <el-col :span="12">
                    <img :src="book.coverImage" :alt="book.title" class="book-cover1" />
                  </el-col>
                  <el-col :span="12">
                    <p >{{ book.bookName }}</p>
                    <p>
                      作者: <span style="font-style: italic">{{ book.author }}</span>
                    </p>
                  </el-col>
                </el-row>
              </el-card>
            </div>
          </div>
        </div>
      </keep-alive>
    </el-main>
    <!-- 底部备案 -->
    <bottom></bottom>
  </div>





</template>

<script>
import Cookies from "js-cookie";
import { mapMutations } from "vuex";
import axios from "axios";
import Top from '@/components/reception/Top.vue'
import Bottom from '@/components/reception/Bottom.vue'

export default {
  name: "Search",
  components: { Top, Bottom },
  data() {
    return {
      input: "",
      books: Array,
      bookName: "",
    };
  },
  computed: {
    changeValue() {
      if (this.input != "") {
        this.getValue(this.input);
        return this.input;
      }
    },
  },

  methods: {
    async getValue(input) {
      console.log(input);
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      await axios
        .post("http://localhost:8888/book/search", {
          bookName: input,
        })
        .then(result => {
          this.books = result.data;

        
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    goToBookDetail(bookId) {
      console.log("书ID" + bookId);
      /*
      this.$router.push({ name: "detail", params: { book_id: bookId } });
      */
      Cookies.set("bookId", bookId);
      window.location.href = "http://localhost:8080/detail";
    },
  },
};
</script>

<style lang='scss' scope>
.card {
  width: 240px;
}

.welcome-content1 {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-position: center center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  min-height: 100%;
  background-size: cover;
  background-color: #dfe6e9;
}

.home-main-body1 {
  margin: 0 auto;
  margin-top: 80px;
  width: 1000px;
}

.content1 {
  margin: 0 auto;
  width: 960px;
}
.book-cover1{
  width: 80px;
  height: 112px;
}

</style>

