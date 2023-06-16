package com.zhourb.familyaccount_api.common.aspect.log;

import java.lang.annotation.*;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/3/10 17:24
 * @description 自定义操作日志注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    String operModul() default ""; // 操作模块

    String operType() default ""; // 操作类型

    String operDesc() default ""; // 操作说明
}
