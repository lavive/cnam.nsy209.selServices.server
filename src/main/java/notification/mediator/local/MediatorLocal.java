package notification.mediator.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.NotificationEntity;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean to send notification to subscribers
 * 
 * @author lavive
 *
 */

@Local
public interface MediatorLocal {
	
	public List<SuscriberLocal> getSuscribers();
	
	public void sendNotification(NotificationEntity notification);
}
