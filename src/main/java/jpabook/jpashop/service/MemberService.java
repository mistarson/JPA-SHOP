package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.form.MemberForm;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
//    @Transactional
//    public Long join(Member member) {
//        validateDuplicateMember(member); // 중복 회원 검증 로직
//        memberRepository.save(member);
//        return member.getId();
//    }


    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) throws Exception {
        return memberRepository.findById(memberId).orElseThrow(() -> new Exception("Member set null"));
    }

    @Transactional
    public Long join(MemberForm memberForm) {

        validateDuplicateLoginId(memberForm.getLoginId()); // 중복 회원 검증 로직

        Member member = memberRepository.save(DtoToEntity(memberForm));

        return member.getId();
    }

    private void validateDuplicateLoginId(String loinId) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByLoginId(loinId);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private Member DtoToEntity(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setLoginId(memberForm.getLoginId());
        member.setPassword(memberForm.getPassword());
        member.setAddress(new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));

        return member;
    }




}
