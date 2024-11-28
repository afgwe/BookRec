package com.promise.dao;

import com.promise.pojo.Book;
import com.promise.pojo.Click;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClickDao {

    @Select("select * from tb_click")
    List<Click> selectClick();

    @Select("select * from tb_click where user_id=#{user_id}")
    List<Click> selectClickByUserId(Integer user_id);

    //通过id查书
    @Select("select * from tb_click where book_id = #{book_id} AND user_id=#{user_id}")
    Click selectById(Integer book_id ,Integer user_id);


    @Delete("delete from tb_click where user_id=#{user_id}")
    Integer deleteClick(Integer user_id);

    @Delete("delete from tb_click where book_id=#{book_id}")
    Integer deleteClickByBookId(Integer book_id);

    @Insert("insert into tb_click values(#{id},#{userId},#{clickNum},#{bookId})")
    Integer insertClick(Click click);

    @Update("update tb_click set click_num=#{clickNum} where book_id=#{bookId} AND user_id=#{userId}")
    Integer updateClickNum(Click click);

}
