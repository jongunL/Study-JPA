package jpabook.jpashop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

/*
 * @Service
 * 	- <context:component-scan>에 의해 스프링 빈으로 등록된다.
 * 
 * @Transactional
 * 	- 스프링 프레임워크는 이 어노테이션이 붙어있는 클래스나 메소드에 트랜잭션을 적용한다.
 * 	- 외부에서 이 클래스의 메소드를 호출할 때 트랜잭션을 시작하고 메소드를 종료할 떄 트랜잭션을 커밋한다.
 *	- 만약 예외가 발생하면 트랜잭션을 롤백한다. 
 *	(런타임 예외와 그 자식인 언체크 예외만 롤백한다. 만약 체크 예외가 발생해도 롤백하고 싶다면 @Transactional(rollbackFor = Exception.class) 처럼 롤백할 예외를 지정해야 한다.)
 */
@Service
@Transactional
public class MemberService {

	/*
	 * @Autowired
	 * 	- 스프링 컨테이너가 적절한 스프링 빈을 자동-주입 해준다.
	 *	- 필드, 생성자, 접근자를 이용하여 자동-주입 할 수 있다.
	 */
	@Autowired
	MemberRepository memberRepository;
	
	/*
	 * 회원 가입
	 */
	public Long join(Member member) {
		validateDulpicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	/*
	 * 중복 회원 검증
	 * 	- 검증 로직이 있어도 멀티 스레드 상황을 고려해서 회원 테이블의 회원명 컬럼에 유니크 제약 조건을 추가하는 것이 안전하다.
	 */
	private void validateDulpicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
	}
	
	/*
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/*
	 * 
	 */
	public Optional<Member> fineOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
	
}
