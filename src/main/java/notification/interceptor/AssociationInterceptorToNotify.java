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

public class AssociationInterceptorToNotify {

	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
		
//	private Map<EnumAssociationAttribute,String> mapAttributeValue;
//	
//	public void setMapAttributeValue(Map<EnumAssociationAttribute,String> mapAttributeValue){
//		this.mapAttributeValue = mapAttributeValue;
//	}

	@AroundInvoke
	public Object create(InvocationContext ic) throws Exception {
		Object result = ic.proceed();
		
		//List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
		
		NotificationEntity notification = createAssociationChange();
		
		//notifications.add(notification);
		
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
//		for(EnumAssociationAttribute attribute:this.mapAttributeValue.keySet()){
//			attributeValues += attribute.getWording()+": "+this.mapAttributeValue.get(attribute)+"; ";
//		}
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
