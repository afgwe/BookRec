package com.promise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.promise.pojo.Comment;
import com.promise.pojo.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContentDao extends BaseMapper<Content> {
    @Select("select * from tb_content where book_id=#{book_id}")
    List<Content> selectByBookId(Integer book_id);

}
