package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;


    @Test
    public void loginTest() {
        Member member = new Member();
        member.setLoginId("thsckdgus0");
        member.setName("thsckd");
        member.setPassword("123123");

        memberRepository.save(member);

        em.flush();
        em.clear();

        assertThat(member.getLoginId()).isEqualTo(memberRepository.findByLoginId("thsckdgus0").get(0).getLoginId());

    }
}