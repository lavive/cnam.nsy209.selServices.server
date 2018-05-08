package notification.suscriber.interceptor.strategy.local;

import notification.suscriber.local.SuscriberLocal;

public interface SuscriberInterceptorStrategy {
	
	public void execute(SuscriberLocal suscriber);

}
