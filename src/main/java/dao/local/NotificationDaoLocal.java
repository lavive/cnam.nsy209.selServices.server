package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.NotificationEntity;

@Local
public interface NotificationDaoLocal extends CommonDao<NotificationEntity> {
	
	public List<NotificationEntity> create(List<NotificationEntity> notifications);
	
	public void addNotificationToMember(long notificationId, long memberId);
	
}