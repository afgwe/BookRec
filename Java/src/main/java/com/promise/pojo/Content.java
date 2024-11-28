package com.promise.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@TableName("tb_content")
public class Content {

  private long chapterId;
  private String chapter;
  private long bookId;
  private String chapterContent;
  private Integer mark;

}
