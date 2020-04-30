package com.rays.dto;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by hand on 2017/3/23.
 */

@Entity
@TableName("net_user")
@Data
public class User extends BaseEntity implements Serializable {

    @Id
    private Long id ;

    @NotNull
    @Size(max = 30)
    private String name ;

    private Integer age ;

    private Boolean sex ;

    private String phone;

    private String avatar;

    private String email;

    private String password;

    private String comment;


}
