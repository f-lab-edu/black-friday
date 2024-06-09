package com.flab.blackfriday.modules.product.coupon.util;

import java.util.Random;

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

}
