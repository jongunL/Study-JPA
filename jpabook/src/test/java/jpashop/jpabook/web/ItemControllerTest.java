package jpashop.jpabook.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
 * @Runwith + @ContextConfiguration(전통적인 테스트 방법) VS @SpringBootTest
 * 	- 전통적인 테스트 방법
 * 		- 테스트 환경을 보다 세밀하게 설정할 수 있지만 설정이 복잡질 수 있다.
 * 	- 스프링 부트 테스트
 * 		- Spring Boot의 특성을 활용해 간단하게 통합 테스트를 설정하고 실행할 수 있도록 설계됨.
 * 	- 사용 목적에 따라 적절한 어노테이션을 선택하면 된다. 도메인이나 비지니스 로직을 포함한 애플리케이션 전체를 테스트할 떄는 스프링 부트 테스트를
 *    HTTP 연결 테스트와 같이 특정 계층만을 테스트할 때는 불필요한 컨텍스트 로드를 피하기 위한 어노테이션을 사용하는 것이 더 효율적이다.
 * 
 * HTTP 테스트의 필요성
 * 	- HTTP 요청 테스트와 같은 경우에는 전체 애플리케이션 컨텍스트를 로드하는 것이 과할 수 있어 더 효율적인 테스트를 위한(MVC 계층만을 테스트 하기 위한) 
 *    @SpringBootTest와 @AutoConfigureMockMvc 또는 @WebMvcTest와 같은 더 세분화된 어노테이션을 사용해 테스트 실행속도 및 불필요한 설정을 줄일 수 있다.
 */

/*
 * @SpringBootTest	
 * 	- 스프링 부트 애플리케이션의 전체 컨텍스트를 로드하여 통합 테스트를 수행할 수 있게 해주는 어노테이션으로 테스트 실행 시 모든 빈을 로드하고 실제 애플리케이션과 유사한 환경을 구성한다.
 * 
 * @AutoConfigureMockMvc
 * 	- 'MockMvc'를 자동으로 구성하여 MVC 테스트를 쉽게 할 수 있도록 도와준다.
 * 	- 이 어노테이션은 단독으로 수행되지 않으며, 애플리케이션 컨텍스트가 필요하다.
 * 	  (이 컨텍스트는 일반적으로 @SpringBootTest에 의해 로드된다.)
 *  - 만약 전통적인 Spring 설정을 사용하면서 'MockMvc'를 활용하려면 이 어노테이션 대신 수동으로 MockMvc를 설정해야 한다.
 *    (@ContextConfiguration) 및 @Runwith 와는 호환되지 않는다. 그 이유는 @AutoConfigureMockMvc가 스프링 부트 환경에서 제공하는 자동 설정 기능에 의존하기 때문이다.)
 *    
 * @WebAppConfiguration
 * 	- Spring의 WebApplicationcontext를 설정하며, 테스트가 웹 애플리케이션 컨텍스트와 통합되도록 보장한다.
 *    이는 웹 애플리케이션 관련 컴포넌트가 제대로 설정되도록 한다.(MockMvc 등)
 */
//@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webAppConfig.xml", "classpath:appConfig.xml"})
@WebAppConfiguration
public class ItemControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void 주문폼연결확인() throws Exception {
		mockMvc
			.perform(MockMvcRequestBuilders.get("/items"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}





























