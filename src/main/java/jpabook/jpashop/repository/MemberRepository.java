package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@RequiredArgsConstructor
public interface MemberRepository extends JpaRepository<Member, Long> {

   // @PersistenceContext //스프링이 생성한 EntityManage를 주입해준다.
//    private final EntityManager em;

//    public void save(Member member) {
//        em.persist(member);
//    }

//    public Member findOne(Long id) {
//        return em.find(Member.class, id);
//    }

//    public List<Member> findAll() {
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//
//    }

    public List<Member> findByName(String name);

}
