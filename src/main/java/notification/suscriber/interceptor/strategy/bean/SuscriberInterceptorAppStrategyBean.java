package notification.suscriber.interceptor.strategy.bean;

import javax.ejb.Singleton;

import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorAppStrategy;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean sending notification by mobile app
 * 
 * @author lavive
 * 
 * note: to implement
 *
 */

@Singleton
public class SuscriberInterceptorAppStrategyBean implements SuscriberInterceptorAppStrategy {
	

	@Override
	public void execute(SuscriberLocal suscriber) {
		
		//SuscriberUtil.upDateNotificationsMember(suscriber, this.notificationsMemberDao);

	}

}
