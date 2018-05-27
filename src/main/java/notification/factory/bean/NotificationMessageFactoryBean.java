package notification.factory.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;
import dao.entity.MessageEntity;
import dao.entity.NotificationEntity;
import dao.entity.NotificationTopicEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.constants.EnumTopicNotification;
import notification.factory.local.NotificationMessageFactoryLocal;

/**
 * Bean to create notification concerning message
 * 
 * @author lavive
 * 
 * note: internazionalization has to be added
 *
 */

@Singleton
@TransactionAttribute
public class NotificationMessageFactoryBean implements NotificationMessageFactoryLocal {

	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
	
	private MessageEntity message;
	
	@Override
	public void setMessage(MessageEntity message){
		this.message = message;
	}
	
	/* create notification when a new message is sended */
	
	@Override
	public List<NotificationEntity> create() {
		List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
		
		NotificationEntity notification = createNewMessage();
		
		notifications.add(notification);
		
		return notifications;
		
		
	}
	
	private NotificationEntity createNewMessage(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.NEW_MESSAGE.getWording();
		
		String category = "Nothing";
		
		String origin = null;
		if(this.message.getTransmitterPerson() instanceof MemberEntity){
			origin = ((MemberEntity) this.message.getTransmitterPerson()).getForname()+
					" "+this.message.getTransmitterPerson().getName();
		}
		else{
			origin = "Le Bureau";
		}
				
		String title = association.getName()+": "+event+" ("+origin+")";
		
		String text = this.message.getText();
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		
		notificationTopicEntity.setTopic(event);
		notificationTopicEntity.setCategory(category);
		notificationTopicEntity.setPersonOriginEvent(this.message.getTransmitterPerson());
		
		notification.setMembersToNotify(memberToNotify);
		notification.setText(text);
		notification.setTitle(title);
		notification.setTopic(notificationTopicEntity);
		
		notificationDao.create(notification);
		
		return notification;
	}

}
