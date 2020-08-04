package com.huanfion.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.huanfion.wms.entity.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author huanfion
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    private Long id;
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

    private LocalDateTime lastlogined;

    /**
     * 登录次数
     */
    private Long count;

    /**
     * 状态 1启用 0停用
     */
    private CommonStatus status;

    /**
     * 创建人
     */
    private Integer createid;

    /**
     * 创建时间
     */
    private LocalDateTime createdate;

    /**
     * 修改人ID
     */
    private Integer modifyid;

    /**
     * 修改时间
     */
    private LocalDateTime modifydate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
