package jpabook.jpashop.domain;

import lombok.Setter;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class OrderSearch {

	private String memberName;		//회원 이름
	private OrderStatus orderStatus;//주문 상태
	
	public Specification<Order> toSpecification() {
		return Specification
				.where(OrderSpec.memberNameLike(memberName))
				.and(OrderSpec.orderStatusEq(orderStatus));
	}
	
}
