package jpabook.jpashop.domain;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class OrderSearch {

	private String memberName;		//회원 이름
	private OrderStatus orderStatus;//주문 상태
	
	
}
