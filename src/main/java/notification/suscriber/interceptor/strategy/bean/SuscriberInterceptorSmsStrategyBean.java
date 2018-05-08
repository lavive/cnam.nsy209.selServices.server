package notification.suscriber.interceptor.strategy.bean;

import javax.ejb.Singleton;

import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorSmsStrategy;
import notification.suscriber.local.SuscriberLocal;

@Singleton
public class SuscriberInterceptorSmsStrategyBean implements SuscriberInterceptorSmsStrategy {
	

	@Override
	public void execute(SuscriberLocal suscriber) {
		/**
		 * rajouter une condition vérifiant si le membre a renseigné son n° de portable
		 */
		
		//SuscriberUtil.upDateNotificationsMember(suscriber, this.notificationsMemberDao);		

	}

}
