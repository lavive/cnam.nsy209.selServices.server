package notification.factory.local;

import javax.ejb.Local;

import dao.entity.MessageEntity;

/**
 * Bean to create notification concerning message
 * 
 * @author lavive
 *
 */

@Local
public interface NotificationMessageFactoryLocal extends NotificationFactory {
	
	public void setMessage(MessageEntity message);

}
