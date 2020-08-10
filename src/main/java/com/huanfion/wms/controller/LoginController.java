package com.huanfion.wms.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.huanfion.wms.common.model.AjaxResult;
import com.huanfion.wms.entity.dto.WMSToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping("/success")
    @ResponseBody
    public String success(){
        return getUsername()+" 登录成功";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "hello world";
    }
    //获取当前用户信息
    private String getUsername(){
        String username = null;
        //当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //用户身份
        Object principal = authentication.getPrincipal();
        if(principal == null){
            username = "匿名";
        }
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        }else{
            username = principal.toString();
        }
        return username;
    }

    /**
     * 第三方应用的回调地址，这里是测试，写在了授权服务器上了
     * @param code
     * @return
     */
    @GetMapping("/rollback")
    @ResponseBody
    public AjaxResult rollback(String code){
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", "client");
        params.put("client_secret", "secret");
        params.put("code", code);
        String post = HttpUtil.post("http://localhost:9000/oauth/token", params);
        WMSToken wmsToken = JSONUtil.toBean(post, WMSToken.class);
        //拿到token 可以根据第三方自己的业务需求跳转到自己想去的地址
        return  AjaxResult.success(wmsToken);
    }
}
