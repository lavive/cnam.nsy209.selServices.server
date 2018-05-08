package shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotificationDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String title;
	
	private String text;
	
	private NotificationTopicDto topic;
	
	private List<MemberDto> membersToNotify = new ArrayList<MemberDto>();
	
	
	
	/* getters and setters */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NotificationTopicDto getTopic() {
		return topic;
	}

	public void setTopic(NotificationTopicDto topic) {
		this.topic = topic;
	}

	public List<MemberDto> getMembersToNotify() {
		return membersToNotify;
	}

	public void setMembersToNotify(List<MemberDto> membersToNotify) {
		this.membersToNotify = membersToNotify;
	}
	
	@Override
	public String toString() {
		return getTitle()+" "+getText()+" "+getTopic();
	}
	
	

}
