<template>
  <div>
    <el-breadcrumb class="top" separator="/">
      <el-breadcrumb-item>后台</el-breadcrumb-item>
      <el-breadcrumb-item>图书修改</el-breadcrumb-item>
    </el-breadcrumb>
    <div>

      <el-row>
        <el-col :span="8">

        </el-col>
        <el-col :span="8">
          <form action="http://localhost:8888/book/change/info" enctype="multipart/form-data" method="post"
            target="iframe">
            上传图片:
            <input type="file" name="file" id="input_c" style="display: none;">
            <el-button @click="input_c">上传图片</el-button>
            <br><br>
            <input name="bookId" v-model="book.bookId" style="display:none">

            书籍名称:<input name="bookName" v-model="book.bookName">
            书籍作者:<input name="author" v-model="book.author"> 
            出 版 社 :<input name="publisher" v-model="book.publisher"><br>
            出版时间:<input name="publicationDate" v-model="book.publicationDate">
            书籍isbn:<input name="isbn" v-model="book.isbn">
            图书简介:<input name="description" v-model="book.description">
            豆瓣评分:<input name="rating" v-model="book.rating">
            书籍类型：<input name="category" v-model="book.categoryId" style="display:none">
          <el-select placeholder="请选择书籍类型" v-model="book.categoryId" class="select">
            <el-option label="1软件、程序理解" value="1"></el-option>
            <el-option label="2系统理解" value="2"></el-option>
            <el-option label="3语言方面" value="3"></el-option>
            <el-option label="4数学方面" value="4"></el-option>
            <el-option label="5网络方面" value="5"></el-option>
            <el-option label="6算法方面" value="6"></el-option>
            <el-option label="7程序员修养方面" value="7"></el-option>
            <el-option label="8数据库方面" value="8"></el-option>
            <el-option label="9编程历史" value="9"></el-option>
            <el-option label="10硬件方面" value="10"></el-option>
            <el-option label="0不确定或者无分类" value="0"></el-option>
          </el-select>
          <br><br>

            <input type="submit" name="submit" value="提交" style="margin-left: 30px;">
          </form>
          <!--
          <iframe src="" name="iframe" frameborder="0" style="display:none">{{result}}</iframe>
          -->
          <br>
          <br>
          <iframe src="" name="iframe" frameborder="0"></iframe>
        </el-col>
        
        

        <el-col :span="8"  style="margin-left: 90px;">
          <img :src="book.coverImage" class="book-cover " />
          
        </el-col>
      </el-row>




    </div>

  </div>
</template>

<script>
import Cookies from "js-cookie";
import axios from "axios";
//axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

export default {
  name: 'BookAdd',
  data() {
    return {
      result: '',
      book: Array,
     
    }
  },

  methods: {
    getBook() {
      var book_id = Cookies.get('changeBookId')
      console.log(book_id)
      axios.get("http://localhost:8888/book/get/book/" + book_id, {
        book_id: book_id
      })
        .then((result) => (this.book = result.data))
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
        input_c(){
            var img=document.getElementById('input_c');
            img.click();
        }
  },
  created() {
    this.getBook();
  }
}

</script>

<style>
input {

  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 13px 14px;
  width: 275px;
  font-size: 14px;
  font-family: "Microsoft soft";
}
.select{
  border-radius: 3px;
  padding: 13px 14px;
  width: 260px;
  font-size: 14px;
  font-family: "Microsoft soft";
}
.book-cover {
  margin-left: 100px;
  margin-bottom: 250px;
  border: solid 1px;
  height: 390px;
  width: 270px
}
</style>