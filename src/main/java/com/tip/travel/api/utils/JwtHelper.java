package com.tip.travel.api.utils;

import com.tip.travel.api.config.TokenConfig;
import com.tip.travel.common.exception.BizException;
import com.tip.travel.common.exception.UnauthenticatedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtHelper {

    @Autowired
    private TokenConfig tokenConfig;

    /**
     * 生成token
     *
     * @param id 一般传入userName
     * @return
     */
    public String createJwtToken(Long id) {
        String issuer = "travel.nupday.com";
        long ttlMillis = System.currentTimeMillis();
        return createJwtToken(id, issuer, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param ttlMillis 签发时间
     * @return token String
     */
    public String createJwtToken(Long id, String issuer, long ttlMillis) {

        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        Date now = Calendar.getInstance().getTime();

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenConfig.getSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id + "")
                                 .setIssuedAt(now)
                                 .setIssuer(issuer)
                                 .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = now.getTime() + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();

    }

    // Sample method to validate and read the JWT
    public Claims parseJWT(String jwt) {
        try {
            // This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(tokenConfig.getSecret()))
                                .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (Exception e) {
            throw new UnauthenticatedException("Token错误");
        }
    }
}