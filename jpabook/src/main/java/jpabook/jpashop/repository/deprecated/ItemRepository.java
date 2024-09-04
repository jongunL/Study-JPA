package jpabook.jpashop.repository.deprecated;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;

@Deprecated
@Repository
public class ItemRepository {

	@PersistenceContext
	EntityManager em;
	
	/*
	 * save 메서드 하나로 저장과 수정(병합)을 다 처리한다.
	 *  - 클라이언트는 저장과 수정을 구분하지 않아도 되므로 로직이 단순화된다.
	 * 	- 식별자 값이 없으면 새로운 엔티티로 판단해서 영속화하고 있으면 이미 한 번 영속화 되었던 엔티티로 판단해서 수정(병합) 한다.
	 * 	    결국 여기서의 저장이라는 의미는 신규 데이터를 저장하는 것뿐만 아니라 변경된 데이터의 저장이라는 의미도 포함한다.
	 * 	- 단 이경우 기본 키 생성 전략은 자동 생성 전략이어야 한다.
	 */
	public void save(Item item) {
		if(item.getId() == null) em.persist(item);
		else em.merge(item);
	}
	
	public Item fineOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll() {
		return em
				.createQuery("SELECT i FROM Item i", Item.class)
				.getResultList();
	}
	
}
