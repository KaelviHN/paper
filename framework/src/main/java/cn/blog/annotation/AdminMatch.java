package cn.blog.annotation;

import cn.blog.Validate.AdminMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AdminMatchValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface AdminMatch {

//    // 校验单个参数
//    int id() default 1;

    // 校验错误时返回的消息
    String message() default "";

    //分组校验
    Class<?>[] groups() default {};

    // 传递异常信息
    Class<? extends Payload>[] payload() default {};

}
