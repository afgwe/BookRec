package com.promise.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@TableName("tb_rating")
public class Click {

  private Integer id;
  private Integer userId;
  private Integer clickNum;
  private Integer bookId;


}
