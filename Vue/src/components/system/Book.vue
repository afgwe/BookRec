<template>
  <div class="comment-content">
    <el-breadcrumb class="top" separator="/">
      <el-breadcrumb-item>后台</el-breadcrumb-item>
      <el-breadcrumb-item>图书管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 表格 -->
    <el-table border ref="table" :data="books" style="width: 100%" fit>
      <el-table-column property="bookId" label="#" width="50px" align="center" prop="bookId">
      </el-table-column>

      <el-table-column type="index" label="图片" width="200px" align="center" prop="coverImage">
        <template slot-scope="scope">
          <img :src="scope.row.coverImage" style="width: 50%" />
        </template>
      </el-table-column>

      <el-table-column property="bookName" label="图书名" align="center" prop="bookName">
      </el-table-column>

      <el-table-column property="author" label="作者" align="center" prop="author">
      </el-table-column>

      <el-table-column property="publisher" label="出版社" align="center" prop="publisher">
      </el-table-column>

      <el-table-column property="publicationDate" label="出版时间" align="center" prop="publicationDate">
      </el-table-column>
      <el-table-column property="description" label="概况" width="240px" align="center" prop="description">
      </el-table-column>
      <el-table-column property="isbn" label="ISBN" align="center" prop="isbn">
      </el-table-column>

      <el-table-column property="rating" label="分数" align="center" prop="rating">
      </el-table-column>

      <el-table-column property="caozuo" label="操作" align="center">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="danger" @click="handleDelete((book_id = scope.row.bookId))">删 除</el-button> -->
          <el-button plain @click="handleDelete((book_id = scope.row.bookId))">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column property="caozuo" label="更改" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleChange((book_id = scope.row.bookId))">修 改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>  <br>
    <div>
      <p><span style="margin-left: 550px;"></span>已经到末尾了！</p>
    </div>
    
  </div>
</template>

<script>

import Cookies from "js-cookie";
import axios from "axios";
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
export default {
  name: "User",
  data() {
    return {
      books: [],
      
    };
  },
  methods: {
    getUsersByPage() {
      axios
        .get("http://localhost:8888/book/all", {})
        .then(result=> {
          this.books = result.data
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
          
        });
    },
    handleDelete(book_id) {
      console.log(book_id);
      axios
        .post("http://localhost:8888/book/delete", { book_id: book_id })
        .then((response) => {
          console.log(response.data);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        });
    },
    handleChange(bookId) {
      //console.log(book_id);
      Cookies.set('changeBookId',bookId)
      window.location.href='http://localhost:8080/home/bookChange'
    },
  },
  created() {
    this.getUsersByPage();
  },
};
</script>


<styl>
  .form {
    width: 600px;
    height: 44px;
    border: 2px solid #4569ff;
    border-radius: 10px;
    overflow: hidden;
  }

  .text {
    width: 468px;
    height: 44px;
    float: left;
    border: none;
    padding: 0 16px;
  }

  .text:focus {
    outline: none;
  }

  .btn {
    width: 100px;
    height: 44px;
    float: left;
    border: none;
    background-color: #4e6ef2;
    color: #fff;
    cursor: pointer;
  }

  .btn:hover {
    background-color: #4662d9;
  }
</style>
