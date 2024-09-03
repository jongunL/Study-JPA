package jpabook.jpashop.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;

//스프링 MVC는 HTTP 요청 정보와 요청-응답 애노테이션의 속성 값을 비교해서 실행할 메서드를 찾는다.
@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired ItemService itemService;
	
	//상품 목록
	//	- /items URL을 HTTP GET 방식으로 요청하는 경우 실행되는 메서드이다.
	@GetMapping
	public String list(Model model) {
		List<Item> items = itemService.findItems();	//서비스 계층에서 상품 목록 조회
		model.addAttribute("items", items);			//조회한 상품을 뷰에 전달하기 위해 스프링 MVC가 제공하는 모델 객체에 담는다.
		return "items/itemList";			//실행할 뷰 이름을 반환한다.
	}
	
	//상품 등록 폼
	//	- 첫 화면에서 상품 등록을 선택하면 /items/new URL을 HTTP GET 방식으로 요청한다.
	@GetMapping("/new")
	public String createForm() {
		
		//이 메서드는 단순히 items/createItemForm 문자를 반환하는데
		//webAppConfig.xml에 등록된 스프링 MVC의 뷰 리졸버는 이 정보를 바탕으로 실행할 뷰를 찾는다.
		return "items/createItemForm";
	}
	
	//상품 등록
	//	- 상품 등록 폼에서 데이터를 입력하고 Submit 버튼을 클릭해 /items/new를 HTTP POST 방식으로 요청한 경우 실행되는 메서드이다.
	@PostMapping("/new")
	public String create(Book item) {
		//이 때 파라미터로 전달한 item에는 화면에서 입력한 데이터가 모두 바인딩되어 있다.
		//바인딩은 HttpServletRequest의 파라미터와 객체의 프로퍼티 이름을 비교해서 같으면 스프링 프레임워크가 값을 바인딩해준다.
		itemService.saveItem(item);	//상품 서비스에 상품을 저장하고
		return "redirect:/items";	//저장이 끝나면 상품 목록 화면으로 리다이렉트한다.
	}
	
	//상품 수정 폼
	@GetMapping("/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Item item = itemService.fineOne(itemId);
		model.addAttribute("item", item);
		return "items/updateItemForm";
	}
	
	//상품 수정
	@PostMapping("{itemId}/edit")
	public String updateItem(@ModelAttribute("item") Book item) {
		//이 떄 컨트롤러로 넘어온 item 엔티티 인스턴스는 현재 준영속 상태이므로
		//영속성 컨텍스트의 지원을 받을 수 없어 변경 감지 기능이 동작되지 않는다.
		//이런 준영속 엔티티를 수정하기 위한 방법은 두 가지가 있다.
		//1. 변경 감지 기능 사용
		//	- 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
		//	- Service 계층의 트랜잭션 내에서 준영속 엔티티의 식별자로 엔티티를 다시 조회하면 영속 상태의 엔티티를 변경할 수 있는데,
		//	    이 엔티티는 트랜잭션이 커밋될 때 변경 감지 기능이 동작해서 DB에 수정사항이 반영된다.
		//2. 병합 사용
		//	- 1번 방식과 거의 비슷하게 동작한다.
		//	- 파라미터로 넘긴 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한 뒤 영속 엔티티의 값을 준영속 엔티티의 값으로 채워 넣는다.
		//3. 1번과 2번의 차이점
		//	- 1번 방식은 원하는 속성만 선택해서 변경할 수 있지만 2번 방식은 모든 속성이 변경된다.
		
		//여기서는 2번 방식이 사용됐다.
		itemService.saveItem(item);	//준영속 상태의 item엔티티를 상품 서비스에 전달(상품 서비스는 트랜잭션을 시작하고 상품 리포지토리에 저장을 요청한다.)
		
		return "redirect:/items";
	}
	
}


























