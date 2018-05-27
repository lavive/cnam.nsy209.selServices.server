package dao.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.local.MemberDaoLocal;
import dao.local.NotificationDaoLocal;
import notification.factory.local.NotificationFactory;

/**
 * Bean to manage NotificationEntity persistance
 * 
 * @author lavive
 *
 */

@Stateless
@TransactionAttribute
public class NotificationDaoBean implements NotificationDaoLocal {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private MemberDaoLocal memberDao;

	public void create(NotificationEntity entity) {
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		entity.getTopic().setActive(true);
		this.entityManager.persist(entity);
		this.entityManager.flush();
		
		if(!entity.getMembersToNotify().isEmpty()) {
			for(MemberEntity member: entity.getMembersToNotify()) {
				MemberEntity memberUpdated = memberDao.get(member.getId());
				memberUpdated.getNotifications().add(entity);
				memberDao.update(memberUpdated);
			}
			
		}

	}

	public NotificationEntity get(long id) {
		/* API Criteria use */
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<NotificationEntity> query = builder.createQuery(NotificationEntity.class);
		Root<NotificationEntity> notification = query.from(NotificationEntity.class);
		
		query.select(notification).where(builder.equal(notification.get("id"), (int) id));		
				
		return this.entityManager.createQuery(query).getSingleResult();
		
	}

	public void update(NotificationEntity entity) {
		NotificationEntity entityUpdated = getValuesFrom(entity);
		entityUpdated.setActive(true);
		entityUpdated.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(entityUpdated);
		for(MemberEntity member: entityUpdated.getMembersToNotify()) {
			MemberEntity memberUpdated = memberDao.get(member.getId());
			memberUpdated.getNotifications().add(entityUpdated);
			memberDao.update(memberUpdated);
		}

	}

	public void delete(long id) {
		NotificationEntity entity = get(id);
		entity.setActive(false);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		entity.getTopic().setActive(false);
		this.entityManager.merge(entity);

	}

	public List<NotificationEntity> create(List<NotificationEntity> notifications) {
		List<NotificationEntity> notificationsResult = new ArrayList<NotificationEntity>();
		for(NotificationEntity notification:notifications){
			create(notification);
			notificationsResult.add(notification);
		}
		return notificationsResult;
	}

	@Override
	public void addNotificationToMember(long notificationId, long memberId) {
		NotificationEntity notification = get(notificationId);
		MemberEntity member = memberDao.get(memberId);
		notification.getMembersToNotify().add(member);
		update(notification);
		member.getNotifications().add(notification);
		memberDao.update(member);
		
	}

	@Override
	public Date lastDateUpdate() {
		/* API Criteria use */
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<NotificationEntity> query = builder.createQuery(NotificationEntity.class);
		Root<NotificationEntity> root = query.from(NotificationEntity.class);
		
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<NotificationEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			NotificationEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}

	@Override
	public NotificationFactory getNotificationFactory() {
		
		return null;
	}
	
	/* helper method */
	private NotificationEntity getValuesFrom(NotificationEntity notification) {
		NotificationEntity notificationUpdated = get(notification.getId());
		if(!notification.getMembersToNotify().isEmpty()) {
			List<MemberEntity> members = new ArrayList<MemberEntity>();
			for(MemberEntity member:notification.getMembersToNotify()) {
				members.add(memberDao.get(member.getId()));
			}
			notificationUpdated.setMembersToNotify(members);
		}
		if(notification.getText()!=null) {
			notificationUpdated.setText(notification.getText());
		}
		if(notification.getTitle()!=null) {
			notificationUpdated.setTitle(notification.getTitle());
		}
		if(notification.getTopic()!=null) {
			notificationUpdated.setTopic(notification.getTopic());
			notificationUpdated.getTopic().setActive(true);
		}
		
		return notificationUpdated;
	}
	

}
