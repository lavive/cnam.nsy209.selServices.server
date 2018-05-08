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
import dao.entity.SupplyDemandEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.constants.EnumTopicNotification;
import notification.factory.local.NotificationSupplyDemandFactoryLocal;

@Singleton
@TransactionAttribute
public class NotificationSupplyDemandFactoryBean implements NotificationSupplyDemandFactoryLocal {
	
	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
	
	private MemberEntity memberOrigin;
	
	@Override
	public void setMemberOrigin(MemberEntity member){
		this.memberOrigin = member;
	}
	
	private SupplyDemandEntity newDemand;
	
	@Override
	public void setNewDemand(SupplyDemandEntity demand){
		this.newDemand = demand;
	}
	
	private SupplyDemandEntity newSupply;
	
	@Override
	public void setNewSupply(SupplyDemandEntity supply){
		this.newSupply = supply;
	}
	
	@Override
	public List<NotificationEntity> create() {
		List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
		
		if(this.newDemand != null){
			NotificationEntity notificationNewDemand = createNewDemand();
		
			notifications.add(notificationNewDemand);
		}
		
		if(this.newSupply != null){
			NotificationEntity notificationNewSupply = createNewSupply();

			notifications.add(notificationNewSupply);
		}
		
		this.newDemand = null;
		this.newSupply = null;
		
		return notifications;
		
	}
	
	private NotificationEntity createNewDemand(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.NEW_DEMAND.getWording();
		
		String category = this.newDemand.getCategory();
		
		String title = association.getName()+": "+event+" - "+category;
		
		String text = this.newDemand.getTitle()+" - "+this.memberOrigin.getForname()+
					" "+this.memberOrigin.getName();
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		
		notificationTopicEntity.setTopic(event);
		notificationTopicEntity.setCategory(category);
		notificationTopicEntity.setPersonOriginEvent(this.memberOrigin);
		
		notification.setMembersToNotify(memberToNotify);
		notification.setText(text);
		notification.setTitle(title);
		notification.setTopic(notificationTopicEntity);
		
		notificationDao.create(notification);
		
		return notification;
	}
	
	private NotificationEntity createNewSupply(){
		NotificationEntity notification = new NotificationEntity();
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		AssociationEntity association = associationDao.getAssociation();
		
		String event = EnumTopicNotification.NEW_SUPPLY.getWording();
		
		String category = this.newSupply.getCategory();
		
		String title = association.getName()+": "+event+" - "+category;
		
		String text = this.newSupply.getTitle()+" - "+this.memberOrigin.getForname()+
					" "+this.memberOrigin.getName();
		
		List<MemberEntity> memberToNotify = memberDao.getAllMembers();
		
		notificationTopicEntity.setTopic(event);
		notificationTopicEntity.setCategory(category);
		notificationTopicEntity.setPersonOriginEvent(this.memberOrigin);
		
		notification.setMembersToNotify(memberToNotify);
		notification.setText(text);
		notification.setTitle(title);
		notification.setTopic(notificationTopicEntity);
		
		notificationDao.create(notification);
		
		return notification;
	}

}
