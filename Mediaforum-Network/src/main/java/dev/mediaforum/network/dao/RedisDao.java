package dev.mediaforum.network.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

//    public void set(String key, String value) {
//        Jedis jedis = new Jedis();
//
//        String phoneNum = "12345";
//
//        // send_check_code(phoneNum):
//        {
////            if (jedis.get("vcode_phone_$pNum_alreadysendNum") > 3) throw Ex;
//
//            String randCode = "SomeRandCode"; // genCode()
//            // sendToPhone(phoneNum, randCode);
//            jedis.setex("vcode_phone_"+phoneNum, 2*60, randCode);
//
////            jedis.incr("vcode_phone_$pNum_alreadysendNum");
////            jedis.expire("vcode_phone_$pNum_alreadysendNum", 24*60*60);
//        }
//
//        // validate_check_code(phoneNum, checkCode):
//        {
//            String checkCode = "SomeRandCode"; // from user submit
//
//            if (checkCode == jedis.get("vcode_phone_"+phoneNum)); // pass
//        }
//
//        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.DAYS);
//    }

//    public String get(String key) {
//        return (String)redisTemplate.opsForValue().get(key);
//    }


}
