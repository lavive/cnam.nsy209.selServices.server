package notification.factory.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.constants.EnumAssociationAttribute;
import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.entity.NotificationTopicEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.constants.EnumTopicNotification;
import notification.factory.local.NotificationAssociationFactoryLocal;

@Singleton
@TransactionAttribute
public class NotificationAssociationFactoryBean implements NotificationAssociationFactoryLocal {

	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
		
	private Map<EnumAssociationAttribute,String> mapAttributeValue;
	
	@Override
	public void setMapAttributeValue(Map<EnumAssociationAttribute,String> mapAttributeValue){
		this.mapAttributeValue = mapAttributeValue;
	}

	@Override
	public List<NotificationEntity> create() {
		List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
		
		NotificationEntity notification = createAssociationChange();
		
		notifications.add(notification);
		
		return notifications;
	}
	
	private NotificationEntity createAssociationChange(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.ASSOCIATION_CHANGE.getWording();
		
		String category = "Nothing";
						
		String title = association.getName()+": "+event;
		
		String attributeValues ="";
		for(EnumAssociationAttribute attribute:this.mapAttributeValue.keySet()){
			attributeValues += attribute.getWording()+": "+this.mapAttributeValue.get(attribute)+"; ";
		}
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
