package com.zhourb.familyaccount_api.module.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhourb.familyaccount_api.module.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 周如彬
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailVo {
    private Integer id;

    @NotBlank(message = "账号不能为空")
    @Length(max=50,message="最大长度为50")
    private String username;

    @NotBlank(message = "姓名不能为空")
    @Length(max=50,message="最大长度为50")
    private String usernick;

    @NotBlank(message = "密码不能为空")
    @Length(max=50,message="最大长度为50")
    private String password;

    @Length(max=11,message="最大长度为11")
    @Pattern(regexp="^1[3-9]\\d{9}$",message = "手机号格式不对！")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private Integer sex;
    private String address;
    private String creator;
    //由于boolean类型默认值不能为null只有Boolean才行 但是此处不能改为Boolean 因为下面重写了isEnabled方法 值不能为null
    //所以新建了一个实体类UserDetailVo 为了方便给前端做查询使用
    private Boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;
    private String avatar;
    private String code;
    @JsonIgnore
    private Collection<GrantedAuthority> authorities;
    private Integer page = 1;
    private Integer size = 10;

    private List<Role> roleList;
    //上传头像使用的构造函数
    public UserDetailVo(Integer id,String avatar){
        this.id = id;
        this.avatar = avatar;
    }
}
