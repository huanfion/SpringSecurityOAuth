package com.huanfion.wms.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通用枚举注入，注意需要实现 IEnums 也需要扫描枚举包
 */
@Getter
public enum CommonStatus {
    DISABLE(0,"停用"),
    ENABLE(1,"启用");
    CommonStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    @EnumValue
    @JsonValue
    private int value;
    private String desc;
}
