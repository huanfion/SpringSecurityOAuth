package com.huanfion.wms.entity.dto;

import lombok.Data;

/**
 * OAuth2 返回的token
 */
@Data
public class WMSToken {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private Integer expires_in;

    private String scope;
}
