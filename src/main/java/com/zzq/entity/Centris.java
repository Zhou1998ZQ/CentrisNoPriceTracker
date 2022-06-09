package com.zzq.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@TableName("t_centris")
public class Centris {
    private static final long serialVersionUID = 1L;

    @TableId(value = "centris_id", type = IdType.ASSIGN_ID)
    private Long centrisId;

    @TableField("centris_no")
    private Long centrisNo;

    @TableField("is_active")
    private Boolean isActive;


    @TableField("price")
    private String price;


    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
