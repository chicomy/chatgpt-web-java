package com.hncboy.chatgpt.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hncboy.chatgpt.base.enums.GptUserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hncboy
 * @date 2023/3/28 09:56
 * 系统用户登录参数
 */
@Entity(name = "gpt_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "gpt_user", comment ="用户信息")
@TableName("gpt_user")
public class GptUserDO extends BaseEntity {

    @NotNull(message = "账号不能为空")
    @Schema(title = "账号")
    @Column(columnDefinition = "varchar(20) comment '账号' ")
    private String account;

    @NotNull(message = "密码不能为空")
    @Schema(title = "密码")
    @Column(columnDefinition = "varchar(1024) comment '密码' ")
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY) //序列化排除字段
    private String password;

   // @NotNull(message = "手机号不能为空")
    @Schema(title = "手机号")
    @Column(columnDefinition = "varchar(20)  comment '手机号' ")
    private String phone;

    @NotNull(message = "账号状态不能为空")
    @Schema(title = "账号状态")
    @Column(columnDefinition = "int comment '账号状态' ")
    private GptUserStatusEnum status=GptUserStatusEnum.ACTIVED;
}
