package notification.factory.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;

import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.entity.NotificationTopicEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.constants.EnumTopicNotification;
import notification.factory.local.NotificationMemberFactoryLocal;

@Singleton
@TransactionAttribute
public class NotificationMemberFactoryBean implements NotificationMemberFactoryLocal {
	
	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;

	
	private MemberEntity newMember;
	
	@Override
	public void setNewMember(MemberEntity member){
		this.newMember = member;
	}	
	
	private MemberEntity memberLeaving;
	
	@Override
	public void setMemberLeaving(MemberEntity member){
		this.memberLeaving = member;
	}
	
	@Override
	public List<NotificationEntity> create() {
		List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
		
		if(this.newMember != null){
			NotificationEntity notificationNewMember = createNewMember();
			NotificationEntity notificationWelcome = createWelcome();		
			notifications.add(notificationNewMember);
			notifications.add(notificationWelcome);
		}
		if(this.memberLeaving != null){
			NotificationEntity notificationMemberLeaving = createMemberLeaving();
			notifications.add(notificationMemberLeaving);
		}
		this.newMember = null;
		this.memberLeaving = null;
		
		return notifications;
	}
	
	/* helper method */
	
	private NotificationEntity createNewMember(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.NEW_MEMBER.getWording();
		
		String title = association.getName()+": "+event+" ("+
						this.newMember.getForname()+" "+this.newMember.getName()+")";
		
		String text = "Nous souhaitons la bienvenue parmis nous à "+
				this.newMember.getForname()+" "+this.newMember.getName();
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		memberToNotify.remove(this.newMember);
		
		String category = "Nothing";
		
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
	
	private NotificationEntity createMemberLeaving(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.MEMBER_LEAVING.getWording();
		
		String title = association.getName()+": "+event+" ("+
						this.memberLeaving.getForname()+" "+this.memberLeaving.getName()+")";
		
		String text = this.memberLeaving.getForname()+" "+this.memberLeaving.getName()+
						" a quitté notre association ";
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		
		String category = "Nothing";
		
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
	
	private NotificationEntity createWelcome(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.WELCOME_MESSAGE.getWording();
		
		String category = "Nothing";
						
		String title = event+" à "+association.getName();
		
		String text = "Nous vous souhaitons la bienvenue parmi nous";
		
		List<MemberEntity> memberToNotify = new ArrayList<MemberEntity>();
		memberToNotify.add(this.newMember);
		
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
