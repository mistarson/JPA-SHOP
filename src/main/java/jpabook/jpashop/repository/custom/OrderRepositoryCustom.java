package jpabook.jpashop.repository.custom;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAll(OrderSearch orderSearch);
}
