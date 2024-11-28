package com.promise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.promise.pojo.Book;
import com.promise.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    //查询所有评论
    @Select("select * from tb_comment")
    List<Comment> selectAll();

    @Select("select * from tb_comment where comment_id=#{commentId}")
    Comment selectByCommentId(Integer commentId);

    //通过id查询
    @Select("select * from tb_comment where book_id = #{book_id}")
    List<Comment> selectById(Integer book_id);

    @Select("select * from tb_comment where comment_id = #{user_id}")
    List<Comment> selectByUserId(Integer user_id);

    //添加
    @Insert("insert into tb_comment (book_id,user_id,content,parent_comment_id,rating ,create_time) values(#{bookId},#{userId},#{content},#{parentCommentId},#{rating},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "commentId",keyColumn = "comment_id")
    Integer addComment(Comment comment);

    @Update("update tb_comment set rating=#{rating} where comment_id=#{commentId}")
    Integer updateRating(Comment comment);

    @Delete("delete from tb_comment where comment_id=#{comment_id}")
    Integer deleteComment(Integer comment_id);

    @Delete("delete from tb_comment where book_id=#{book_id}")
    Integer deleteCommentByBookId(Integer book_id);

    @Delete("delete from tb_comment where comment_id= #{comment_id}")
    void delete(Integer comment_id);


    @Delete("delete from tb_comment where user_id= #{user_id}")
    void deleteCommentByUserId(Integer user_id);

    @Select("select * from tb_comment where user_id=#{user_id}")
    @Results(
            {
                    @Result(column = "comment_id",property = "commentId"),
                    @Result(column = "book_id",property = "bookId"),
                    @Result(column = "user_id",property = "userId"),
                    @Result(column = "content",property = "content"),
                    @Result(column = "parent_comment_id",property = "parentCommentId"),
                    @Result(column = "rating",property = "rating"),
                    @Result(column = "create_time",property = "createTime"),
                    @Result(column = "book_id",property = "book",javaType = Book.class,
                    one=@One(select = "com.promise.dao.BookDao.selectById"))
            }
    )
    List<Comment> selectCommentAndBook(Integer user_id);


    @Select("select * from tb_comment order by create_time desc ")
    @Results(
            {
                    @Result(column = "comment_id",property = "commentId"),
                    @Result(column = "book_id",property = "bookId"),
                    @Result(column = "user_id",property = "userId"),
                    @Result(column = "content",property = "content"),
                    @Result(column = "parent_comment_id",property = "parentCommentId"),
                    @Result(column = "rating",property = "rating"),
                    @Result(column = "create_time",property = "createTime"),
                    @Result(column = "book_id",property = "book",javaType = Book.class,
                            one=@One(select = "com.promise.dao.BookDao.selectById"))
            }
    )
    List<Comment> selectAllCommentAndBook();
}
