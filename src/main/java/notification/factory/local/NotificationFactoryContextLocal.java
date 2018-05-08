package notification.factory.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.NotificationEntity;

@Local
public interface NotificationFactoryContextLocal {	
	
	public List<NotificationEntity> create(NotificationFactory notificationFactory);

}
