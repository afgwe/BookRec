package com.promise.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@TableName("tb_rating")
public class Rating {

  private long ratingId;
  private long bookId;
  private long userId;
  private double rating;
  
  @TableField(exist = false)
  private Book book;


}
