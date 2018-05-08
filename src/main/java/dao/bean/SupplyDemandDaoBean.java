package dao.bean;

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

import dao.constants.EnumSupplyDemand;
import dao.entity.MemberEntity;
import dao.entity.SupplyDemandEntity;
import dao.local.MemberDaoLocal;
import dao.local.SupplyDemandDaoLocal;
import notification.factory.local.NotificationFactory;
import notification.factory.local.NotificationSupplyDemandFactoryLocal;

@Stateless
@TransactionAttribute
public class SupplyDemandDaoBean implements SupplyDemandDaoLocal {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private NotificationSupplyDemandFactoryLocal notificationFactory;

	//@Interceptors({ notification.interceptor.InterceptorToNotify.class}) 
	public void create(SupplyDemandEntity entity) {
		
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		
		this.entityManager.persist(entity);		
		this.entityManager.flush();
		
		/* update member */
		MemberEntity member = memberDao.get(entity.getMember().getId());
		member.getSupplyDemand().add(entity);
		memberDao.update(member);
		
		if(entity.getType().equals(EnumSupplyDemand.DEMAND.getWording())) {
			this.notificationFactory.setNewDemand(entity);
		} else if(entity.getType().equals(EnumSupplyDemand.SUPPLY.getWording())) {
			this.notificationFactory.setNewSupply(entity);
		}
		this.notificationFactory.setMemberOrigin(member);
		this.notificationFactory.create();
	}

	public SupplyDemandEntity get(long id) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<SupplyDemandEntity> query = builder.createQuery(SupplyDemandEntity.class);
		Root<SupplyDemandEntity> supplyDemand = query.from(SupplyDemandEntity.class);
		
		query.select(supplyDemand).where(builder.equal(supplyDemand.get("id"), (int) id));		
				
		return this.entityManager.createQuery(query).getSingleResult();
	}

	//@Interceptors({ notification.interceptor.InterceptorToNotify.class}) 
	public void update(SupplyDemandEntity entity) {
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		
		this.entityManager.merge(entity);		

		
		if(entity.getType().equals(EnumSupplyDemand.DEMAND.getWording())) {
			this.notificationFactory.setNewDemand(entity);
		} else if(entity.getType().equals(EnumSupplyDemand.SUPPLY.getWording())) {
			this.notificationFactory.setNewSupply(entity);
		}
		MemberEntity member = memberDao.get(entity.getMember().getId());
		this.notificationFactory.setMemberOrigin(member);
		this.notificationFactory.create();

	}

	public void delete(long id) {
		SupplyDemandEntity entity = get(id);
		entity.setActive(false);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(entity);

	}

	public List<SupplyDemandEntity> getAllSupplyDemand() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<SupplyDemandEntity> query = builder.createQuery(SupplyDemandEntity.class);
		Root<SupplyDemandEntity> supplyDemand = query.from(SupplyDemandEntity.class);
		
		query.select(supplyDemand).where(builder.equal(supplyDemand.get("active"), true));
		query.orderBy(builder.asc(supplyDemand.get("type")),builder.asc(supplyDemand.get("category")),builder.asc(supplyDemand.get("title")));
				
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<SupplyDemandEntity> getSuppliesDemands(String type) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<SupplyDemandEntity> query = builder.createQuery(SupplyDemandEntity.class);
		Root<SupplyDemandEntity> supplyDemand = query.from(SupplyDemandEntity.class);
		
		query.select(supplyDemand).where(builder.and(
				builder.equal(
						builder.upper(builder.trim(supplyDemand.get("type"))),
						builder.upper(builder.trim(builder.literal(type))))),
				builder.equal(supplyDemand.get("active"), true));
		query.orderBy(builder.asc(supplyDemand.get("category")),builder.asc(supplyDemand.get("title")));
			
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<SupplyDemandEntity> query = builder.createQuery(SupplyDemandEntity.class);
		Root<SupplyDemandEntity> root = query.from(SupplyDemandEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<SupplyDemandEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			SupplyDemandEntity entity = entities.get(0);		
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
