package dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification_topic")
public class NotificationTopicEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "event", nullable = false, length = 50)
	private String topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn (name="person_id")
	private PersonEntity personOriginEvent;
	
	@Column(name = "category", nullable = false, length = 50)
	private String category;

	@Column(name = "active", nullable = false)
	private boolean active;

	/* getter and setter */
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public PersonEntity getPersonOriginEvent() {
		return personOriginEvent;
	}

	public void setPersonOriginEvent(PersonEntity personOriginEvent) {
		this.personOriginEvent = personOriginEvent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}