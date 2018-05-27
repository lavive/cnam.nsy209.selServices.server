package notification.factory.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.entity.NotificationEntity;
import notification.factory.local.NotificationFactory;
import notification.factory.local.NotificationFactoryContextLocal;
import notification.mediator.local.MediatorLocal;

/**
 * Bean to create notifications according a notification factory
 * 
 * @author lavive
 *
 */

@Singleton
@TransactionAttribute
public class NotificationFactoryContextBean implements NotificationFactoryContextLocal {
	
	@EJB
	private MediatorLocal mediator;

	/* create notification with the notification factory strategy */
	@Override
	public List<NotificationEntity> create(NotificationFactory notificationFactory) {
		List<NotificationEntity> notifications = notificationFactory.create();
		/* the mediator sends notification */
		for(NotificationEntity notification:notifications){
			mediator.sendNotification(notification);
		}
		return notifications;
	}

}
