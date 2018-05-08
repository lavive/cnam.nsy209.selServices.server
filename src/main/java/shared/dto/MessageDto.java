package shared.dto;

import java.io.Serializable;



public class MessageDto implements Serializable{
	
	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String title;
	
	private String text;
	
	//private boolean state;
	
	private PersonDto emitterPerson;
	

	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	public boolean isState() {
//		return state;
//	}
//
//	public void setState(boolean state) {
//		this.state = state;
//	}

	public PersonDto getEmitterPerson() {
		return emitterPerson;
	}

	public void setEmitterPerson(PersonDto emitterPerson) {
		this.emitterPerson = emitterPerson;
	}
	

	
	@Override
	public String toString() {
		return getTitle()+" "+getText();
	}

}
