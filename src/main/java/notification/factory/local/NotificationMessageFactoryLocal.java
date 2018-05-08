package notification.factory.local;

import javax.ejb.Local;

import dao.entity.MessageEntity;

@Local
public interface NotificationMessageFactoryLocal extends NotificationFactory {
	
	public void setMessage(MessageEntity message);

}
