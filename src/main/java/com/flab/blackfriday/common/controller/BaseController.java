package com.flab.blackfriday.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.common.web
 * fileName       : BaseController
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 전체 공통 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**결과 처리 Map*/
    protected Map<String,Object> modelMap = null;

    protected final String MGN_URL = "/mgn";

    //modelMap 초기화
    @ModelAttribute
    protected void initModelMap(){
        modelMap = new LinkedHashMap<>();
    }

}
