package notification.interceptor;

import java.util.List;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.entity.NotificationTopicEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.constants.EnumTopicNotification;

/**
 * Interceptor to launch notification creation concerning association
 * 
 * @author lavive
 * 
 * note: 
 *  - to fix: does not work correctly
 *  - internationalization has to be added
 *
 */

public class AssociationInterceptorToNotify {

	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;

	@AroundInvoke
	public Object create(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
		
		NotificationEntity notification = createAssociationChange();
		
		return result;
	}
	
	private NotificationEntity createAssociationChange(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.ASSOCIATION_CHANGE.getWording();
		
		String category = "Nothing";
						
		String title = association.getName()+": "+event;
		
		String attributeValues ="";
		String text = "Les modification concernent: "+attributeValues;
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		
		notificationTopicEntity.setTopic(event);
		notificationTopicEntity.setCategory(category);
		notificationTopicEntity.setPersonOriginEvent(association);
		
		notification.setMembersToNotify(memberToNotify);
		notification.setText(text);
		notification.setTitle(title);
		notification.setTopic(notificationTopicEntity);
		
		notificationDao.create(notification);
		
		return notification;
	}

}
