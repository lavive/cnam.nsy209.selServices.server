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
import javax.persistence.criteria.Root;

import dao.entity.MemberEntity;
import dao.entity.TransactionEntity;
import dao.entity.WealthSheetEntity;
import dao.local.MemberDaoLocal;
import dao.local.WealthSheetDaoLocal;
import notification.factory.local.NotificationFactory;

/**
 * Bean to manage WealthSheetEntity persistance
 * 
 * @author lavive
 *
 */

@Stateless
@TransactionAttribute
public class WealthSheetDaoBean implements WealthSheetDaoLocal {
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void create(WealthSheetEntity entity) {

	}

	public WealthSheetEntity get(long id) {
		/* API Criteria use */
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<WealthSheetEntity> query = builder.createQuery(WealthSheetEntity.class);
		Root<WealthSheetEntity> root = query.from(WealthSheetEntity.class);
		
		query.select(root).where(builder.equal(root.get("id"), id));		
			
		WealthSheetEntity entity = this.entityManager.createQuery(query).getSingleResult();
		
		return entity;
	}

	public void update(WealthSheetEntity entity) {
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		List<TransactionEntity> transactions = new ArrayList<TransactionEntity>();
		for(TransactionEntity transaction: get(entity.getId()).getTransactions()) {
			transaction.setActive(true);
			transaction.setDateLastUpdate(new Date(System.currentTimeMillis()));
			transactions.add(transaction);
		}
		entity.setTransactions(transactions);
		this.entityManager.merge(entity);

	}

	public void delete(long id) {
		this.entityManager.remove(this.entityManager.merge(get(id)));

	}

	public List<WealthSheetEntity> getAllWealthSheet() {
		/* API Criteria use */
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<WealthSheetEntity> query = builder.createQuery(WealthSheetEntity.class);
		Root<WealthSheetEntity> root = query.from(WealthSheetEntity.class);
		
		query.select(root).where(builder.equal(root.get("active"), true));
				
		return this.entityManager.createQuery(query).getResultList();
	}

	
	public TransactionEntity createTransaction(TransactionEntity transactionEntity) {

		transactionEntity.setActive(true);
		transactionEntity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.persist(transactionEntity);		
		this.entityManager.flush();
		
		return transactionEntity;
		
	}

	public void addTransaction(TransactionEntity transactionEntity) {


	}

	@Override
	public void applyTransaction(long transactionId) {
		/* get entities */
		TransactionEntity transaction = getTransaction(transactionId);
		MemberEntity creditor = transaction.getCreditorMember();
		MemberEntity debtor = transaction.getDebtorMember();
		
		/* apply transaction to debtor and creditor */
		BigDecimal amount = transaction.getAmount();
		creditor.getWealthSheet().getTransactions().add(transaction);
		creditor.getWealthSheet().setFinalAccount(creditor.getWealthSheet().getFinalAccount().add(amount));
		memberDao.update(creditor);
		
		amount = amount.multiply(new BigDecimal(-1));
		debtor.getWealthSheet().getTransactions().add(transaction);
		debtor.getWealthSheet().setFinalAccount(debtor.getWealthSheet().getFinalAccount().add(amount));
		memberDao.update(debtor);		
		
	}

	@Override
	public TransactionEntity getTransaction(long transactionId) {
		/* API Criteria use */
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<TransactionEntity> query = builder.createQuery(TransactionEntity.class);
		Root<TransactionEntity> root = query.from(TransactionEntity.class);
		
		query.select(root).where(builder.equal(root.get("id"), transactionId));		
			
		TransactionEntity entity = this.entityManager.createQuery(query).getSingleResult();
		
		return entity;
	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<WealthSheetEntity> query = builder.createQuery(WealthSheetEntity.class);
		Root<WealthSheetEntity> root = query.from(WealthSheetEntity.class);
		
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<WealthSheetEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			WealthSheetEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}

	@Override
	public NotificationFactory getNotificationFactory() {

		return null;
	}
	

}
