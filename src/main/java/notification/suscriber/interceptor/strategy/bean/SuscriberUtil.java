package notification.suscriber.interceptor.strategy.bean;

public class SuscriberUtil {	
	
//	
//	public static void upDateNotificationsMember
//			(SuscriberLocal suscriber,NotificationsMemberDao notificationsMemberDao){
//		
//		List<NotificationsMemberEntity> notificationsMembers = notificationsMemberDao.getAllNotificationsMember();
//		if(notificationsMembers == null){
//			notificationsMemberDao.initialise();
//			notificationsMembers = notificationsMemberDao.getAllNotificationsMember();
//		}
//		
//		for(MemberEntity member:suscriber.getMembersToNotify()){
//			NotificationsMemberEntity notificationsMember= isInside(member,notificationsMembers);
//			if(notificationsMember != null){
//				if(notificationsMember.getNotifications() != null){
//					notificationsMember.getNotifications().add(suscriber.getNotification());
//				}
//				else{
//					List<NotificationEntity> newNotifications = new ArrayList<NotificationEntity>();
//					newNotifications.add(suscriber.getNotification());
//					notificationsMember.setNotifications(newNotifications);
//				}
//				notificationsMemberDao.update(notificationsMember);
//			}else{
//				notificationsMember = new NotificationsMemberEntity();
//				List<NotificationEntity> newNotifications = new ArrayList<NotificationEntity>();
//				newNotifications.add(suscriber.getNotification());
//				notificationsMember.setNotifications(newNotifications);
//				notificationsMember.setMember(member);
//				notificationsMemberDao.create(notificationsMember);
//			}
//			
//		}
//	}
//	
//	private static NotificationsMemberEntity isInside(MemberEntity member, List<NotificationsMemberEntity> notificationsMembers){
//		
//		if(notificationsMembers != null){
//			for(NotificationsMemberEntity notificationsMember:notificationsMembers){
//				if(member.getId().equals(notificationsMember.getMember().getId()))
//						return notificationsMember;
//			}
//		}
//		return null;
//	}

}
