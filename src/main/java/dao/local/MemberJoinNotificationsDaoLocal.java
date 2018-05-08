//package dao.local;
//
//import java.util.List;
//
//import javax.ejb.Local;
//
//import dao.entity.MemberJoinNotificationsEntity;
//import dao.entity.NotificationEntity;
//
//@Local
//public interface MemberJoinNotificationsDaoLocal extends CommonDao<MemberJoinNotificationsEntity> {
//	
//	public List<MemberJoinNotificationsEntity> getAllNotificationsMember();
//	
//	public void initialise();
//	
//	public List<NotificationEntity> getNotifications(long memberId);
//	
//	public void deleteNotification(long memberId, long notificationId);
//
//}