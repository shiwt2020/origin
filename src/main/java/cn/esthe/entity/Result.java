package cn.esthe.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "返回结果类")
public class Result implements Serializable {
    
    @ApiModelProperty(value = "状态判断")
    private String success;
    
    @ApiModelProperty(value = "返回消息内容")
    private String message;

    public Result(String success,String message) {
        this.message = message;
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}