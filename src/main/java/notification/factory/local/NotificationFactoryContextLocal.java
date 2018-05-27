package notification.factory.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.NotificationEntity;

/**
 * Bean to create notifications according a notification factory
 * 
 * @author lavive
 *
 */

@Local
public interface NotificationFactoryContextLocal {	
	
	public List<NotificationEntity> create(NotificationFactory notificationFactory);

}
