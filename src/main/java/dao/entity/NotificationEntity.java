package dao.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class NotificationEntity implements SelServicesEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title", nullable = false, length = 200)
	private String title;
	
	@Column(name = "text", nullable = false, length = 500)
	private String text;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,orphanRemoval = true)
	@JoinColumn(name = "topic_id")
	private NotificationTopicEntity topic;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "notification_member",joinColumns = @JoinColumn(name = "notification_id"),
              inverseJoinColumns = @JoinColumn(name = "member_id"))
	private List<MemberEntity> membersToNotify = new ArrayList<MemberEntity>();

	@Column(name = "active", nullable = false)
	private boolean active;
	
	@Column(name = "date_last_update", nullable = false)
	private Date dateLastUpdate;

	
	/* getter and setter */
	
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

	public NotificationTopicEntity getTopic() {
		return topic;
	}

	public void setTopic(NotificationTopicEntity topic) {
		this.topic = topic;
	}

	public List<MemberEntity> getMembersToNotify() {
		return membersToNotify;
	}

	public void setMembersToNotify(List<MemberEntity> membersToNotify) {
		this.membersToNotify = membersToNotify;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

}
