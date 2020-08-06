package com.huanfion.wms.config;

import com.huanfion.wms.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return  new UserServiceImpl();
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // 不声明bean会提示
    // AuthorizationServerConfig required a bean of type 'org.springframework.security.authentication.AuthenticationManager' that could not be found.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      /*  auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("huanfion").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("lisi").password(passwordEncoder().encode("666666")).roles("USER");*/
        //使用自定义UserDetailService
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //将项目中的静态资源路径开放出来,如果要能访问静态资源需要配置webmvc====registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 安全拦截机制（最重要）
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/page").hasAuthority("ADMIN")
                .antMatchers("/user/list").hasAuthority("USER")
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                .antMatchers("/login","/login/form").permitAll()
                .anyRequest().authenticated()//除了/r/**，其它的请求可以访问
        .and().formLogin()
        .loginPage("/login")//指定登录页面
        .loginProcessingUrl("/login/form")//指定登录处理方法，需要和自定义页面的post路径保持一致
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        ;
                //.successForwardUrl("/success");//验证成功后跳转的页面
    }
}
