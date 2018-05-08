package notification.suscriber.interceptor.strategy.local;

import javax.ejb.Local;

import notification.suscriber.local.SuscriberLocal;


@Local
public interface SuscriberInterceptorStrategyContext {
	
	public void execute(SuscriberLocal suscriber, String Strategy);

}
