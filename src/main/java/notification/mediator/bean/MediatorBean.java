package notification.mediator.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import dao.entity.NotificationEntity;
import notification.mediator.local.MediatorLocal;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean to send notification to subscribers
 * 
 * @author lavive
 *
 */

@Singleton
public class MediatorBean implements MediatorLocal {

	private List<SuscriberLocal> suscribers = new ArrayList<SuscriberLocal>();
	
	@EJB
	private SuscriberLocal suscriber;


	@Override
	public List<SuscriberLocal> getSuscribers() {
		this.suscribers.clear();
		this.suscribers.add(this.suscriber);
		return this.suscribers;
	}

	@Override
	public void sendNotification(NotificationEntity notification) {
		for(SuscriberLocal suscriber:getSuscribers()){
			suscriber.update(notification);
		}
		
	}


}
