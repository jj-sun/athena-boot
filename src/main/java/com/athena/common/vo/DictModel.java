package com.athena.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sunjie
 * @date 2022/1/20 10:21
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 749454345159553637L;

    public DictModel() {}

    public DictModel(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 字典value
     */
    private String value;
    /**
     * 字典文本
     */
    private String text;

    /**
     * 特殊用途： JgEditableTable
     * @return
     */
    public String getTitle() {
        return this.text;
    }

}
