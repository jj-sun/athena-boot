package com.athena.common.base.po;

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

    private String creator;

    private Date ctime;

    private String editor;

    private Date mtime;

    private Integer delFlag;
}
