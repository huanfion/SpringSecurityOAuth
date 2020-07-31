package com.huanfion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("huanfion").password(passwordEncoder().encode("123456")).authorities("p1").build());
        //信息若是没有设置角色授权，就会报错误 java.lang.IllegalArgumentException: Cannot pass a null GrantedAuthority collection
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("123456")).authorities("p2").build());
        return manager;
    }*/
    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("huanfion").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("lisi").password(passwordEncoder().encode("666666")).roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //将项目中的静态资源路径开放出来
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
                .antMatchers("/r/r1").hasAuthority("p2")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                .anyRequest().authenticated()//除了/r/**，其它的请求可以访问
        .and().formLogin().successForwardUrl("/success");
    }
}
