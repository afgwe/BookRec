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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentDao commentDao;

    @Autowired
    BookDao bookDao;

    //点赞评论
    @PostMapping("/add/rating")
    public Integer addCommentClickRating(Integer comment_id){
        Comment comment=commentDao.selectByCommentId(comment_id);
        comment.setRating(comment.getRating()+1);
        return commentDao.updateRating(comment);
    }

    //通过boookid查评论
    @GetMapping("/{book_id}")
    public List<Comment> getCommentById(@PathVariable Integer book_id) {
        return commentDao.selectById(book_id);
    }

    //查用户在某本书的评论
    @GetMapping("/user_id/{user_id}")
    public List<Comment> getCommentByUserId(@PathVariable Integer user_id, Integer book_id) {
        List<Comment> comments = commentDao.selectById(book_id);
        return comments.stream()
                .filter(comment -> comment.getUserId() == user_id)
                .collect(Collectors.toList());
    }

    @PostMapping("/my")
    public List<Comment> getCommentByUserId(Integer user_id){
        List<Comment> list=commentDao.selectCommentAndBook(user_id);
        return list.stream()
                .filter(comment -> comment.getUserId()==user_id)
                .collect(Collectors.toList());
    }

    //增加评论
    @PostMapping("/book_id/addComment")
    public Integer addComment(Integer book_id, Integer user_id,
                              String content, Integer parent_comment_id, Double rating) {

        Comment comment = new Comment();
        comment.setCommentId(0);
        comment.setBookId(book_id);
        comment.setUserId(user_id);
        comment.setContent(content);

        comment.setParentCommentId(parent_comment_id);
        comment.setRating(0);

        Timestamp t = new Timestamp(new Date().getTime());
        comment.setCreateTime(t);
        List<Comment> comments = commentDao.selectByUserId(user_id);
        return commentDao.addComment(comment);

    }

    //查用户在所有书下的评论
    @PostMapping("/user/my")
    public List<Comment> getMyComment(Integer user_id) {
        /*
        List<Comment> comments = commentDao.selectByUserId(user_id);
        //List<Book> books = bookDao.selectBook();
        comments=comments.stream()
                .filter(comment -> comment.getRating()>0)
                .collect(Collectors.toList());

        return comments;
         */
        List<Comment> comments = commentDao.selectCommentAndBook(user_id);
        return comments.stream()
                .filter(comment -> comment.getRating() > 0)
                .collect(Collectors.toList());

    }

    //所有用户的最新评论
    @GetMapping("/user/new")
    public List<Comment> getAllCommentAndBook() {
        /*
        List<Comment> comments = commentDao.selectByUserId(user_id);
        //List<Book> books = bookDao.selectBook();
        comments=comments.stream()
                .filter(comment -> comment.getRating()>0)
                .collect(Collectors.toList());

        return comments;
         */
        List<Comment> comments = commentDao.selectAllCommentAndBook();
        return comments.stream()
                .filter(comment -> comment.getRating() > 0)
                .collect(Collectors.toList());

    }

    //删除评论
    @PostMapping("my/delete")
    public Integer deleteComment(Integer comment_id) {
        return commentDao.deleteComment(comment_id);
    }
}


