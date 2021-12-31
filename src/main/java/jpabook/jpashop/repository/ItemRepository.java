package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@RequiredArgsConstructor
public interface ItemRepository extends JpaRepository<Item, Long> {


//    public void save(Item item) {
//        if (item.getId() == null) {
//            em.persist(item);
//        } else {
//            em.merge(item); // update와 비슷
//        }
//    }

//    public Item findOne(Long id) {
//        return em.find(Item.class, id);
//    }

//    public List<Item> findAll() {
//        return em.createQuery("select i from Item i", Item.class)
//                .getResultList();
//    }
}
