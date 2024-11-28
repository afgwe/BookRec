package com.promise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.promise.pojo.Book;
import com.promise.pojo.Comment;
import com.promise.pojo.Rating;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RatingDao extends BaseMapper<Rating> {

    //查询所有用户的所有评论
    @Select("select * from tb_rating")
    List<Rating> selectAll();

    //查一个用户的所有评论
    @Select("select * from tb_rating where user_id=#{user_id}")
    List<Rating> selectByUserId(Integer user_id);

    @Select("select * from tb_rating where book_id=#{book_id}")
    List<Rating> selectByBookId(Integer book_id);

    //查一个用户对某本书的评价
    @Select("select * from tb_rating where user_id=#{user_id} and book_id=#{book_id}")
    Rating selectOneRatingByUserIdAndBookId(Integer user_id,Integer book_id);

    //插入一个评价
    @Insert("insert into tb_rating values (#{ratingId},#{bookId},#{userId},#{rating})")
    Integer addRating(Rating rating);

    //更新一个用户对一本书的评价
    @Update("update tb_rating set rating=#{rating} where user_id=#{userId} and book_id=#{bookId}")
    Integer updateRating(Rating rating);

    //
    @Delete("delete from tb_rating where rating_id=#{ratingId}")
    Integer deleteRating(Integer rating_id);

    @Delete("delete from tb_rating where book_id=#{book_id}")
    Integer deleteRatingByBookId(Integer book_id);

    @Delete("delete from tb_rating where user_id=#{user_id}")
    Integer deleteRatingByBookUserId(Integer user_id);


    //
    @Select("select * from tb_rating")
    @Results(
            {
                    @Result(column = "rating_id",property = "ratingId"),
                    @Result(column = "user_id",property = "userId"),
                    @Result(column = "book_id",property = "bookId"),
                    @Result(column = "rating",property = "rating"),
                    @Result(column = "book_id",property = "book",javaType = Book.class,
                            one=@One(select = "com.promise.dao.BookDao.selectById"))
            }
    )
    List<Rating> selectRatingAndBook();

}
