package com.flab.blackfriday.auth.member.repository;

import com.flab.blackfriday.auth.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * packageName    : com.flab.blackfriday.auth.member.repository
 * fileName       : MemberRepository
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member,String> {
}
