package jpashop.jpabook.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockExcpetion;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class OrderServiceTest {

	@PersistenceContext
	EntityManager em;
	
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception {
		//Given
		//	- 테스트를 위한 회원과 상품을 만듦
		Member member = createMember();
		Item item = createBook("시골JPA", 10000, 10);	//이름, 가격, 재고
		int orderCount = 2;
		
		//When
		//	- 실제 상품을 주문
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		//Then
		//	- 주문 가격이 올바른지, 주문 후 재고 수량이 정확히 줄었는지 확인
		Order getOrder = orderRepository.fineOne(orderId);
		
		Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
		Assert.assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, getOrder.getTotalPrice());
		Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
	}
	
	@Test(expected = NotEnoughStockExcpetion.class)
	public void 상품주문_재고수량초과() throws Exception {
		//Given
		Member member = createMember();
		Item item = createBook("시골JPA", 10000, 10);	//이름, 가격, 재고
		int orderCount = 11;							//재고보다 많은 수량 주문
		
		//When
		orderService.order(member.getId(), item.getId(), orderCount);
		
		//Then
		Assert.fail("재고 수량 부족 예외가 발생해야 한다.");
	}
	
	@Test
	public void 주문취소() {
		//Given
		Member member = createMember();
		Item item = createBook("시골JPA", 10000, 10);	//이름, 가격, 재고
		int orderCount = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		//When
		orderService.cancelOrder(orderId);
		
		//Then
		Order getOrder = orderRepository.fineOne(orderId);
		
		Assert.assertEquals("주문 취소시 상태는 CANCLE이다.", OrderStatus.CANCEL, getOrder.getStatus());
		Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuuantity());
		
	}

	private Item createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setStockQuuantity(stockQuantity);
		book.setPrice(price);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		return member;
	}
	
}
