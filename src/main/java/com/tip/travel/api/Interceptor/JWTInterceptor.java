package com.tip.travel.api.Interceptor;

import com.tip.travel.api.config.TokenConfig;
import com.tip.travel.api.utils.JwtHelper;
import com.tip.travel.common.exception.UnauthenticatedException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {


    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        } else {
            if (null == authHeader || !authHeader.startsWith("bearer;")) {
                throw new UnauthenticatedException("不正确的Token");

            }
        }
        final String token = authHeader.substring(7);
        Claims claims = JwtHelper.parseJWT(token, tokenConfig.getSecret());
        request.setAttribute("CLAIMS", claims);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
