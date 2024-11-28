package com.promise.controller;

import com.promise.dao.RatingDao;
import com.promise.pojo.Rating;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingDao ratingDao;

    //改或者加
    @PostMapping("/alert")
    public Integer alertRating(Integer user_id,Integer book_id, Double rating) {
        Rating ratingEntity = ratingDao.selectOneRatingByUserIdAndBookId(user_id,book_id);
        if (ratingEntity==null){
            if (rating>5||rating<0) return null;
            Rating ratingEntity1 = new Rating();
            ratingEntity1.setRatingId(0);
            ratingEntity1.setBookId(book_id);
            ratingEntity1.setUserId(user_id);
            ratingEntity1.setRating(rating);
            return ratingDao.addRating(ratingEntity1);
        }
        ratingEntity.setRating(rating);
        return ratingDao.updateRating(ratingEntity);
    }

    //删
    @PostMapping("/remove")
    public Integer removeRating(Integer rating_id) {
        System.out.println(rating_id);
        return ratingDao.deleteRating(rating_id);
    }

    //查一个用户的打分
    @PostMapping("get/user_id/all")
    public List<Rating> getByUserId(Integer user_id) {
        return ratingDao.selectByUserId(user_id);
    }

    //查一个用户对一本书的打分
    @PostMapping("get/user_id/book_id")
    public Double getOneBookRating(Integer user_id, Integer book_id) {
        //这里应该返回一个对象
        return ratingDao.selectOneRatingByUserIdAndBookId(user_id,book_id).getRating();
    }

    //查所有
    @GetMapping("/get/all")
    public List<Rating> getAll() {
        return ratingDao.selectAll();
    }

    //查所有用户所有的评论和评论的书籍信息
    @GetMapping("get/ratingAndBook")
    public List<Rating> getRatingAndBook (){
        return ratingDao.selectRatingAndBook().stream()
                .sorted(Comparator.comparing(Rating::getRatingId).reversed())
                .collect(Collectors.toList());
    }

    //查一个用户所有的评论和评论的书籍信息
    @PostMapping("get/user/ratingAndBook")
    public List<Rating> getRatingAndBookByUserId(Integer user_id){
        List<Rating> ratings = ratingDao.selectRatingAndBook();
        return ratings.stream()
                .filter(rating -> rating.getUserId()==user_id)
                .collect(Collectors.toList());
    }

}
