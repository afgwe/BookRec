<template>
    <div>
        <el-breadcrumb class="top" separator="/">
            <el-breadcrumb-item>后台</el-breadcrumb-item>
            <el-breadcrumb-item>图书上传</el-breadcrumb-item>
        </el-breadcrumb>
        <form action="http://localhost:8888/book/add/info" enctype="multipart/form-data" method="post" target="iframe">
            上传图片:<input type="file" name="file" id="input_c" style="display: none;">
            <el-button @click="input_c">上传图片</el-button>
            <br><br>
            书籍名称:<input name="bookName">
            书籍作者:<input name="author"> <br><br>
            出 版 社 :<input name="publisher">
            出版时间:<input name="publicationDate"><br><br>
            书籍isbn:<input name="isbn">
            图书简介:<input name="description"><br><br>
            豆瓣评分:<input name="rating">
            书籍类型：<input name="category" v-model="selectedValue" style="display:none">
            <el-select placeholder="请选择书籍类型" v-model="selectedValue">
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
    </div>
</template>

<script>
//import { shadow } from 'pdfjs-dist';

export default {
    name: 'BookAdd',
    data() {
        return {
            result: '',
            selectedValue:'1软件、程序理解'
        }
    },
    methods: {
        input_c() {
            var img = document.getElementById('input_c');
            img.click();

        },
    }

}

</script>

<style>
input {
    outline-style: none;
    border: 1px solid #ccc;
    border-radius: 3px;
    padding: 13px 14px;
    width: 320px;
    font-size: 14px;
    font-family: "Microsoft soft";
}
</style>