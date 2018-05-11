package dao.bean;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import dao.entity.MessageEntity;
import dao.entity.PersonEntity;
import dao.local.MessageDaoLocal;
import notification.factory.local.NotificationFactory;
import notification.factory.local.NotificationMessageFactoryLocal;

@Stateless
@TransactionAttribute
public class MessageDaoBean implements MessageDaoLocal {
	
	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private NotificationMessageFactoryLocal notificationFactory;
	
//	@EJB
//	private MemberDaoLocal memberDao;

	//@Interceptors({ notification.interceptor.InterceptorToNotify.class}) 
	public void create(MessageEntity entity) {

		this.notificationFactory.setMessage(entity);
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.persist(entity);
		this.entityManager.flush();
		//this.notificationFactory.setMessage(entity);
		this.notificationFactory.create();

	}

	public MessageEntity get(long id) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MessageEntity> query = builder.createQuery(MessageEntity.class);
		Root<MessageEntity> message = query.from(MessageEntity.class);
		
		query.select(message).where(builder.equal(message.get("id"), (int) id));		
				
		/*MessageEntity messageEntity =*/ return this.entityManager.createQuery(query).getSingleResult();
	}

	//@Interceptors({ notification.interceptor.InterceptorToNotify.class}) 
	public void update(MessageEntity entity) {
		this.notificationFactory.setMessage(entity);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		entity.setActive(true);
		this.entityManager.merge(entity);
		this.notificationFactory.create();

	}

	public void delete(long id) {
		MessageEntity entity = get(id);
		entity.setActive(false);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(entity);

	}

//	public List<MessageEntity> getMessageByState(boolean state) {
//		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<MessageEntity> query = builder.createQuery(MessageEntity.class);
//		Root<MessageEntity> message = query.from(MessageEntity.class);
//		
//		query.select(message).where(builder.equal(message.get("state"), state));		
//				
//		return this.entityManager.createQuery(query).getResultList();
//	}

	public List<MessageEntity> getMessages() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MessageEntity> query = builder.createQuery(MessageEntity.class);
		Root<MessageEntity> message = query.from(MessageEntity.class);
		
		query.select(message).where(builder.equal(message.get("active"), true));
		query.orderBy(builder.desc(message.get("dateLastUpdate")));
				
		return this.entityManager.createQuery(query).getResultList();
	}

	public List<MessageEntity> getMessages(PersonEntity personEntity) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MessageEntity> query = builder.createQuery(MessageEntity.class);
		Root<MessageEntity> message = query.from(MessageEntity.class);
		Join<MessageEntity,PersonEntity> messagePerson = message.join("transmitterPerson",JoinType.LEFT);
		
		query.select(message).where(builder.and(builder.equal(messagePerson.get("id"), personEntity.getId()),
				builder.equal(messagePerson.get("active"), true)));
		
		return this.entityManager.createQuery(query).getResultList();
	}

	public void deleteMessage() {
		for(MessageEntity messageEntity:getMessages()){
			delete(messageEntity.getId());
		}
		
	}

	public void deleteMessages(List<MessageEntity> messageEntities) {
		for(MessageEntity messageEntity:messageEntities){
			delete(messageEntity.getId());
		}
		
	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<MessageEntity> query = builder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = query.from(MessageEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<MessageEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			MessageEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}

	@Override
	public NotificationFactory getNotificationFactory() {

		return this.notificationFactory;
	}
}
