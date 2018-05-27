package notification.factory.local;

import java.util.List;

import dao.entity.NotificationEntity;

/**
 * Interface picturing the different notifications factories
 * 
 * @author lavive
 *
 */


public interface NotificationFactory {
	
	public List<NotificationEntity> create();

}
