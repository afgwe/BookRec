package com.promise.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Data
@TableName("tb_comment")
public class Comment {


  @TableId(type = IdType.AUTO)
  private long commentId;
  private long bookId;
  private long userId;
  private String content;
  private long parentCommentId;
  private double rating;
  private java.sql.Timestamp createTime;
  @TableField(exist = false)
  private Book book;
}
