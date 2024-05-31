package cn.blog.Validate;

import cn.blog.annotation.AdminMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
public class AdminMatchValidator implements ConstraintValidator<AdminMatch, Integer> {


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != 1;
    }

}
