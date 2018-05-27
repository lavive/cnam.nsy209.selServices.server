package notification.suscriber.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;

/**
 * Bean to set members to be notified
 * 
 * @author lavive
 *
 */

@Local
public interface SuscriberLocal {
	
	public void update(NotificationEntity notification);
	
	public List<MemberEntity> getMembersToNotify();
	
	public NotificationEntity getNotification();
	
	
	
}
