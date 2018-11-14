package com.tip.travel.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tip.travel.api.utils.JsonEntity;
import com.tip.travel.api.utils.JwtHelper;
import com.tip.travel.api.utils.ResponseHelper;
import com.tip.travel.common.annotation.CurrentUser;
import com.tip.travel.common.annotation.LoginIgnore;
import com.tip.travel.common.bo.LoginBo;
import com.tip.travel.common.bo.UserBasicInfo;
import com.tip.travel.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class UserController {

    @Reference
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    @LoginIgnore
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) {
        UserBasicInfo loginRes = userService.login(loginBo);
        String jwtToken = jwtHelper.createJwtToken(loginRes.getUserId());
        loginRes.setToken(jwtToken);
        userService.cacheUserToken(loginRes);
        return ResponseHelper.createInstance(jwtToken);
    }

    @GetMapping("/me")
    public JsonEntity<UserBasicInfo> getMe(@CurrentUser UserBasicInfo user) {
        // String jwtToken = jwtHelper.createJwtToken(loginRes.getUserId());
        return ResponseHelper.createInstance(user);
    }
}
