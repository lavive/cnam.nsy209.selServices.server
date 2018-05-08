package notification.suscriber.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import notification.suscriber.interceptor.strategy.bean.EnumSuscriberInterceptorStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorStrategyContext;
import notification.suscriber.local.SuscriberLocal;


public class SuscriberForEmailInterceptor implements SuscriberInterceptor{
	
	@EJB
	SuscriberInterceptorStrategyContext suscriberInterceptorStrategyContext;
	
	@Override
	@AroundInvoke
	public Object interceptSuscriber(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
		
		SuscriberLocal suscriber = (SuscriberLocal) ic.getTarget();
		
		suscriberInterceptorStrategyContext.execute(suscriber, 
				EnumSuscriberInterceptorStrategy.EMAIL_JAVAMAIL_STRATEGY.getWording());
		
		return result;
		
	}

}

