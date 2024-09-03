package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	
	private int orderPrice;
	private int count;
	
	//생성 메소드
	//	- 주문 상품, 가격, 수량 정보를 사용해서 주문 상품 엔티티를 생성한다.
	//	    그리고 주문한 수량만큼 상품의 재고를 줄인다.
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);
		return orderItem;
	}
	
	//비지니스 로직
	//주문 취소
	//	- 취소한 주문수량만큼 상품의 재고를 증가시킨다.
	public void cancel() {
		getItem().addStock(count);
	}
	
	//조회 로직
	//주문상품 전체 가격 조회
	//	- 주문 가격에 수량을 곱한 값을 반환한다.
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
	
}
