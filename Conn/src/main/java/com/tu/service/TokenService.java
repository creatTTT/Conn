package com.tu.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tu.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/11/25 0025.
 */
@Service
public class TokenService {

    /**
     * Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
     withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
     * @param user
     * @return
     */
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getuId()+"")// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPhonenum()));// 以 password 作为 token 的密钥
        return token;
    }
}
