package shared.dto;

import java.io.Serializable;

/**
 * DTO picturing supply and demand
 * 
 * @author lavive
 *
 */

public class SupplyDemandDto implements Serializable{

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String type;

	private String category;

	private String title;
	
	private MemberDto member;


	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}
	
	@Override
	public String toString() {
		return getType()+" "+getCategory()+" "+getTitle();
	}
}
