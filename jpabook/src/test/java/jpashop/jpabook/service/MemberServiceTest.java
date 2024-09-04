package jpashop.jpabook.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.deprecated.MemberRepository;
import jpabook.jpashop.service.MemberService;

/*
 * 스프링 프레임워크와 테스트 통합
 * 	- @RunWith(SpringJUnit4ClassRunner.class)
 * 		- JUnit으로 작성한 테스트 케이스를 스프링 프레임워크와 통합하려면 해당 어노테이션에 SpringJUnit4ClassRunner.class를 인자로 주면 된다.
 * 		- 이렇게 하면 테스트가 스프링 컨테이너에서 실행되므로 스프링 프레임워크가 제공하는 기능들을 사용할 수 있게 된다. (@Autowired 등)
 * 	- @ContextConfiguration(locations = "classpath:appConfig.xml")
 * 		- 테스트 케이스를 실행할 때 사용할 스프링 설정 정보를 지정한다.
 * 		- 여기서는 웹과 관련된 정보는 필요하지 않으므로 webAppConfig.xml은 지정하지 않았다.
 * 
 * @Transactional
 * 	- 보통 비지니스 로직이 있는 서비스 계층에서 사용하나, 테스트에서 사용하면 동작 방식이 달라진다.
 *	- 테스트에서 사용시 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백한다.
 *	    따라서 테스트를 진행하면서 DB에 저장한 데이터가 테스트가 끝나면 롤백되므로 반복해서 테스트를 진행할 수 있게 된다.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	//회원가입 테스트 케이스
	//	- 회원 엔티티를 생성하고 회원가입을 시도할 때 회원이 정상적으로 저장되는지 검증하기 위함
	@Test
	public void 회원가입() throws Exception {
		//Given, When, Then
		//	- 테스트를 이해하기 쉽게 도와준다.
		//	- 순서대로 테스트할 상황 설정, 테스트 대상 실행, 결과 검증을 의미한다.
		
		//Given
		Member member = new Member();
		member.setName("kim");
		//When
		Long saveId = memberService.join(member);
		//Then
		Assert.assertEquals(member, memberRepository.fineOne(saveId));
	}
	
	//중복 회원 예외처리 테스트
	//	- 이름이 같은 회원은 중복으로 저장되면 안 되고 예외가 발생해야 한다.
	@Test(expected = IllegalStateException.class)			//expected 속성 : 지정된 예외가 발생해야 테스트가 성공한다.
	public void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//When
		memberService.join(member1);
		memberService.join(member2);	//예외가 발생해야 한다.
		
		//Then
		Assert.fail("예외가 발생해야 한다.");
	}
	
}
