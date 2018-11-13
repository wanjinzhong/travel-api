package com.tip.travel.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tip.travel.api.config.TokenConfig;
import com.tip.travel.api.utils.JsonEntity;
import com.tip.travel.api.utils.JwtHelper;
import com.tip.travel.api.utils.ResponseHelper;
import com.tip.travel.common.bo.LoginBo;
import com.tip.travel.common.bo.LoginResBo;
import com.tip.travel.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private TokenConfig tokenConfig;

    @Reference
    private UserService userService;

    @PostMapping("/login")
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) {
        LoginResBo loginRes = userService.login(loginBo);
        String jwtToken = JwtHelper.createJWT(loginRes.getUserId(), loginRes.getUserName(),
                tokenConfig.getExpiresSecond() * 1000, tokenConfig.getSecret(), tokenConfig.getAlgorithm());
        jwtToken = "bearer;" + jwtToken;

        return ResponseHelper.createInstance(jwtToken);
    }
}
