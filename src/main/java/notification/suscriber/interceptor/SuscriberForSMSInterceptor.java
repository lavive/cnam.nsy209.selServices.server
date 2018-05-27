package notification.suscriber.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import notification.suscriber.interceptor.strategy.bean.EnumSuscriberInterceptorStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorStrategyContext;
import notification.suscriber.local.SuscriberLocal;

/**
 * interceptor to launch sending notification by sms
 * 
 * @author lavive
 * 
 * note: not used for instance
 *
 **/


public class SuscriberForSMSInterceptor implements SuscriberInterceptor {
	
	@EJB
	SuscriberInterceptorStrategyContext suscriberInterceptorStrategyContext;
	
	@Override
	@AroundInvoke
	public Object interceptSuscriber(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
		
		SuscriberLocal suscriber = (SuscriberLocal) ic.getTarget();
		
		suscriberInterceptorStrategyContext.execute(suscriber, 
				EnumSuscriberInterceptorStrategy.SMS_LONG_POLLING_STRATEGY.getWording());

		
		return result;
		
	}

}
