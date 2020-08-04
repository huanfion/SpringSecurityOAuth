package com.huanfion.wms.entity.query;

import com.huanfion.wms.entity.enums.CommonStatus;
import lombok.Data;

@Data
public class UserParam {
    /**
     * 用户名
     */
    private String username;
    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 状态 1启用 0停用
     */
    private CommonStatus status;
}
