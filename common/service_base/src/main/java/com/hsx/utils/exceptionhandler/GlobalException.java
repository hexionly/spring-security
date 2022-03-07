package com.hsx.utils.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局异常处理
 *
 * @author HEXIONLY
 * @date 2022/3/6 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    private Integer code;

    private String message;
}
