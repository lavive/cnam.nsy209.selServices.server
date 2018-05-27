package notification.suscriber.interceptor.strategy.bean;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorAppStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorEmailStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorSmsStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorStrategy;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorStrategyContext;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean sending notification according to a strategy
 * 
 * @author lavive
 * 
 * note: not used for instance
 *
 **/

@Singleton
public class SuscriberInterceptorStrategyContextBean implements SuscriberInterceptorStrategyContext {
	
	@EJB
	private SuscriberInterceptorAppStrategy suscriberInterceptorAppStrategy;
	
	@EJB
	private SuscriberInterceptorEmailStrategy suscriberInterceptorEmailStrategy;
	
	@EJB
	private SuscriberInterceptorSmsStrategy suscriberInterceptorSmsStrategy;
	
	
	private Map<String,SuscriberInterceptorStrategy> mapSuscriberInterceptorStrategy
			= new HashMap<String,SuscriberInterceptorStrategy>();
	
	private SuscriberInterceptorStrategyContextBean(){
		this.mapSuscriberInterceptorStrategy.put(EnumSuscriberInterceptorStrategy.APP_LONG_POLLING_STRATEGY.getWording(),
				suscriberInterceptorAppStrategy);
		this.mapSuscriberInterceptorStrategy.put(EnumSuscriberInterceptorStrategy.EMAIL_JAVAMAIL_STRATEGY.getWording(),
				suscriberInterceptorEmailStrategy);
		this.mapSuscriberInterceptorStrategy.put(EnumSuscriberInterceptorStrategy.SMS_LONG_POLLING_STRATEGY.getWording(),
				suscriberInterceptorSmsStrategy);
	}
	
	@Override
	public void execute(SuscriberLocal suscriber, String Strategy) {
		getSuscriberInterceptorStrategy(Strategy).execute(suscriber);
		
	}
	
	
	private SuscriberInterceptorStrategy getSuscriberInterceptorStrategy(String key){
		return mapSuscriberInterceptorStrategy.get(key);
	}

}
