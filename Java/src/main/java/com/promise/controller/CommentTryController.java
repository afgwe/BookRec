package com.promise.controller;

import com.promise.dao.BookDao;
import com.promise.dao.CommentDao;
import com.promise.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/commentTry")
public class CommentTryController {

    @Autowired
    CommentDao commentDao;

    @Autowired
    BookDao bookDao;


    //通过bookid查评论
    @GetMapping("/{book_id}")
    public List<Comment> getCommentById(@PathVariable Integer book_id) {
        List<Comment> comments=commentDao.selectById(book_id);

        comments = comments.stream()
                .filter(comment -> comment.getParentCommentId()==0)
                .collect(Collectors.toList());
        System.out.println("!!!!!!!!!"+comments);
        return comments;
    }

    //嵌套评论的实现
    @PostMapping("/try")
    public List<Comment> getCommentByCommentId(Integer comment_id,Integer book_id) {
        List<Comment> comments=commentDao.selectById(book_id);
        comments= comments.stream()
                .filter(comment -> comment.getParentCommentId()==comment_id)
                .collect(Collectors.toList());
        System.out.println("!!!!!!!!!"+comments);
        return comments;
    }

}


