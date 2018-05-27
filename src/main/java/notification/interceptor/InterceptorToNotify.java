package notification.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import dao.entity.SelServicesEntity;
import dao.local.CommonDao;
import notification.factory.local.NotificationFactoryContextLocal;

/**
 * Interceptor to launch notification creation according to a context
 * 
 * @author lavive
 * 
 * note: 
 *  - to fix: does not work correctly
 *  - internationalization has to be added
 *
 */


public class InterceptorToNotify{
	
	@EJB
	private NotificationFactoryContextLocal notificationContext;
	
	@AroundInvoke
	public Object notify(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
				
		notificationContext.create(((CommonDao<SelServicesEntity>) ic.getTarget()).getNotificationFactory());
		return result;
	}

}
