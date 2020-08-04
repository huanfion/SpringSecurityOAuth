package com.huanfion.wms.entity.dto;

import com.huanfion.wms.entity.enums.CommonStatus;
import lombok.Data;

@Data
public class UserInput {
    /**
     * 所属员工id
     */
    private Long employeeId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

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
