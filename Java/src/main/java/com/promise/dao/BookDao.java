package com.promise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.promise.pojo.Book;
import com.promise.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookDao extends BaseMapper<Book>{

    //查询所有书
    @Select("select * from tb_book")
    List<Book> selectBook();

    //通过id查书
    @Select("select * from tb_book where book_id = #{book_id}")
    Book selectById(Integer book_id);

    @Select("select * from tb_book where book_name like #{book_name}")
    List<Book> selectBookByName(String book_name);

    @Select("select * from tb_book where book_name like #{book_author}")
    List<Book> selectBookByAuthor(String book_author);

    @Delete("delete from tb_book where book_id=#{book_id}")
    Integer deleteBook(Integer book_id);


    @Insert("insert into tb_book values(#{bookId},#{bookName},#{author},#{publisher},#{publicationDate}," +
            "#{categoryId},#{isbn},#{description},#{rating},#{coverImage},#{createTime},#{clickNum},#{shareNum})")
    Integer insertBook(Book book);

    @Update("update tb_book set click_num=#{clickNum} where book_id=#{bookId}")
    Integer updateBookClickNum(Book book);

    @Update("update tb_book set book_name=#{bookName},author=#{author},publisher=#{publisher}," +
            "publication_date=#{publicationDate},description=#{description},rating=#{rating}," +
            "cover_image=#{coverImage},ISBN=#{isbn} where book_id=#{bookId}")
    Integer update(Book book);
}
