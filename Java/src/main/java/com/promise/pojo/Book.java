package com.promise.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_book")
public class Book {

  private long bookId;
  private String bookName;
  private String author;
  private String publisher;
  private String publicationDate;
  private long categoryId;
  private String isbn;
  private String description;

  private Double rating;
  private String coverImage;
  private java.sql.Timestamp createTime;
  private long clickNum;
  private long shareNum;


}
