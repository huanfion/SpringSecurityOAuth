package com.huanfion.wms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
//配置授权服务器
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private ClientDetailsService clientDetailsService;
    /**
     *  注入authenticationManager 来支持password
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
    设置授权码模式
     */
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    /**
     * 令牌存储策略
     * 1.InMemoryTokenStore：保存在内存中
     * 2.JdbcTokenStore：基于JDBC的实现版本，令牌会保存到数据库中。不同服务器之间共享令牌，使用这种方式需要引入依赖 spring-jdbc
     * 3.JwtTokenStore:JSON Web Token(JWT),它可以把令牌相关的数据编码（因此对于后端服务来说，它不需要存储，这是重大优势）,
     *   缺点是撤销一个已经授权令牌将会非常困难，通常用来处理一个生命周期较短的令牌以及撤销刷新令牌
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //内存方式，生成普通令牌
        return new InMemoryTokenStore();
    }

    /**
     * 使用内存方式管理授权码
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //令牌存储策略
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
        //默认30天，这里修改
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenServices;
    }
    /**
     * 用来配置客户端详情服务（clientDetailsService）,客户端详情信息在这里初始化
     * 你可以写死在这里也可以通过数据库来存储调取详细信息AuthorizeConfigProvider
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //region =======使用内存方式=================
        clients.inMemory()
                //客户端id
                .withClient("client")
                //客户端密钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                //客户端可以访问的资源列表
                .resourceIds("res1")
                //支持的授权方式
                .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit", "client_credentials")
                //允许授权范围，自定义
                .scopes("all")
                //是否自动授权，true则不需跳转到授权页面
                .autoApprove(true)
                //验证回调地址
                .redirectUris("http://localhost:9000/rollback");
        //endregion
    }

    /**
     * 用来配置令牌端点的安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");//开启/oauth/token_key 验证端口无权限访问
        security.checkTokenAccess("permitAll()");//开启/oauth/check_token验证端口认证权限访问
        security.allowFormAuthenticationForClients();//表单认证，申请令牌
    }


    /**
     * 用来配置令牌（token）的访问端点和令牌服务
     * 令牌服务存储有三种方式：
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//开启密码授权模式
                .authorizationCodeServices(authorizationCodeServices)//授权码模式需要
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许post请求
    }
}
