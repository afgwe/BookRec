<template>
  <div class="welcome-content">
    <!-- 头部 -->
    <div>
      <top></top>
    </div>
    <!-- 主体 -->
    <el-main class="home-main-body">
      <keep-alive>
        <div class="book-detail">
          <div class="detail-section">
            <el-row>
              <el-col :span="12">
                <p>{{ bookDetail.bookName }}</p>
                <img :src="bookDetail.coverImage" alt="封面图" width="160px" height="225px" />
                <p>本书已被点击:{{ bookDetail.clickNum }}次！</p>
              </el-col>
              <el-col :span="12">
                <p>作者: {{ bookDetail.author }}</p>
                <p>出版社: {{ bookDetail.publisher }}</p>
                <p>出版日期: {{ bookDetail.publicationDate }}</p>
                <p>ISBN号: {{ bookDetail.isbn }}</p>
                <p>图书简介: {{ bookDetail.description }}</p>
                <p>评分: {{ bookDetail.rating }}</p>
              </el-col>
            </el-row>
          </div>

          <!-- 评分 -->
          <div class="rating-section detail-section">
            <div class="form">
              <h3>我的评分</h3>
              <el-rate v-model="value" :max="5" style="margin-top: 20px; margin-bottom: 20px; margin-right: 20px"
                show-text :texts="['无感', '略可', '一般', '好看', '大爱']" />
              <el-tag type="" @click="changeRating(value)"> 提交评分 </el-tag>
            </div>
          </div>

          <!--目录-->
          <div class="rating-section detail-section">
            <h3>试读</h3>
            <a :href="content.chapterContent" style="margin-left: 10px;">{{ tip }}</a><br>
            <el-card v-for="(content, index) in bookContents" :key="index">
              <a :href='"http://localhost:8888/" + content.chapterContent' style="margin-left: 10px;">{{ content.chapter
                }}</a>
            </el-card>

          </div>











          <!--评论-->
          <div class="comment-list detail-section">
            <h3>评论列表</h3>
            <p>这里是评论区！</p>
            <div class="comment-content" style="margin-bottom: 40px;">
              评价图书：<input v-model.trim="content" style="width: 1000px; height: 50px" />
              <el-tag type="primary" @click="addComment('content')">提交评论</el-tag>
            </div>


            <div class="comment-items">
              <div v-for="comment in comments" :key="comment.commentId" class="comment-item">
                <div class="comment-content">
                  <p>{{ comment.commentId }}</p>
                  <p>
                    ID:<span class="comment-user">{{ comment.userId }}</span>
                    <span v-if="comment.parentCommentId == 0"> 写下了一条评论：</span>
                    （此评论的ID：<span class="comment-user"> {{ comment.commentId }}）</span>
                    <span style="margin-left:800px;">
                      点赞量：{{ comment.rating }}
                      <el-tag @click="clickRating(comment_id = comment.commentId)">点赞评论</el-tag>
                    </span>
                  </p>
                  <p>{{ comment.content }}</p>
                  <p class="comment-time">评论时间: {{ comment.createTime }}</p>





                  <el-tag type="primary" @click="load4(num = comment.commentId)">展开评论</el-tag>

                  <div>
                    <p>评论详情：{{ show }}</p>
                    <div v-for="comment in commentTry" :key="comment.commentId" class="comment-item">
                      <el-card style="margin-left: 25px;" >
                       
                      <p>
                        ID:<span class=" comment-user">{{ comment.userId }}</span>
                        <span v-if="comment.parentCommentId == 0"> 写下了一条评论：</span>
                        （此评论的ID：<span class="comment-user"> {{ comment.commentId }}）</span>
                        <span style="margin-left:800px;">
                          点赞量：{{ comment.rating }}
                          <el-tag @click="clickRating(comment_id = comment.commentId)">点赞评论</el-tag>
                        </span>
                      </p>
                      
                      <p>{{ comment.content }}</p>
                      <p class="comment-time">评论时间: {{ comment.createTime }}</p>

                      <div class="form">
                        回复ta：<input v-model.trim="content1" style="width: 1000px; height: 50px" />

                        <el-tag type="primary" @click="replyComment(content1, (num = comment.commentId))">
                          提交回复
                        </el-tag>
                      </div>
                      </el-card>
                    </div>
                    </div>








                    <div class="rating-section detail-section">
                      <div class="form">
                        回复ta：<input v-model.trim="content1" style="width: 1000px; height: 50px" />

                        <el-tag type="primary" @click="replyComment(content1, (num = comment.commentId))">
                          提交回复
                        </el-tag>
                      </div>
                    </div>
                  </div>
                </div>
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
import Top from "@/components/reception/Top.vue";
import Bottom from "@/components/reception/Bottom.vue";
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: "Detail",
  data() {
    return {
      bookDetail: Array,
      commentList: Array,
      comments: Array,
      commentTry: Array,
      rating: Array,
      content: "",
      content1: "",
      value: 0,
      content2: "",
      comment: {
        parentCommentId: 0,
      },
      otherValue: 0,
      bookContents: Array,
      tip: '未收录本书资源！',
      show:'点击展开评论按钮查看评论！',
    };
  },
  components: { Top, Bottom },
  methods: {
    async load1() {
      var book_id = Cookies.get("bookId");
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      //获得图书详情
      await axios
        .post("http://localhost:8888/book/rec/book", { book_id: book_id })
        .then((result) => (this.bookDetail = result.data))
        .catch(function (error) {
          console.log(error);
        });
      //更新点击量
      await axios
        .post("http://localhost:8888/book/click", { book_id: book_id })
        .then((result) => console.log(result.data))
        .catch(function (error) {
          console.log(error);
        });
    },

    load2() {
      var book_id = Cookies.get("bookId");
      var url = "http://localhost:8888/commentTry/" + book_id;
      //获得一本书的所有0级评论
      axios
        .get(url)
        .then(response => {
          this.comments = response.data
          console.log(this.comments)
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    load4(num) {
      var book_id = Cookies.get("bookId");
      var comment_id = num;
      console.log('要回复的评论id'+comment_id);
      var url = "http://localhost:8888/commentTry/try";
      //获得一本书的所有次级评论
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post(url, {
          book_id: book_id,
          comment_id: comment_id,
        })
        .then(response => {
          if(response.data!=null||response.data!=undefined||response.data!=null){
            this.commentTry = response.data
            console.log( this.commentTry)
          }
          if(response.data==null||response.data==''){
            this.show='还没有人回复此评论！'
          }
          
        
        }
        )
        .catch(function (error) {
          console.log(error);
        });
    },


    addComment(content) {
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      var content = this.content;
      var parent_comment_id = 0;
      var rating = this.value;
      var url = "http://localhost:8888/comment/book_id/addComment";

      if (content == '' || content == undefined) {
        alert('评论内容不能为空！');
        return;
      };

      //增加评论
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      axios
        .post(url, {
          book_id: book_id,
          user_id: user_id,
          content: content,
          parent_comment_id: parent_comment_id,
          rating: rating,
        })
        .then((response) => console.log(response.data))
        .catch(function (error) {
          console.log(error);
        });
    },

    replyComment(content1, num) {
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      var content = this.content1;
      var parent_comment_id = this.num;
      var rating = 0;
      var url = "http://localhost:8888/comment/book_id/addComment";
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

      if (content == '' || content == undefined) {
        console.log('++++' + content.trim)
        alert('评论内容不能为空！');
        return;
      };

      //回复评论
      axios
        .post(url, {
          book_id: book_id,
          user_id: user_id,
          content: content,
          parent_comment_id: parent_comment_id,
          rating: rating,
        })
        .then((response) => console.log(response.data))
        .catch(function (error) {
          console.log("返回：" + error);
        });
    },
    load3() {
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      //获取评分
      axios
        .post("http://localhost:8888/rating/get/user_id/book_id", {
          user_id: user_id,
          book_id: book_id,
        })
        .then((response) => {
          this.value = response.data;
        })
        .catch(function (error) {
          console.log("返回：" + error);
        });
    },
    changeRating(value) {
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      var rating = value;
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      //获取评分
      axios
        .post("http://localhost:8888/rating/alert", {
          user_id: user_id,
          book_id: book_id,
          rating: rating,
        })
        .then((response) => {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log("返回：" + error);
        });
    },
    async clickRating(comment_id) {
      console.log(comment_id);
      await axios
        .post("http://localhost:8888/comment/add/rating", {
          comment_id: comment_id,
        })
        .then((response) => {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log("返回：" + error);
        });
    },
    getBook() {
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      var book_id = Cookies.get("bookId");
      console.log("书唯一id" + book_id);
      axios
        .post("http://localhost:8888/content/read", { book_id: book_id })
        .then((result) => {
          console.log(result)
          if (result.data[0].chapterContent != null) {
            this.bookContents = result.data;
            this.tip = '点击试读！'
          }
          console.log(this.contents)
          //this.downloadFile(result.data.chapter)
          //this.openBook(result.data.chapterContent)
        })
        .catch(function (error) {
          console.log(error);
        });

    },
  },
  created() {
    this.load1();
    this.load2();
    this.load3();
    this.getBook();
  },
};
</script>

<style lang="scss" scope>
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

.book-detail {
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.detail-section {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.book-detail h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.book-detail p {
  font-size: 16px;
  margin-bottom: 5px;
}

.book-detail h3 {
  font-size: 20px;
  margin-top: 20px;
}

.comment-items {
  list-style-type: none;
  padding: 0;
}

.comment-item {
  margin-bottom: 20px;
}

.comment-content {
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comment-user {
  color: #007bff;
  font-weight: bold;
}

.sub-comment-list {
  padding-left: 20px;
}

.reply-form {
  margin-top: 10px;
}

.comment-time {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
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
</style>