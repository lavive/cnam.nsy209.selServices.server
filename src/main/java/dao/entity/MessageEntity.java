package dao.entity;

import java.util.Date;

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
@Table(name = "message")
public class MessageEntity implements SelServicesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "message_subject_id")
//	private MessageSubjectEntity subject;

	@Column(name = "text", nullable = false, length = 500)
	private String text;
	
	@Column(name = "title", nullable = false, length = 200)
	private String title;
	
//	@Column(name = "state")
//	private boolean state;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	private PersonEntity transmitterPerson;
	
//	@Column(name = "date", nullable = false)
//	private Date date;

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

//	public MessageSubjectEntity getSubject() {
//		return subject;
//	}
//
//	public void setSubject(MessageSubjectEntity subject) {
//		this.subject = subject;
//	}

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

	public PersonEntity getTransmitterPerson() {
		return transmitterPerson;
	}

	public void setTransmitterPerson(PersonEntity person) {
		this.transmitterPerson = person;
	}

//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}

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