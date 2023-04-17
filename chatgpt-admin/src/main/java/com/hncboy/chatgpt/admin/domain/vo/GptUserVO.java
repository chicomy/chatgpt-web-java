package com.hncboy.chatgpt.admin.domain.vo;


import com.hncboy.chatgpt.base.enums.GptUserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "用户管理展示")
public class GptUserVO {

    @Schema(title = "账号")
    private String account;

    @Schema(title = "手机号")
    private String phone;

    @Schema(title = "账号状态")
    private GptUserStatusEnum status;
}
