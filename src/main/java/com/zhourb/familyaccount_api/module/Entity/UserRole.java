package com.zhourb.familyaccount_api.module.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 周如彬
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    private Integer id;

    private Integer uid;

    private Integer rid;
}
