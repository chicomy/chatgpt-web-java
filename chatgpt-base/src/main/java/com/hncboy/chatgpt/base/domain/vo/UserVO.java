package com.hncboy.chatgpt.base.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "用户登录信息")
public class UserVO {

    @NotNull(message = "账号不能为空")
    @Schema(title = "账号")
    private String account;

    @NotNull(message = "密码不能为空")
    @Schema(title = "密码")
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY) //序列化排除字段
    private String password;

    @Schema(title = "手机号")
    private String phone;

    @Schema(title = "记住我")
    private Boolean rememberMe=false; //默认不记住
}
