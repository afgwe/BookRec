package com.promise.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user")
public class User {
    // 主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 1表示管理员 2表示用户
    private Integer identity;
    private String sex;
    // 验证码
    @TableField(exist = false)
    private String code;

    // 状态
    private Integer status;

    private String phone;

    private String email;

    private String info;
}
