package cn.blog.handler.exception;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.model.ResponseResult;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@RestControllerAdvice
public class BindExceptionHandler {
    /**
     * 处理@RequestBody参数校验报错
     * 和
     * 处理json请求体调用接口校验失败抛出的异常
     * (支持 @Valid 和 @Validator)
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseResult bindExceptionHandle(BindException e) {
        // 获取校验异常的字段的信息
        List<FieldError> errorFields = e.getBindingResult().getFieldErrors();
        // 获取具体的message
        List<String> errorMsgs = errorFields.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        String msg = String.join(",", errorMsgs);
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAMETER_ERROR.getCode(), msg);
    }

    /**
     * 处理单个参数校验失败抛出的异常
     * (支持@Validator)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult constraintViolationExceptionHandler(ConstraintViolationException e) {
        // 获取校验异常的字段的信息
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        // 获取具体的message
        List<String> errorMsgs = constraintViolations.stream()
                .map(error -> error.getMessage())
                .collect(Collectors.toList());
        String msg = String.join(",", errorMsgs);
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAMETER_ERROR.getCode(), msg);
    }

}