package notification.factory.local;

import javax.ejb.Local;

import dao.entity.MemberEntity;

/**
 * Bean to create notification concerning association
 * 
 * @author lavive
 *
 */

@Local
public interface NotificationMemberFactoryLocal extends NotificationFactory {
	
	public void setNewMember(MemberEntity member);
	public void setMemberLeaving(MemberEntity member);

}
