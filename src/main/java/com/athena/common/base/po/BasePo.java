package com.athena.common.base.po;

import com.athena.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sunjie
 * @date 2021/12/14 12:45
 * @description
 */
@Data
public class BasePo implements Serializable {

    @Serial
    private static final long serialVersionUID = -8172686200993082466L;

    @TableId
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private Date ctime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String editor;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date mtime;
    @Dict(dicCode = "del_flag")
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;
}
