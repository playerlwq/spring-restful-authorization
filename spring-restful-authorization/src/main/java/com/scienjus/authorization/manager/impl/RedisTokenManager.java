package com.scienjus.authorization.manager.impl;

import com.scienjus.authorization.manager.TokenManager;
import com.scienjus.authorization.model.TokenModel;
import com.scienjus.config.Constants;
import com.scienjus.utils.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 * @see com.scienjus.authorization.manager.TokenManager
 * @author lwq
 */
@Component
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<String, String> redis;
    
    private JSONUtils jsonUtils=new JSONUtils();
    @Autowired
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public TokenModel createToken(long userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(jsonUtils.toJson(model), Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    /**
     * 获取token对象。反序列json
     * 
     */
    public TokenModel getToken(String authentication) {
    	
    	 String res = redis.boundValueOps(authentication).get();
    	 TokenModel resultObj =(TokenModel) jsonUtils.parseJson2Clazz(res, TokenModel.class);
//    	 	jsonUtils.parseJsonList(null, TokenModel.class);
    			 
//        if (authentication == null || authentication.length() == 0) {
//            return null;
//        }
//        String[] param = authentication.split("_");
//        if (param.length != 2) {
//            return null;
//        }
//        //使用userId和源token简单拼接成的token，可以增加加密措施
//        long userId = Long.parseLong(param[0]);
//        String token = param[1];
        return resultObj;
    }

    public boolean checkToken(String authorization) {
        if (authorization == null) {
            return false;
        }
        String token = redis.boundValueOps(authorization).get();
    	 TokenModel resultObj =(TokenModel) jsonUtils.parseJson2Clazz(token, TokenModel.class);
    	 if(authorization==null||!(authorization.equals(resultObj.getToken()))){
    		  return false;
    	 }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        Boolean expire = redis.boundValueOps(authorization).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return expire;
    }

    public void deleteToken(String userId) {
        redis.delete(userId);
    }
}
