package notification.factory.local;

import java.util.List;

import dao.entity.NotificationEntity;


public interface NotificationFactory {
	
	public List<NotificationEntity> create();

}
