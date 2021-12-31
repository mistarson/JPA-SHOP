package jpabook.jpashop.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderSearch;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QOrder.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {

        return queryFactory
                .select(order)
                .from(order)
                .leftJoin(order.member, member)
                .where(memberNameEq(orderSearch.getMemberName()),
                        orderStatusEq(orderSearch.getOrderStatus()))
                .fetch();
    }

    private BooleanBuilder orderStatusEq(OrderStatus orderStatus) {
        return nullSafeBuilder(() -> order.status.eq(orderStatus));
    }

    private BooleanBuilder memberNameEq(String memberName) {
        return nullSafeBuilder(() -> member.name.eq(memberName));
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }
}
