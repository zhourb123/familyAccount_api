package com.zhourb.familyaccount_api.module.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author 周如彬
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Integer id;
    @NotBlank(message = "角色名不能为空")
    @Length(max=50,message="最大长度为50")
    private String roleName;

    @Length(max=100,message="最大长度为100")
    private String description;

    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @Length(max=50,message="最大长度为50")
    private String creator;
    private Integer page = 1;
    private Integer size = 10;


}
