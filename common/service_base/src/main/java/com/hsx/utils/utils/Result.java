package com.hsx.utils.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一接口返回数据
 *
 * @author HEXIONLY
 * @date 2022/3/6 16:29
 */
@Data
public class Result {

    /**
     * true or false
     * 成功还是失败
     */
    private Boolean status;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据对象
     */
    private Map<String, Object> data = new HashMap<>();

    public Result() {

    }

    /**
     * 操作成功
     *
     * @return success result
     */
    public static Result success() {
        return new Result() {{
            setStatus(true);
            setCode(200);
            setMessage("success");
        }};
    }

    /**
     * 操作失败
     *
     * @return error result
     */
    public static Result error() {
        return new Result() {{
            setStatus(true);
            setCode(400);
            setMessage("success");
        }};
    }

    public Result status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result data(String key, Object data) {
        this.data.put(key, data);
        return this;
    }

    public Result data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }
}
