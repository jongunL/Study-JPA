package jpashop.jpabook.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.deprecated.ItemRepository;
import jpabook.jpashop.service.ItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ItemServiceTest {

	@Autowired ItemRepository itemRepository;
	@Autowired ItemService itemService;
	
	@Test
	public void 상품등록() throws Exception {
		//Given
		Item item = new Book();
		item.setName("인생 책");
		
		//When
		itemService.saveItem(item);
		List<Item> itemList = itemService.findItems();
		
		//Then
		Assert.assertTrue(itemList.size() > 0);
	}
	
	
}
