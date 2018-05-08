package notification.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import dao.entity.SelServicesEntity;
import dao.local.CommonDao;
import notification.factory.local.NotificationFactoryContextLocal;


public class InterceptorToNotify{
	
	@EJB
	private NotificationFactoryContextLocal notificationContext;
	
//	@EJB
//	private NotificationDaoLocal notificationDao;
	
	@AroundInvoke
	public Object notify(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
				
		//notificationDao.create(notificationContext.create(((CommonDao<SelServicesEntity>) ic.getTarget()).getNotificationFactory()));
		notificationContext.create(((CommonDao<SelServicesEntity>) ic.getTarget()).getNotificationFactory());
		return result;
	}

}
