package com.flab.blackfriday.auth.member.controller;

import com.flab.blackfriday.auth.jwt.JwtProvider;
import com.flab.blackfriday.auth.member.dto.MemberCreateRequest;
import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.auth.member.dto.MemberSummaryResponse;
import com.flab.blackfriday.auth.member.service.MemberService;
import com.flab.blackfriday.common.controller.BaseModuleController;
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

    private final JwtProvider jwtProvider;

    private final MemberSession memberSession;

    /**
     * 회원 정보 조회
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/member/view")
    public MemberSummaryResponse selectMypageMember() throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 회원 정보가 존재하지 않습니다. ### ");
            return null;
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
    @PostMapping(API_URL+"/member/ins")
    public ResponseEntity<?> createMember(@RequestBody @Valid final MemberCreateRequest memberRequest) throws Exception {

        try{
            MemberDto prevDto = new MemberDto();
            prevDto.setId(memberRequest.getId());
            prevDto = memberService.selectMember(prevDto);
            if(prevDto != null){
                modelMap.put("msg", "이미 사용중인 아이디 입니다.");
                return new ResponseEntity<>(modelMap,HttpStatus.FOUND);
            }
            memberService.saveMember(MemberDto.createOf(memberRequest));
        }catch (Exception e){
            logger.error("create member error : {}",e.getMessage());
            modelMap.put("msg","회원가입 진행시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap,HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","회원가입 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 로그인
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/member/login")
    public ResponseEntity<?> login(@RequestParam("id") String id,
                                   @RequestParam("password") String password) throws Exception {

        MemberDto prevDto = new MemberDto();
        prevDto.setId(id);
        prevDto = memberService.selectMember(prevDto);

        if(prevDto == null){
            modelMap.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNAUTHORIZED);
        }

        if(!PasswordEncoderTypeHandler.matches(password,prevDto.getPassword())){
            modelMap.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("token",memberSession.createToken(prevDto));
        modelMap.put("msg","로그인에 성공하였습니다.");
        return ResponseEntity.ok().headers(headers).body(modelMap);
    }

    /**
     * 로그아웃
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/member/logout")
    public ResponseEntity<?> logout() throws Exception {
        //로그아웃 처리
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }
}
