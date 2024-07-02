package com.flab.blackfriday.modules.product.coupon.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * packageName    : com.flab.blackfriday.product.coupon.util
 * fileName       : CouponRandomUtil
 * author         : GAMJA
 * date           : 2024/05/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/26        GAMJA       최초 생성
 */
public class CouponRandomUtil{

    //랜덤 문자,숫자 생성
    public static String randomMix(int range) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for(int i=0;i<range;i++){

            if(rd.nextBoolean()){
                sb.append(rd.nextInt(10));
            }else {
                sb.append((char)(rd.nextInt(26)+65));
            }
        }

        return sb.toString();
    }

    /**
     * UUid 쿠폰 생성
     * @param range
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String createUuid(int range) throws NoSuchAlgorithmException {
        if((range/2) < 2){
            throw new NoSuchAlgorithmException("랜덤 생성 길이는 2보다 커야합니다. 길이 : "+(range/2));
        }
        String uuidString = UUID.randomUUID().toString();
        byte[] uuidBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes  = md.digest(uuidBytes);
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j<(range/2); j++) {
            sb.append(String.format("%02x",hashBytes[j]));
        }
        return sb.toString().toUpperCase();
    }

}
