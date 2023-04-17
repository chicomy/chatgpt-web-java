package com.hncboy.chatgpt.admin.domain.query;

import com.hncboy.chatgpt.base.domain.query.PageQuery;
import com.hncboy.chatgpt.base.enums.EnableDisableStatusEnum;
import com.hncboy.chatgpt.base.enums.GptUserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hncboy
 * @date 2023/3/27 23:18
 * 聊天记录分页查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(title = "用户管理分页查询")
public class GptUserPageQuery extends PageQuery {

    @Schema(title = "账号")
    private String account;

    @Schema(title = "账号状态")
    private GptUserStatusEnum status;
}
