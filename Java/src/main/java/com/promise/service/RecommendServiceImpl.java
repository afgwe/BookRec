package com.promise.service;

import com.promise.dao.BookDao;
import com.promise.dao.CommentDao;
import com.promise.dao.RatingDao;
import com.promise.pojo.Book;
import com.promise.pojo.Comment;
import com.promise.pojo.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl{

    @Autowired
    BookDao bookDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    RatingDao ratingDao;

    public List<Double> initDoubleList(@PathVariable Integer user_id) {
        //首先得到所有书籍
        List<Book> books = bookDao.selectBook();
        //把书里的的id和用户评论的id做对比，用户有书就获取评分，没有就赋值0
        List<Double> list = new ArrayList<>();

        List<Rating> ratings = ratingDao.selectByUserId(user_id);
        List<Rating> userRating = ratings.stream()
                .filter(rating -> rating.getRating() > 0)
                .collect(Collectors.toList());

        //实例化
        for (Book book : books) {
            book.setRating(0.0);
            for (Rating rating : userRating) {
                /*
                if (book.getBookId() == comment.getBookId()) {
                    book.setRating(comment.getRating());
                }
                 */
                if (rating.getBookId()== book.getBookId()){
                    book.setRating(rating.getRating());
                }

            }
        }
        ;
        for (Book book : books) {
            list.add(new Double(book.getRating()));
        }
        return list;
    }
}
