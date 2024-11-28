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
            <p style="font-size: 12px;">{{ bookDetail.bookId }}</p>
            <el-row>
              
              <el-col :span="12">
                <h4>{{ bookDetail.bookName }}</h4>
                <img :src="bookDetail.coverImage" alt="封面图" width="200px" height="280px" />
                <p>本书已被点击:{{ bookDetail.clickNum }}次！</p>
                <p>其中你已点击:{{ click }}次！</p>
              </el-col>
              <el-col :span="12">
                <br><br><br>
                <span>作者: {{ bookDetail.author }}</span><br><br>
                <span>出版社: {{ bookDetail.publisher }}</span><br><br>
                <span>出版日期: {{ bookDetail.publicationDate }}</span><br><br>
                <span>ISBN号: {{ bookDetail.isbn }}</span><br><br>
                <span>图书简介: {{ bookDetail.description }}</span><br><br>
                <span>评分: {{ bookDetail.rating }}</span><br><br>
              </el-col><br><br>
            </el-row>
          </div>

          <!-- 评分 -->
          <div class="rating-section detail-section">
            <div class="form">
              <h3>我的评分</h3>
              <el-rate v-model="value" :max="5" style="margin-top: 20px; margin-bottom: 20px; margin-right: 20px;"
                show-text :texts="['无感', '略可', '一般', '好看', '大爱']" />
                <div v-if="value.code!=500">
                  <span  style="font-size: 14px;font-style: italic;" >当前书籍评分为{{value}}分</span>
                  <br><br>
                </div>
                <div v-if="value.code==500">
                  <span  style="font-size: 14px;font-style: italic;" >(当前书籍未进行评分！)</span>
                  <br><br>
                </div>
                
              <el-tag type="" @click="changeRating(value)"> 提交评分 </el-tag>
            </div>
          </div>

          <!--目录-->
          <div class="rating-section detail-section">
            <h3>试读</h3>
            <a :href="content.chapterContent" style="margin-left: 10px;">{{ tip }}</a><br><br>
            <a :href="content.chapterContent" style="margin-left: 10px;display:block">{{ tip2 }}</a>
            <div v-if="bookContents.chapterId!=''">
              <el-card v-for="(content, index) in bookContents" :key="index" style="margin:7px;border-radius: 5px;width:660px;display:block">
                <!--
                <a :href='"http://localhost:8888/" + content.chapterContent' 
                style="margin-left: 10px;">{{ content.chapter}}</a>
                -->
                <a @click="clickWWW(www = content.chapterContent, (mark = content.mark))" style="margin-left: 10px;">{{
                  content.chapter }}</a>
              </el-card>
            </div>
            
            <p></p>

          </div>

          <!--评论-->
          <div class="comment-list detail-section">
            <h3>评论列表</h3>
            <p>这里是评论区！</p>
            <div  style="margin-bottom: 40px;">
              评价图书：<input v-model.trim="content" style="width: 600px; height: 30px" />&nbsp;&nbsp;&nbsp;&nbsp;
              <el-tag type="primary" @click="addComment('content')">提交评论</el-tag>
            </div>
            <div class="comment-items">
              <div v-for="comment in comments" :key="comment.commentId" class="comment-item">
                <div class="comment-content" style="">
                  <p>{{ comment.commentId }}</p>
                  <!--<img :src="avatar" alt="" width="60px" height="60px" style="border-radius: 60px;">-->


                  <p>
                    ID:<span class="comment-user">{{ comment.userId }}</span>
                    <span v-if="comment.parentCommentId == 0"> 写下了一条评论：
                    </span>
                    <span v-if="comment.parentCommentId > 0">
                      回复 用户的ID：<span class="comment-user"> {{ comment.parentCommentId }}</span>
                    </span>
                    
                  </p>
                  <p>{{ comment.content }}</p>
                  <p class="comment-time">评论时间: {{ comment.createTime }}</p>
                  <div class="rating-section detail-section">
                    
                    <div class="form"  style="display:flex;width:500px">
                      回复：&nbsp;<input v-model.trim="content1" style="width: 450px; height: 7px" />
                      &nbsp;&nbsp;&nbsp;&nbsp;
                      <el-tag type="primary" @click="replyComment(content1, (num = comment.userId))">
                        
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
      tip2: '非常抱歉！',
      show: false,
      avatar: '',
      click:'0',
    };
  },
  components: { Top, Bottom },
  methods: {
    clickWWW(www, mark) {
      console.log('mark:' + mark)
      Cookies.set('www', www);
      if (mark == 1) {
        //window.location.href='http://localhost:8080/read'


        var md = Cookies.get('www');
        console.log(md);
        var str = 'src/main/resources/' + md;


        axios
          .post("http://localhost:8888/content/md", { str: str })
          .then(result => {
            window.location.href='http://localhost:8888/static/md/convert/'+result.data+'.html'
          })
          .catch(function (error) {
            // 处理错误情况
            console.log(error);
          });








      }
      if (mark == 2) {
        window.location.href = 'http://localhost:8888/' + Cookies.get('www')
      }

    },

    loadAvater() {
      var user_id = Cookies.get('userID')
      axios
        .post("http://localhost:8888/users/checkAvatar", {
          user_id: user_id
        })
        .then(result => {
          if (result.data == 1) {
            this.avatar = 'http://localhost:8888/static/avatar/' + user_id + '.jpg'
          } else {
            this.avatar = 'http://localhost:8888/static/avatar/avatar.jpg'
          }


        })
        .catch(function (error) {
          console.log(error);
        });
    },





    async load1() {
      var book_id = Cookies.get("bookId");
      var user_id = Cookies.get('userID')
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
        .post("http://localhost:8888/book/click", {
         book_id: book_id,
         user_id:user_id,
        })
        .then((result) => console.log(result.data))
        .catch(function (error) {
          console.log(error);
        });

        //this.load5();
    },

    load2() {
      var book_id = Cookies.get("bookId");
      var url = "http://localhost:8888/comment/" + book_id;
      //获得一本书的所有评论
      axios
        .get(url)
        .then((response) => (this.comments = response.data))
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

      if (user_id == 32) {
        this.$message("游客用户无法使用此功能！");
        return;
      }

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

      if (user_id == 32) {
        this.$message("游客用户无法使用此功能！");
        return;
      }

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
      console.log(value)
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      if(value!=1&&value!=2&&value!=3&&value!=4&&value!=5){
        this.$message("书籍评分应在1-5分！");
        return
      }
      var rating = value;

      if (user_id == 32) {
        this.$message("游客用户无法使用此功能！");
        return;
      }

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
            this.tip2=''
            //this.loaduserclick();
          }
          console.log(this.contents)
          //this.downloadFile(result.data.chapter)
          //this.openBook(result.data.chapterContent)
        })
        .catch(function (error) {
          console.log(error);
        });

    },

     loaduserclick(){
      var user_id = Cookies.get("userID");
      var book_id = Cookies.get("bookId");
      //var rating = value;
      axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      //获取评分
       axios
        .post("http://localhost:8888/book/clickaaa", {
          user_id: user_id,
          book_id: book_id,
        })
        .then((response) => {
          if(response.data.code!=500){
            this.click=response.data
          }
          
        })
        .catch(function (error) {
          console.log("返回：" + error);
        });


    },
  },
  created() {
    this.load1();
    this.load2();
    this.load3();
    this.getBook();
    this.loaduserclick();
    //this.loadAvater();
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
