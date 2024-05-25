package com.flab.blackfriday.auth.member.controller;

import com.flab.blackfriday.auth.jwt.JwtProvider;
import com.flab.blackfriday.auth.member.dto.*;
import com.flab.blackfriday.auth.member.service.MemberService;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.common.response.CommonResponse;
import com.flab.blackfriday.common.typehandler.PasswordEncoderTypeHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.auth.member.web
 * fileName       : MemberUserController
 * author         : rhkdg
 * date           : 2024-04-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class MemberUserController extends BaseModuleController {

    private final MemberService memberService;

    private final MemberSession memberSession;

    /**
     * 회원 정보 조회
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/member/view")
    public MemberSummaryResponse selectMypageMember() throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberSession.getMemberSession().getId());
        memberDto = memberService.selectMember(memberDto);

        return MemberSummaryResponse.summaryViewOf(memberDto);
    }

    /**
     * 회원 가입
     * @param memberRequest
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/member/join")
    public ResponseEntity<?> createMember(@RequestBody @Valid final MemberCreateRequest memberRequest) throws Exception {

        try{
            MemberDto prevDto = new MemberDto();
            prevDto.setId(memberRequest.getId());
            prevDto = memberService.selectMember(prevDto);
            if(prevDto != null){
                return new ResponseEntity<>(new CommonResponse("이미 사용중인 아이디 입니다.",null),HttpStatus.FOUND);
            }
            memberService.saveMember(memberRequest);
        }catch (Exception e){
            logger.error("create member error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("회원가입 진행시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(new CommonResponse("회원가입 되었습니다.", null));
    }

    /**
     * 로그인
     * @Param memberLoginRequest
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/member/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest) throws Exception {

        MemberDto prevDto = MemberDto.loginOf(memberLoginRequest);
        prevDto = memberService.selectMember(prevDto);

        if(prevDto == null){
            modelMap.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNAUTHORIZED);
        }

        if(!PasswordEncoderTypeHandler.matches(memberLoginRequest.getPassword(),prevDto.getPassword())){
            modelMap.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,memberSession.createToken(prevDto));
        return ResponseEntity.ok().headers(headers).body(new CommonResponse("로그인에 성공하였습니다.",null));
    }

    /**
     * 로그아웃
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/member/logout")
    public ResponseEntity<?> logout() throws Exception {
        //로그아웃 처리
        return new ResponseEntity<>(new CommonResponse("로그아웃 되었습니다.",null),HttpStatus.OK);
    }
}
