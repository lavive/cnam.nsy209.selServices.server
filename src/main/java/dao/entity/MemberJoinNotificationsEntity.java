//package dao.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "notifications_member")
//public class MemberJoinNotificationsEntity implements SelServicesEntity{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id")
//	private Long id;
//	
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "member_id")
//	private MemberEntity member;
//	
////	@OneToMany(fetch = FetchType.EAGER)
////	@JoinColumn(name = "member_notifications_id")
//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name = "memberNotifications_notification",joinColumns = @JoinColumn(name = "memberNotifications_id"),
//              inverseJoinColumns = @JoinColumn(name = "notification_id"))
//	private List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
//	
//
//	/* getters and setters */
//	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public MemberEntity getMember() {
//		return member;
//	}
//
//	public void setMember(MemberEntity member) {
//		this.member = member;
//	}
//
//	public List<NotificationEntity> getNotifications() {
//		return notifications;
//	}
//
//	public void setNotifications(List<NotificationEntity> notifications) {
//		this.notifications = notifications;
//	}
//	
//	
//}
