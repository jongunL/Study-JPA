package jpabook.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Order;

//복잡한 검색 로직은 스프링 데이터 JPA가 제공하는 명세 기능을 사용해서 구현할 수 있다.
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {}
