package shared.dto;

import java.io.Serializable;

/**
 * DTO picturing category
 * 
 * @author lavive
 *
 */



public class CategoryDto implements Serializable {

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;

	private String name;

	
	/* getter and setter */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	
	@Override
	public String toString() {
		return getName();
	}


}
