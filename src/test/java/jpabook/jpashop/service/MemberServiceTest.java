package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.form.MemberForm;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {


    @Autowired EntityManager em;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("KIM");
        memberForm.setLoginId("123");
        memberForm.setPassword("123");
        Member member = memberService.DtoToEntity(memberForm);

        //when
        Long savedId = memberService.join(memberForm);


        Member findMember = memberService.findByLoginId(member.getLoginId());

        //then
        assertThat(member.getLoginId()).isEqualTo(findMember.getLoginId());
    }

    @Test
    void 중복_회원_예외() {
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("KIM");
        memberForm.setLoginId("123");
        memberForm.setPassword("123");


        MemberForm memberForm1 = new MemberForm();
        memberForm1.setName("LEE");
        memberForm1.setLoginId("123");
        memberForm1.setPassword("123");

        //when
        memberService.join(memberForm);

        //then
        assertThrows(IllegalStateException.class, ()->memberService.join(memberForm1));

    }


}