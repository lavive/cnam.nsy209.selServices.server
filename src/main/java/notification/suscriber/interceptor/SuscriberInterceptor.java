package notification.suscriber.interceptor;

import javax.interceptor.InvocationContext;

public interface SuscriberInterceptor {
	
	public Object interceptSuscriber(InvocationContext ic) throws Exception;

}
