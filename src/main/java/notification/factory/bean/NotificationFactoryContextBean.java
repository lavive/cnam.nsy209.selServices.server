package notification.factory.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.entity.NotificationEntity;
import notification.factory.local.NotificationFactory;
import notification.factory.local.NotificationFactoryContextLocal;
import notification.mediator.local.MediatorLocal;

@Singleton
@TransactionAttribute
public class NotificationFactoryContextBean implements NotificationFactoryContextLocal {
	
	@EJB
	private MediatorLocal mediator;

	@Override
	public List<NotificationEntity> create(NotificationFactory notificationFactory) {
		List<NotificationEntity> notifications = notificationFactory.create();
		for(NotificationEntity notification:notifications){
			mediator.sendNotification(notification);
		}
		return notifications;
	}

}
