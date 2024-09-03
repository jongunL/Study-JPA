package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID")
	private Long id;
	
	private String name;
	
	/*
	 * @ManyToMany
	 * 	- 사용하지 말것
	 * 	- 사용하게 되더라도 조인 테이블이 연관관계의 주인이 될 수 없음 따라서 관련된 두 테이블 중 하나를 연관관계의 주인으로 정해야 함
	 *    두 엔티티 중 하나에 mappedBy가 설정되었다 하더라도 그게 연관관계의 주인이 아님을 의미하지는 않음 즉 @ManyToMany는 특수한 관계로
	 *    두 엔티티 모두 연관관계의 주인이 되지는 않지만 조인 테이블에 연관관계의 주인을 설정할 수 없으므로 두 엔티티 중 하나에 임의로 설정할 뿐임
	 */
	@ManyToMany
	@JoinTable(
		name = "CATEGORY_ITEM",
		joinColumns = @JoinColumn(name = "CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
	)
	private List<Item> items = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();
	
	//연관관계 메소드
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
	
}
