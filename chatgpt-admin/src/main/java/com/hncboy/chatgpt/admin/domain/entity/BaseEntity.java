package com.hncboy.chatgpt.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //标注父类
@EntityListeners(AuditingEntityListener.class) //jpa数据监听
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) //忽略解析的字段
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY) //序列化排除字段
    private Long id;
    @Column(columnDefinition = "varchar(32) comment '创建人' ")
    @CreatedBy
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '创建时间' ")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Column(columnDefinition = "varchar(32) comment '更新人' ")
    @LastModifiedBy
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '更新时间'")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
