package notification.suscriber.interceptor.strategy.local;

import javax.ejb.Local;

import notification.suscriber.local.SuscriberLocal;

/**
 * Bean sending notification according to a strategy
 * 
 * @author lavive
 * 
 * note: not used for instance
 *
 **/


@Local
public interface SuscriberInterceptorStrategyContext {
	
	public void execute(SuscriberLocal suscriber, String Strategy);

}
