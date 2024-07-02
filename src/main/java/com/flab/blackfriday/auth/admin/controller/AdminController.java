package com.flab.blackfriday.auth.admin.controller;

import com.flab.blackfriday.auth.admin.service.AdminService;
import com.flab.blackfriday.common.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.flab.blackfriday.auth.admin.controller
 * fileName       : AdminController
 * author         : GAMJA
 * date           : 2024/05/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class AdminController extends BaseController {

    private final AdminService adminService;

}
