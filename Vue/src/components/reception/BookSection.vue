<template>
  <div>
    <!-- 头部 -->
    <div>
      <top></top>
    </div>
    <!-- 主体 -->
    <el-main class="home-main-body">
      <keep-alive>
        <div style="margin-top: 80px;">
        <el-card class="user-info" v-for="(content, index) in contents" :key="index" >
              <a :href="content.chapterContent">试读</a>

        </el-card>
        <!--
        <div class="read-wrapper">
          <div id="reader" class="reader"></div>
          <a id="prev" href="#prev" @click="prevPage()" class="arrow" style="width: 11%;">‹</a>
          <a id="next" href="#next" @click="nextPage()" class="arrow" style="width: 11%;">›</a>
        </div>
        -->
      </div>
      </keep-alive>
    </el-main>
    <bottom></bottom>
  </div>
</template>


<script>
import Top from "@/components/reception/Top.vue";
import Bottom from "@/components/reception/Bottom.vue";
//import ePub from 'epubjs';
import Cookies from "js-cookie";
import axios from 'axios';
//import Epub, { Book } from "epubjs"
//import pdf from 'vue-pdf'


export default {
  name: 'BookSection',
  components: { Top, Bottom },
  data() {
    return {
      contents: Array,
    }
  },
  methods: {
    getBook() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var book_id = Cookies.get("epub");
      console.log("书唯一id" + book_id);
      axios
        .post("http://localhost:8888/content/read", { book_id: book_id })
        .then((result) => {
          console.log(result)
          this.contents = result.data;
          console.log(this.contents)
          //this.downloadFile(result.data.chapter)
          //this.openBook(result.data.chapterContent)
        })
        .catch(function (error) {
          console.log(error);
        });

    },
    /*
        downloadFile(chapter) {
          
          axios
            .post('http://localhost:8888/file/epub', { chapter: chapter })
            .then(response => {
              console.log(response)
            })
            .catch(error => {
              console.error(error);
            });
            
      },
    */
    openBook(chapterContent) {
      /*
      console.log(chapterContent);
      var book = ePub(chapterContent);
      console.log(book)
      var rendition = book.renderTo("read", { width: 600, height: 400 });
      var displayed = rendition.display();
      */

      // 多页pdf的src中不能直接使用后端获取的pdf地址 否则会按页数请求多次数据
      // 需要使用下述方法的返回值作为url
      //this.url = pdf.createLoadingTask(chapterContent);



    },
  },
  created() {
    this.getBook();
  },
}
</script>




<style lang='scss' scope>
.welcome-content {
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
</style>
