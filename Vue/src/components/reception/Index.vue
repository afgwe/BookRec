<template>
  <div class="app-container home">



    <h4 class="section-title" >热门书籍</h4>
    <div class="book-list" style="padding-left: 60px;">
      <div v-for="(book, index) in hotBooks" :key="index" class="book-item" @click="goToBookDetail(book.bookId)">
        <img :src="book.coverImage" class="book-cover" />
        <div class="book-details">
          <p class="book-title">{{ book.bookName }}</p>
          <p class="book-author">作者: {{ book.author }}</p>
          <p class="book-author">评分: {{ book.rating }}</p>
          <p class="book-author">出版社: {{ book.publisher }}</p>
          <p class="book-author">出版日期: {{ book.publicationDate }}</p>
          <p class="book-author">点击次数: {{ book.clickNum }}</p>
          <span v-if="book.isHot" class="tag hot">热门</span>
        </div>
      </div>
    </div>

    <h4 class="section-title">全部书籍
      <el-switch v-model="flag1" @click="loadall" class="ml-2"
        style="--el-switch-on-color: #000000; --el-switch-off-color: #000000" />
    </h4>

    <div class="book-list" style="padding-left: 60px;" v-if="flag1">
      <div v-for="(book, index) in books" :key="index" class="book-item" @click="goToBookDetail(book.bookId)">
        <img :src="book.coverImage" :alt="book.title" class="book-cover" />
        <div class="book-details">
          <p class="book-title">{{ book.bookName }}</p>
          <p class="book-author">作者: {{ book.author }}</p>
          <p class="book-author">评分: {{ book.rating }}</p>
          <p class="book-author">出版社: {{ book.publisher }}</p>
          <p class="book-author">出版日期: {{ book.publicationDate }}</p>
          <p class="book-author">点击次数: {{ book.clickNum }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Cookies from "js-cookie";
//axios.defaults.withCredentials = true

export default {
  name: "BlogIndex",
  data() {
    return {
      hotBooks: Array,
      books: Array,
      src: "",
      flag1:true,
    };
  },
  methods: {
    loadall(){
      this.flag1=!this.flag1
    },
    load1() {
      axios
        .get("http://localhost:8888/book/hot", {})
        .then((result) => (this.hotBooks = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    load2() {
      axios
        .get("http://localhost:8888/book/all", {})
        .then((result) => (this.books = result.data))
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
  created() {
    this.load1();
    this.load2();
  },
};
</script>

<style scoped>
.index-content {
  margin-top: 60px;
  display: flex;
  justify-content: space-evenly;
}

.app-container {
  padding: 20px;
}

/* 标题样式 */
.section-title {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
  border-bottom: 2px solid #ccc;
  /* 添加底部边框 */
  padding-bottom: 5px;
  /* 调整底部边框与标题之间的间距 */
}

.book-list {
  display: flex;
  flex-wrap: wrap;
}

.book-item {
  width: 200px;
  margin: 10px;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  transition: transform 0.3s ease;
  cursor: pointer;
  /* 将鼠标光标设置为指示链接的手型 */
}

.book-item:hover {
  transform: translateY(-5px);
}

.book-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

.book-details {
  padding: 10px;
}

.book-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.book-author {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.tag {
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 12px;
  margin-left: 5px;
}

.hot {
  background-color: #ff5722;
  color: #fff;
}

.recommended {
  background-color: #4caf50;
  color: #fff;
}















.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
