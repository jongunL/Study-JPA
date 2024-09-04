package jpabook.jpashop.repository.deprecated;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;

/*
 * @Repository
 * 	- <context:component-scan>에 의해 스프링 빈으로 자동 등록
 *  - JPA 전용 예외가 발생하면 스프링이 추상화한 예외로 변환해서 서비스 계층에 반환한다.
 *    따라서 서비스 계층은 JPA에 의존적인 예외를 처리하지 않아도 된다.
 */
@Deprecated
@Repository
public class MemberRepository {
	
	/*
	 * @PersistenceUnit
	 * 	- 직접 사용할 일은 거의 없겠지만, 컨테이너가 관리하는 엔티티 매니저를 주입받을 수 있다.
	 */
	@PersistenceUnit
	EntityManagerFactory emf;
	
	/*
	 * @PersistenceContext
	 *  - @PersistenceContext 애노테이션은 컨테이너가 관리하는 엔티티 매니저를 주입하는 어노테이션이다.
	 *  - 엔티티 매니저를 컨테이너로부터 주입 받아서 사용함으로써 컨테이너가 제공하는 트랜잭션 기능과 연계해서 컨테이너의 다양한 기능들을 사용할 수 있게 된다.
	 * 	- 순수 자바 환경에서는 엔티티 매니저 팩토리에서 엔티티 매니저를 직접 생성해서 사용했지만,
	 *    스프링이나 J2EE 컨테이너를 사용하면 컨테이너가 엔티티 매니저를 관리하고 제공해준다.
	 *    따라서 엔티티 매니저 팩토리에서 엔티티 매니저를 직접 생성해서 사용하는 것이 아니라
	 *    컨테이너가 제공하는 엔티티 매니저를 사용해야 한다.
	 */
	@PersistenceContext
	EntityManager em;
	
	//회원 엔티티 저장
	public void save(Member member) {
		em.persist(member);
	}
	
	//식별자로 회원 엔티티 조회
	public Member fineOne(Long id) {
		return em.find(Member.class, id);
	}

	//JPQL을 사용해서 모든 회원 엔티티 조회
	public List<Member> findAll() {
		return em
				.createQuery("SELECT m FROM Member m", Member.class)
				.getResultList();
	}
	
	//JPQL을 사용해서 이름으로 회원 엔티티 조회
	public List<Member> findByName(String name) {
		return em
				.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
