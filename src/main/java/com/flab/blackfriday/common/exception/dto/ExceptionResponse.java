package com.flab.blackfriday.common.exception.dto;

/**
 * packageName    : com.flab.blackfriday.common.exception.dto
 * fileName       : ExceptionResponse
 * author         : GAMJA
 * date           : 2024/05/03
 * description    : 공통 예외처리 결과 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
public record ExceptionResponse(
        String message,
        String errorCode
        ) {
}
