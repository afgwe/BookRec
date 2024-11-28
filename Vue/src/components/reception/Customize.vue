<template>
  <div class="app-container home" style="flex-shrink: 0;">
    <br><br><br>
    <div v-if="bookList.length > 0" style="display:inline-block;margin-left: 85px;">
      <div>推荐的书籍id:{{ bookList }} 
      <el-tag v-if="bookList.length > 0" class="item" @click="getRec(bookList)">获取</el-tag>
      <br><br><br><br>
      
    </div>
      <div class="book-list">
        
        <div class="book-item" style="display:inline-block;">

          <el-row>
            <el-col :span="12">
              <div>
                <img :src="book.coverImage" @click="goToBookDetail(book.bookId)" class="book-cover " />
              </div>
            </el-col>
            <el-col :span="12">
              <br><br><br>
              <div class="book-details" style="margin-left: 150px;">
                <p class="book-title">{{ book.bookName }}</p>
                <p class="book-author">作者: {{ book.author }}</p>
                <p class="book-author">评分: {{ book.rating }}</p>
                <p class="book-author">ID: {{ book.bookId }}</p>
                <p>ISBN号: {{ book.isbn }}</p>
                <p class="book-author">出版社: {{ book.publisher }}</p>
                <p class="book-author">出版日期: {{ book.publicationDate }}</p>
                
                

                <span v-if="book.isHot" class="tag hot">热门</span>
              </div>
            </el-col>
          </el-row>





         
          
          
        </div>
      </div>
    </div>


    <div v-if="bookList.length == 0">
      <p >{{ tip }}</p>
      <div class="book-list" v-for="(book, index) in hotBook" :key="index"
        style="display:inline-block;margin-left: 85px;">
        <div class="book-item">
          <img :src="book.coverImage" @click="goToBookDetail(book.bookId)" class="book-cover"
            style="height: 390px; width: 270px" />
          <div class="book-details">
            <p class="book-title">{{ book.bookName }}</p>
            <p class="book-author">作者: {{ book.author }}</p>
            <p class="book-author">评分: {{ book.rating }}</p>
            <p class="book-author">ID: {{ book.bookId }}</p>
            <p class="book-author">出版社: {{ book.publisher }}</p>
            <p class="book-author">出版日期: {{ book.publicationDate }}</p>
            <span v-if="book.isHot" class="tag hot">热门</span>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: "Customize",
  data() {
    return {
      book: Array,
      bookList: Array,
      tip: '',
      hotBook: Array,
      divideLine: 9.5,
    };
  },
  methods: {
    
    async loadGood() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var user_id = Cookies.get("userID");
      await axios
        .post("http://localhost:8888/rec/good", { user_id: user_id, divideLine: this.divideLine })
        .then((result) => (this.bookList = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
      console.log(this.getRec)
      setTimeout(this.getRec(this.bookList), 2000)
      this.tipf();
    },

    load1() {
      axios
        .get("http://localhost:8888/book/hotrec", {})
        .then((result) => (this.hotBook = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },

    tipf() {
      if (this.bookList.length == 0) {
        this.tip = '用户无图书评分记录或评分数量过少，无法推荐！系统向你推送了热门书籍！'
        this.load1()
        //this.getRec(this.bookList);
      }
    },

    async getRec(bookList) {
      var index = Math.floor(Math.random() * bookList.length);
      var radom = bookList[index];
      console.log(radom);
      console.log(bookList);
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      await axios
        .post("http://localhost:8888/book/rec/book", { book_id: radom })
        .then((result) => (this.book = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    goToBookDetail(bookId) {
      Cookies.set("bookId", bookId);
      window.location.href = "http://localhost:8080/detail";
    },
  },
  created() {
      this.loadGood();
  },
};
</script>

<style>
.book-cover {
  height: 390px;
  width: 270px
}

.book-details p {
  width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
