package dao.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dao.constants.EnumAssociationAttribute;
import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;
import dao.local.AssociationDaoLocal;
import dao.local.MemberDaoLocal;
import notification.factory.local.NotificationAssociationFactoryLocal;
import notification.factory.local.NotificationFactory;

@Stateless
@TransactionAttribute
public class AssociationDaoBean implements AssociationDaoLocal {
	
	@PersistenceContext(unitName="selServices")
	private EntityManager entityManager;
	
	@EJB
	private MemberDaoLocal memberDao;

	@EJB
	private NotificationAssociationFactoryLocal notificationFactory;
	
	public void create(AssociationEntity entity) {
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.persist(entity);
		this.entityManager.flush();

	}

	public AssociationEntity get(long id) {
		return getAssociation();
		
	}

	//@Interceptors({ notification.interceptor.AssociationInterceptorToNotify.class}) 
	public void update(AssociationEntity entity) {
//		AssociationEntity associationEntity = getAssociation();	
//		if(entity.getId() != associationEntity.getId()){		
//			updateAssociationEntity(associationEntity,entity);
//			this.entityManager.remove(this.entityManager.merge(entity));
//		} 
		
		
		this.notificationFactory.setMapAttributeValue(modifiedAttributes(entity));
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(entity);
		
		this.notificationFactory.create();
		

	}

	public void delete(long id) {
		AssociationEntity association = get(id);
		association.setActive(false);
		association.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(association);

	}

	
	public AssociationEntity getAssociation() {
		AssociationEntity resultEntity = null;
		
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<AssociationEntity> query = builder.createQuery(AssociationEntity.class);
		Root<AssociationEntity> association = query.from(AssociationEntity.class);
		
		query.select(association);
		
		List<AssociationEntity> associations = this.entityManager.createQuery(query).getResultList();
		
		if(!associations.isEmpty()){
			int rank = 0;
			for(AssociationEntity associationEntity:associations){
				if(rank>0) this.entityManager.remove(this.entityManager.merge(associationEntity));
				else resultEntity = associationEntity;
				rank++;
			}
		}
		return resultEntity;
	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AssociationEntity> query = builder.createQuery(AssociationEntity.class);
		
		Root<AssociationEntity> root = query.from(AssociationEntity.class);
		
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<AssociationEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			AssociationEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}

	@Override
	public NotificationFactory getNotificationFactory() {
		
		return this.notificationFactory;
	}

	@Override
	public List<MemberEntity> checkIdAssociation(String login, String password, int number) {
		List<AssociationEntity> resultAssociationEntity = null;
		
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<AssociationEntity> query = builder.createQuery(AssociationEntity.class);
		Root<AssociationEntity> association = query.from(AssociationEntity.class);
		
		query.select(association).where(builder.and(
				builder.equal(
						builder.upper(builder.trim(association.get("password"))),
						builder.upper(builder.trim(builder.literal(login)))),
				builder.equal(
						builder.upper(builder.trim(association.get("password"))),
						builder.upper(builder.trim(builder.literal(password)))),
				builder.equal(association.get("active"),true)));
		resultAssociationEntity = this.entityManager.createQuery(query).getResultList();
		
		List<MemberEntity> resultEntity = null;
		if(!resultAssociationEntity.isEmpty())
			resultEntity = memberDao.getListLastMember(number);
		
		return resultEntity;
	}
	
	
	/* helper methods */
//	private void updateAssociationEntity(AssociationEntity target, AssociationEntity toCopy){
//
//		target.setName(toCopy.getName());
//		target.setAddress(toCopy.getAddress());
//		target.setPostalCode(toCopy.getPostalCode());
//		target.setTown(toCopy.getTown());
//		target.setEmail(toCopy.getEmail());
//		target.setCellNumber(toCopy.getCellNumber());
//		target.setPhoneNumber(toCopy.getPhoneNumber());
//		target.setWebsite(toCopy.getWebsite());
//		target.setActive(true);
//		target.setDateLastUpdate(new Date(System.currentTimeMillis()));
//	}
	private Map<EnumAssociationAttribute,String> modifiedAttributes(AssociationEntity association){
		Map<EnumAssociationAttribute,String> result = new HashMap<EnumAssociationAttribute,String>();
		AssociationEntity origin = getAssociation();
		if(origin.getName() != null){
			if(!origin.getName().equals(association.getName()))
				result.put(EnumAssociationAttribute.NAME, association.getName());
		}else if(association.getName() != null){
			result.put(EnumAssociationAttribute.NAME, association.getName());
		}

		if(origin.getPostalCode() != null){
			if(!origin.getPostalCode().equals(association.getPostalCode()))
				result.put(EnumAssociationAttribute.POSTAL_CODE, association.getPostalCode());
		}else if(association.getPostalCode() != null){
			result.put(EnumAssociationAttribute.POSTAL_CODE, association.getPostalCode());
		}
		
		if(origin.getTown() != null){
			if(!origin.getTown().equals(association.getTown()))
				result.put(EnumAssociationAttribute.TOWN, association.getTown());
		}else if(association.getTown() != null){
			result.put(EnumAssociationAttribute.TOWN, association.getTown());
		}
		
		if(origin.getAddress() != null){
			if(!origin.getAddress().equals(association.getAddress()))
				result.put(EnumAssociationAttribute.ADDRESS, association.getAddress());
		}else if(association.getAddress() != null){
			result.put(EnumAssociationAttribute.ADDRESS, association.getAddress());
		}		
		
		if(origin.getCellNumber() != null){
			if(!origin.getCellNumber().equals(association.getCellNumber()))
				result.put(EnumAssociationAttribute.CELL_NUMBER, association.getCellNumber());
		}else if(association.getCellNumber() != null){
			result.put(EnumAssociationAttribute.CELL_NUMBER, association.getTown());
		}
		
		if(origin.getPhoneNumber() != null){
			if(!origin.getPhoneNumber().equals(association.getPhoneNumber()))
				result.put(EnumAssociationAttribute.PHONE_NUMBER, association.getPhoneNumber());
		}else if(association.getPhoneNumber() != null){
			result.put(EnumAssociationAttribute.PHONE_NUMBER, association.getTown());
		}
		
		if(origin.getEmail() != null){
			if(!origin.getEmail().equals(association.getEmail()))
				result.put(EnumAssociationAttribute.EMAIL, association.getEmail());
		}else if(association.getEmail() != null){
			result.put(EnumAssociationAttribute.EMAIL, association.getTown());
		}
		
		if(origin.getWebsite() != null){
			if(!origin.getWebsite().equals(association.getWebsite()))
				result.put(EnumAssociationAttribute.WEBSITE, association.getWebsite());
		}else if(association.getWebsite() != null){
			result.put(EnumAssociationAttribute.WEBSITE, association.getWebsite());
		}
		
		return result;
	}
}
