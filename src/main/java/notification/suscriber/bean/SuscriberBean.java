package notification.suscriber.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean to set members to be notified
 * 
 * @author lavive
 *
 */

@Singleton
public class SuscriberBean implements SuscriberLocal {
	
	
	private List<MemberEntity> membersToNotify = new ArrayList<MemberEntity>(); 
	
	
	private NotificationEntity notification;

	@Override
	@TransactionAttribute
//	@Interceptors({ notification.suscriber.interceptor.SuscriberForAppInterceptor.class,
//					notification.suscriber.interceptor.SuscriberForSMSInterceptor.class,
//					notification.suscriber.interceptor.SuscriberForEmailInterceptor.class}) 
	public void update(NotificationEntity notification) {
		this.membersToNotify = notification.getMembersToNotify();
		this.notification = notification;

	}

	@Override
	public List<MemberEntity> getMembersToNotify() {
		return this.membersToNotify;
	}

	@Override
	public NotificationEntity getNotification() {
		return this.notification;
	}

}
