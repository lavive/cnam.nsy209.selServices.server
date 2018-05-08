package notification.suscriber.interceptor.strategy.bean;

import javax.ejb.Singleton;

import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorAppStrategy;
import notification.suscriber.local.SuscriberLocal;

@Singleton
public class SuscriberInterceptorAppStrategyBean implements SuscriberInterceptorAppStrategy {
	

	@Override
	public void execute(SuscriberLocal suscriber) {
		
		//SuscriberUtil.upDateNotificationsMember(suscriber, this.notificationsMemberDao);

	}

}
