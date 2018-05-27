package notification.suscriber.interceptor.strategy.local;

import notification.suscriber.local.SuscriberLocal;

/**
 * interface representing interceptor subscriber
 * 
 * @author lavive
 * 
 * note: not used for instance
 *
 **/

public interface SuscriberInterceptorStrategy {
	
	public void execute(SuscriberLocal suscriber);

}
