package dao.bean;

import java.math.BigDecimal;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dao.constants.TablesName;
import dao.entity.AssociationEntity;
import dao.entity.CategoryEntity;
import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.entity.SupplyDemandEntity;
import dao.entity.WealthSheetEntity;
import dao.local.AssociationDaoLocal;
import dao.local.CategoryDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.MessageDaoLocal;
import dao.local.NotificationDaoLocal;
import dao.local.SupplyDemandDaoLocal;
import dao.local.WealthSheetDaoLocal;
import notification.factory.local.NotificationFactory;
import notification.factory.local.NotificationMemberFactoryLocal;



@Stateless
@TransactionAttribute
public class MemberDaoBean implements MemberDaoLocal {
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@EJB
	private WealthSheetDaoLocal wealthSheetDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
	
	@EJB
	private SupplyDemandDaoLocal supplyDemandDao;
	
	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private CategoryDaoLocal categoryDao;
	
	@EJB
	private MessageDaoLocal messageDao;
	
	@EJB
	private NotificationMemberFactoryLocal notificationFactory;

	@Override
	public void create(MemberEntity entity) {
		create(entity,new BigDecimal(0.0));
	}


	//@Interceptors({ notification.interceptor.InterceptorToNotify.class})
	public void create(MemberEntity member,BigDecimal initialAccount) {
		
		/* create member */
		member.setActive(true);
		member.setDateLastUpdate(new Date(System.currentTimeMillis()));
		member.setDateLastConsult(new Date(System.currentTimeMillis()));
		member.setDateLastRemoteUpdate(new Date(System.currentTimeMillis()));
		
		/* create WealthSheet for the new member */
		WealthSheetEntity wealthSheet = new WealthSheetEntity();
		wealthSheet.setActive(true);
		wealthSheet.setDateLastUpdate(new Date(System.currentTimeMillis()));
		wealthSheet.setInitialAccount(initialAccount);
		wealthSheet.setFinalAccount(initialAccount);
		
		member.setWealthSheet(wealthSheet);
		
		
		this.entityManager.persist(member);
		this.entityManager.flush();
		
		this.notificationFactory.setNewMember(member);
		this.notificationFactory.create();

	}

	public MemberEntity get(long id) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.equal(member.get("id"), id));		
			
		MemberEntity entity = this.entityManager.createQuery(query).getSingleResult();
		if(entity != null) {
			entity.setDateLastConsult(new Date(System.currentTimeMillis()));
			this.entityManager.merge(entity);
		}
		
		return entity;
	}

	public void update(MemberEntity entity) {
		MemberEntity entityUpdated = getValuesFrom(entity);
		entityUpdated.setActive(true);
		entityUpdated.setDateLastUpdate(new Date(System.currentTimeMillis()));
		entityUpdated.setDateLastConsult(new Date(System.currentTimeMillis()));
//		WealthSheetEntity wealthSheet = entity.getWealthSheet();
//		wealthSheetDao.update(wealthSheet);
//		entity.setWealthSheet(wealthSheet);
		this.entityManager.merge(entityUpdated);

	}

	//@Interceptors({ notification.interceptor.InterceptorToNotify.class}) 
	public void delete(long id) {
		MemberEntity entity = get(id);
		entity.setActive(false);
		entity.getWealthSheet().setActive(false);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		List<SupplyDemandEntity> supplyDemands = new ArrayList<SupplyDemandEntity>();
		for(SupplyDemandEntity supplyDemand:entity.getSupplyDemand()) {
			SupplyDemandEntity supplyDemandDeleted = supplyDemandDao.get(supplyDemand.getId());
			supplyDemandDeleted.setActive(false);
			supplyDemands.add(supplyDemandDeleted);
		}
		entity.setSupplyDemand(supplyDemands);
		this.entityManager.merge(entity);
		
		this.notificationFactory.setMemberLeaving(entity);
		this.notificationFactory.create();

	}

	public List<MemberEntity> getListLastMember(int sizeList) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.equal(member.get("active"),true));
		
		query.orderBy(builder.desc(member.get("dateLastConsult")));
		
		List<MemberEntity> members = this.entityManager.createQuery(query).getResultList();
		
		List<MemberEntity> membersResult = new ArrayList<MemberEntity>();
		
		int end = Math.min(sizeList, members.size());		
		for(int i = 0; i < end; i++){
			membersResult.add(members.get(i));
		}
		
		return membersResult;
	}

	public List<MemberEntity> getAllMembers() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.equal(member.get("active"),true));
				
		return this.entityManager.createQuery(query).getResultList();
	}

	public List<MemberEntity> getMembersByName(String name) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.and(builder.equal(
				builder.upper(builder.trim(member.get("name"))),
				builder.upper(builder.trim(builder.literal(name)))),
				builder.equal(member.get("active"),true)));
		
		return this.entityManager.createQuery(query).getResultList();
		
		
	}

	public List<MemberEntity> getMembersByNameAndForname(String name, String forname) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
//		Predicate clauseWhere = builder.conjunction();
//		clauseWhere.getExpressions().add(builder.equal(member.get("name"), name));
//		clauseWhere.getExpressions().add(builder.equal(member.get("forname"), forname));
		
		query.select(member).where(builder.and(
				builder.equal(
						builder.upper(builder.trim(member.get("name"))),
						builder.upper(builder.trim(builder.literal(name)))),
				builder.equal(
						builder.upper(builder.trim(member.get("forname"))),
						builder.upper(builder.trim(builder.literal(forname)))),
				builder.equal(member.get("active"),true)));
		
		return this.entityManager.createQuery(query).getResultList();
		
	}

	public List<MemberEntity> getMembers(MemberEntity attributes) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		Predicate clauseWhere = builder.equal(member.get("active"),true);
		
		if(attributes.getName() != null && !attributes.getName().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("name"))),
						builder.upper(builder.trim(builder.literal(attributes.getName())))));
		
		if(attributes.getForname() != null && !attributes.getForname().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("forname"))),
						builder.upper(builder.trim(builder.literal(attributes.getForname())))));
		
		if(attributes.getAddress() != null && !attributes.getAddress().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("address"))),
						builder.upper(builder.trim(builder.literal(attributes.getAddress())))));
		
		if(attributes.getPostalCode() != null && !attributes.getPostalCode().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("postalCode"))),
						builder.upper(builder.trim(builder.literal(attributes.getPostalCode())))));
		
		if(attributes.getTown() != null && !attributes.getTown().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("town"))),
						builder.upper(builder.trim(builder.literal(attributes.getTown())))));
		
		if(attributes.getEmail() != null && !attributes.getEmail().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("email"))),
						builder.upper(builder.trim(builder.literal(attributes.getEmail())))));
		
		if(attributes.getCellNumber() != null && !attributes.getCellNumber().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("cell_number"))),
						builder.upper(builder.trim(builder.literal(attributes.getCellNumber())))));
		
		if(attributes.getPhoneNumber() != null && !attributes.getPhoneNumber().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("phone_number"))),
						builder.upper(builder.trim(builder.literal(attributes.getPhoneNumber())))));
		
		if(attributes.getPassword() != null && !attributes.getPassword().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("password"))),
						builder.upper(builder.trim(builder.literal(attributes.getPassword())))));
		
		if(attributes.getMobileId() != null && !attributes.getMobileId().trim().equals(""))
			clauseWhere = builder.and(clauseWhere,builder.equal(
						builder.upper(builder.trim(member.get("mobile_id"))),
						builder.upper(builder.trim(builder.literal(attributes.getMobileId())))));
		
		query.select(member).where(clauseWhere);
		
		return this.entityManager.createQuery(query).getResultList();
		
	}

	public List<MemberEntity> getMembersByTown(String town) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.and(builder.equal(
				builder.upper(builder.trim(member.get("town"))),
				builder.upper(builder.trim(builder.literal(town)))),
				builder.equal(member.get("active"),true)));
		
		return this.entityManager.createQuery(query).getResultList();
		
	}

	public List<MemberEntity> getMembersBySupplyDemand(String type, String category) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		Join<MemberEntity,SupplyDemandEntity> memberSupplyDemand = member.join("supplyDemand",JoinType.LEFT);
		
//		Predicate clauseWhere = builder.conjunction();
//		clauseWhere.getExpressions().add(builder.equal(memberSupplyDemand.get("type"), type));
//		clauseWhere.getExpressions().add(builder.equal(memberSupplyDemand.get("category"), category));
//		
//		query.select(member).where(clauseWhere);
		
		query.select(member).where(builder.and(
				builder.equal(
						builder.upper(builder.trim(memberSupplyDemand.get("type"))),
						builder.upper(builder.trim(builder.literal(type)))),
				builder.equal(
						builder.upper(builder.trim(memberSupplyDemand.get("category"))),
						builder.upper(builder.trim(builder.literal(category)))),
				builder.equal(member.get("active"),true)));
		
		return this.entityManager.createQuery(query).getResultList();
		
	}

	public List<SupplyDemandEntity> getSupplyDemands(long memberId) {
		
		return get(memberId).getSupplyDemand();
		
	}

	public WealthSheetEntity getWealthSheet(long memberId) {
		
		return get(memberId).getWealthSheet();
		
	}

	public List<NotificationEntity> getNotifications(long memberId) {
		
		return get(memberId).getNotifications();
		
	}

	public void deleteNotifications(long memberId) {

		MemberEntity entity = get(memberId);
		for(NotificationEntity notificationEntity:entity.getNotifications()) {
			notificationEntity.getMembersToNotify().remove(entity);

			if(notificationEntity.getMembersToNotify().isEmpty()) {
				notificationEntity.setActive(false);
			}
			this.entityManager.merge(notificationEntity);
		}
		entity.getNotifications().clear();
		this.entityManager.merge(entity);

	}
	
	public void deleteNotifications(long memberId,List<NotificationEntity> notifications) {

		MemberEntity entity = get(memberId);
		for(NotificationEntity notificationEntity:notifications) {
			if(NotificationIsInside(notificationEntity,entity.getNotifications())) {
				NotificationEntity notification = notificationDao.get(notificationEntity.getId());
				notification.getMembersToNotify().remove(entity);
				this.entityManager.merge(notification);
				
				entity.getNotifications().remove(notification);
				this.entityManager.merge(entity);

				if(notification.getMembersToNotify().isEmpty()) {
					notification.setActive(false);
					this.entityManager.merge(notification);
				}
			}
		}
	}

	public Long getId(String mobileId) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.and(builder.equal(member.get("mobileId"), mobileId),
				builder.equal(member.get("active"),true)));
		
		MemberEntity entity = this.entityManager.createQuery(query).getSingleResult();
		
		return entity.getId();
		
	}

	public Long getId(String mobileId,String cellNumber) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> member = query.from(MemberEntity.class);
		
		query.select(member).where(builder.and(builder.and(builder.equal(member.get("mobileId"), mobileId),
				builder.equal(member.get("active"),true))),builder.equal(member.get("cellNumber"), cellNumber));
		
		MemberEntity entity = this.entityManager.createQuery(query).getSingleResult();
		
		return entity.getId();
		
	}

	public List<String> checkForUpdate(long id) {
		
		MemberEntity entity = get(id);
		Date dateToCompare = entity.getDateLastRemoteUpdate();
		
		List<String> returnList = new ArrayList<String>();
		
		/* add table to udapte to the return list */
		if(dateToCompare.before(associationDao.lastDateUpdate())){
			returnList.add(TablesName.ASSOCIATION_TABLE);
		}
		if(dateToCompare.before(lastDateUpdate())){
			returnList.add(TablesName.MEMBER_TABLE);
		}
		if(dateToCompare.before(categoryDao.lastDateUpdate())){
			returnList.add(TablesName.CATEGORY_TABLE);
		}
		if(dateToCompare.before(supplyDemandDao.lastDateUpdate())){
			returnList.add(TablesName.SUPPLYDEMAND_TABLE);
		}
		if(dateToCompare.before(notificationDao.lastDateUpdate())){
			returnList.add(TablesName.NOTIFICATION_TABLE);
		}
		if(dateToCompare.before(wealthSheetDao.lastDateUpdate())){
			returnList.add(TablesName.WEALTHSHEET_TABLE);
		}
//		if(dateToCompare.before(maxDateAssociation())){
//			returnList.add(TablesName.ASSOCIATION_TABLE);
//		}
//		if(dateToCompare.before(maxDateMember())){
//			returnList.add(TablesName.MEMBER_TABLE);
//		}
//		if(dateToCompare.before(maxDateCategory())){
//			returnList.add(TablesName.CATEGORY_TABLE);
//		}
//		if(dateToCompare.before(maxDateSupplyDemand())){
//			returnList.add(TablesName.SUPPLYDEMAND_TABLE);
//		}
//		if(dateToCompare.before(maxDateNotification())){
//			returnList.add(TablesName.NOTIFICATION_TABLE);
//		}
//		if(dateToCompare.before(maxDateWealthSheet())){
//			returnList.add(TablesName.WEALTHSHEET_TABLE);
//		}

		return returnList;
	}

	public void update(Date dateLastRemoteUpdate, long memberId) {
		MemberEntity entity = get(memberId);
		entity.setDateLastRemoteUpdate(dateLastRemoteUpdate);
		this.entityManager.merge(entity);

	}

	public void update(boolean toNotify, long memberId) {
		MemberEntity entity = get(memberId);
		entity.setToNotify(toNotify);
		this.entityManager.merge(entity);

	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> root = query.from(MemberEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<MemberEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			MemberEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}


	@Override
	public NotificationFactory getNotificationFactory() {

		return this.notificationFactory;
	}
	
	
	/* helper methods */
	
	/* return the last udapte date of table */
	
	private Date maxDateAssociation(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AssociationEntity> query = builder.createQuery(AssociationEntity.class);
		
		Root<AssociationEntity> root = query.from(AssociationEntity.class);
		
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		
		AssociationEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	private Date maxDateMember(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<MemberEntity> query = builder.createQuery(MemberEntity.class);
		Root<MemberEntity> root = query.from(MemberEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		MemberEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	private Date maxDateSupplyDemand(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<SupplyDemandEntity> query = builder.createQuery(SupplyDemandEntity.class);
		Root<SupplyDemandEntity> root = query.from(SupplyDemandEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		SupplyDemandEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	private Date maxDateCategory(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);
		Root<CategoryEntity> root = query.from(CategoryEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		CategoryEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	private Date maxDateNotification(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<NotificationEntity> query = builder.createQuery(NotificationEntity.class);
		Root<NotificationEntity> root = query.from(NotificationEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		NotificationEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	private Date maxDateWealthSheet(){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<WealthSheetEntity> query = builder.createQuery(WealthSheetEntity.class);
		Root<WealthSheetEntity> root = query.from(WealthSheetEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		WealthSheetEntity entity = this.entityManager.createQuery(query).getResultList().get(0);
		
		return entity.getDateLastUpdate();
	}
	
	/* get values from updated entity */
	
	private MemberEntity getValuesFrom(MemberEntity member) {
		MemberEntity memberUpdated = get(member.getId());
		if(member.getName() != null) {
			memberUpdated.setName(member.getName());
		}
		if(member.getForname() != null) {
			memberUpdated.setForname(member.getForname());
		}
		if(member.getAddress() != null) {
			memberUpdated.setAddress(member.getAddress());
		}
		if(member.getPostalCode() != null) {
			memberUpdated.setPostalCode(member.getPostalCode());
		}
		if(member.getTown() != null) {
			memberUpdated.setTown(member.getTown());
		}
		if(member.getEmail() != null) {
			memberUpdated.setEmail(member.getEmail());
		}
		if(member.getCellNumber() != null) {
			memberUpdated.setCellNumber(member.getCellNumber());
		}
		if(member.getPhoneNumber() != null) {
			memberUpdated.setPhoneNumber(member.getPhoneNumber());
		}
		if(member.getMobileId() != null) {
			memberUpdated.setMobileId(member.getMobileId());
		}
		if(wealthSheetExist(member.getWealthSheet())) {
			wealthSheetDao.update(member.getWealthSheet());
			memberUpdated.setWealthSheet(member.getWealthSheet());
		}
		if(!member.getNotifications().isEmpty()) {
			List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();
			for(NotificationEntity notification:member.getNotifications()) {
				notifications.add(notificationDao.get(notification.getId()));
			}
			memberUpdated.setNotifications(notifications);
		}
		if(!member.getSupplyDemand().isEmpty()) {
			List<SupplyDemandEntity> supplyDemands = new ArrayList<SupplyDemandEntity>();
			for(SupplyDemandEntity supplyDemand:member.getSupplyDemand()) {
				supplyDemands.add(supplyDemandDao.get(supplyDemand.getId()));
			}
			memberUpdated.setSupplyDemand(supplyDemands);
		}
		
		return memberUpdated;
	}
	
	private boolean wealthSheetExist(WealthSheetEntity wealthSheet) {
		List<Long> wealthSheetIds = new ArrayList<Long>();
		for(WealthSheetEntity wealthSheetEntity:wealthSheetDao.getAllWealthSheet()) {
			wealthSheetIds.add(wealthSheetEntity.getId());
		}
		
		return wealthSheetIds.contains(wealthSheet.getId());
	}
	
	private boolean NotificationIsInside(NotificationEntity notification,List<NotificationEntity> notifications) {
		boolean isInside = false;
		for(NotificationEntity notificationEntity:notifications) {
			if(notification.getId().equals(notificationEntity.getId())) {
				isInside = true;
				break;
			}
		}
		return isInside;
	}

}
