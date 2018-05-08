//package dao.bean;
//
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.JoinType;
//import javax.persistence.criteria.Root;
//
//import dao.entity.MemberEntity;
//import dao.entity.MemberJoinNotificationsEntity;
//import dao.entity.NotificationEntity;
//import dao.local.MemberDaoLocal;
//import dao.local.MemberJoinNotificationsDaoLocal;
//import dao.local.NotificationDaoLocal;
//
//@Stateless
//@TransactionAttribute
//public class MemberJoinNotificationsDaoBean implements MemberJoinNotificationsDaoLocal {
//	
//	@EJB
//	private MemberDaoLocal memberDao;
//	
//	@EJB
//	private NotificationDaoLocal notificationDao;
//		
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	public void create(MemberJoinNotificationsEntity entity) {
//		this.entityManager.persist(entity);
//		this.entityManager.flush();
//		
//	}
//
//	public MemberJoinNotificationsEntity get(long id) {
//		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<MemberJoinNotificationsEntity> query = builder.createQuery(MemberJoinNotificationsEntity.class);
//		Root<MemberJoinNotificationsEntity> notificationsMember = query.from(MemberJoinNotificationsEntity.class);
//		
//		query.select(notificationsMember).where(builder.equal(notificationsMember.get("id"), (int) id));		
//				
//		return this.entityManager.createQuery(query).getSingleResult();
//	}
//
//	public void update(MemberJoinNotificationsEntity entity) {
//
//		this.entityManager.merge(entity);
//		
//	}
//
//	public void delete(long id) {
//		
//		this.entityManager.remove(this.entityManager.merge(get(id)));
//		
//	}
//
//	public List<MemberJoinNotificationsEntity> getAllNotificationsMember() {
//		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<MemberJoinNotificationsEntity> query = builder.createQuery(MemberJoinNotificationsEntity.class);
//		Root<MemberJoinNotificationsEntity> notificationsMember = query.from(MemberJoinNotificationsEntity.class);
//		
//		query.select(notificationsMember);
//				
//		return this.entityManager.createQuery(query).getResultList();
//		
//	}
//
//	public void initialise() {
//		List<MemberJoinNotificationsEntity> notificationsMembers = getAllNotificationsMember();
//		List<MemberEntity> members = memberDao.getAllMembers();
//		for(MemberEntity member:members){
//			MemberJoinNotificationsEntity notificationsMember = isInside(member,notificationsMembers);
//			if(notificationsMember != null){
//				notificationsMember.setNotifications(null);
//				update(notificationsMember);
//			}
//			else{
//				notificationsMember = new MemberJoinNotificationsEntity();
//				notificationsMember.setMember(member);
//				create(notificationsMember);
//			}
//		}
//		
//	}
//
//	public List<NotificationEntity> getNotifications(long memberId) {
//		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<MemberJoinNotificationsEntity> query = builder.createQuery(MemberJoinNotificationsEntity.class);
//		Root<MemberJoinNotificationsEntity> notificationsMember = query.from(MemberJoinNotificationsEntity.class);
//		Join<MemberJoinNotificationsEntity,MemberEntity> notificationsMemberMember = notificationsMember.join("member",JoinType.LEFT);
//		
//		query.select(notificationsMember).where(builder.equal(notificationsMemberMember.get("id"), (int) memberId));
//		
//		return this.entityManager.createQuery(query).getSingleResult().getNotifications();
//		
//	}
//
//	public void deleteNotification(long memberId, long notificationId) {
//		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<MemberJoinNotificationsEntity> query = builder.createQuery(MemberJoinNotificationsEntity.class);
//		Root<MemberJoinNotificationsEntity> notificationsMember = query.from(MemberJoinNotificationsEntity.class);
//		Join<MemberJoinNotificationsEntity,MemberEntity> notificationsMemberMember = notificationsMember.join("member",JoinType.LEFT);
//		
//		query.select(notificationsMember).where(builder.equal(notificationsMemberMember.get("id"), (int) memberId));
//		
//		MemberJoinNotificationsEntity notificationsMemberEntity = this.entityManager.createQuery(query).getSingleResult();
//		
//		notificationsMemberEntity.getNotifications().remove(notificationDao.get(notificationId));
//		
//		this.entityManager.merge(notificationsMemberEntity);
//		
//	}
//	
//	/* helper method */
//	
//	
//	private MemberJoinNotificationsEntity isInside(MemberEntity member, List<MemberJoinNotificationsEntity> notificationsMembers){
//		
//		if(notificationsMembers != null){
//			for(MemberJoinNotificationsEntity notificationsMember:notificationsMembers){
//				if(member.getId().equals(notificationsMember.getMember().getId()))
//						return notificationsMember;
//			}
//		}
//		return null;
//	}
//
//}
