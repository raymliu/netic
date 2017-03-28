package com.rays.dto;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by hand on 2017/3/23.
 */

@Entity
@Table(name ="net_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    @NotNull
    //@Type(type = "varchar" )
    @Size(max = 30)
    private String name ;

    private Integer age ;

    private Boolean sex ;

    private DateTime birthday ;

    //@Type(type ="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

    private String address ;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                ", address='" + address + '\'' +
                '}';
    }
}
