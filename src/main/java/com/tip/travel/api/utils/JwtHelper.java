package com.tip.travel.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;

public class JwtHelper {


    public static Claims parseJWT(String jsonWebToken, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jsonWebToken).getBody();
        return claims;
    }


    public static String createJWT(Long userId, String name, long TTLMillis, String secret, String algorithm) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.valueOf(algorithm);

        long nowMillis = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("unique_name", name)
                .claim("userid", userId)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Calendar exp = Calendar.getInstance();
            exp.setTimeInMillis(expMillis);
            builder.setExpiration(exp.getTime()).setNotBefore(now.getTime());
        }

        //生成JWT
        return builder.compact();
    }
}