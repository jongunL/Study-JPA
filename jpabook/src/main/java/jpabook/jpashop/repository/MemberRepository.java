package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;

//JpaRepository<T, ID>
//T	: 리포지토리가 관리하는 엔티티 타입
//ID: 엔티티의 식별자 타입
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	//save(), fineOne(), findAll() 과 같은 기본 메소드는 상속받은 JpaRepository가 모두 제공한다.
	List<Member> findByName(String name);	//스프링 데이터 JPA가 메소드의 이름을 적절히 분석해 쿼리를 생성해준다.
}
