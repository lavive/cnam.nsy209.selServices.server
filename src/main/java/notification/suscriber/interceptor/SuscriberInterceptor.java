package notification.suscriber.interceptor;

import javax.interceptor.InvocationContext;

/**
 * interface representing interceptor to launch sending notification
 * 
 * @author lavive
 * 
 * note: not used for instance
 *
 **/

public interface SuscriberInterceptor {
	
	public Object interceptSuscriber(InvocationContext ic) throws Exception;

}
