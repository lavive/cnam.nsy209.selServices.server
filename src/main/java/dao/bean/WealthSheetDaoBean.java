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

@Stateless
@TransactionAttribute
public class WealthSheetDaoBean implements WealthSheetDaoLocal {
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void create(WealthSheetEntity entity) {
//		entity.setActive(true);
//		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
//		this.entityManager.persist(entity);
//		this.entityManager.flush();

	}

	public WealthSheetEntity get(long id) {
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
		//createTransaction(transactionEntity);
				
//		/* update creditor */
//		transactionEntity.setActive(true);
//		transactionEntity.setDateLastUpdate(new Date(System.currentTimeMillis()));
//		MemberEntity creditor = memberDao.get(transactionEntity.getCreditorMember().getId());
//		
//		WealthSheetEntity wealthSheet = get(creditor.getWealthSheet().getId());
//		wealthSheet.setFinalAccount(wealthSheet.getFinalAccount().add(transactionEntity.getAmount()));
//		wealthSheet.getTransactions().add(getTransaction(transactionEntity.getId()));
//		creditor.setWealthSheet(wealthSheet);
		
		
		
		
//		WealthSheetEntity wealthSheetCreditor = creditor.getWealthSheet();
//		List<TransactionEntity> transactionsCreditor = null;
//		if(wealthSheetCreditor.getTransactions() == null) {
//			transactionsCreditor = new ArrayList<TransactionEntity>();
//			wealthSheetCreditor.setTransactions(transactionsCreditor);
//		}
//		transactionsCreditor = wealthSheetCreditor.getTransactions();
//		transactionsCreditor.add(transactionEntity);
//		wealthSheetCreditor.setFinalAccount(
//				wealthSheetCreditor.getFinalAccount().add(transactionEntity.getAmount()));
////		update(wealthSheetCreditor);
////		wealthSheetCreditor.setActive(true);
//		wealthSheetCreditor.setDateLastUpdate(new Date(System.currentTimeMillis()));
//		//creditor.setWealthSheet(wealthSheetCreditor);
//		this.entityManager.merge(creditor);
		
//		/* update debitor */
//		MemberEntity debtor = transactionEntity.getDebtorMember();
//		WealthSheetEntity wealthSheetDebtor = debtor.getWealthSheet();
//		BigDecimal amount = transactionEntity.getAmount().multiply(new BigDecimal(-1));
//		transactionEntity.setAmount(amount);
//		List<TransactionEntity> transactionsDebtor = null;
//		if(wealthSheetDebtor.getTransactions() == null) {
//			transactionsDebtor = new ArrayList<TransactionEntity>();
//			wealthSheetDebtor.setTransactions(transactionsDebtor);
//		}
//		transactionsDebtor = wealthSheetDebtor.getTransactions();
//		transactionsDebtor.add(transactionEntity);
//		wealthSheetDebtor.setFinalAccount(
//				wealthSheetDebtor.getFinalAccount().add(transactionEntity.getAmount()));
//		update(wealthSheetDebtor);
//		debtor.setWealthSheet(wealthSheetDebtor);
//		memberDao.update(debtor);

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
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
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
