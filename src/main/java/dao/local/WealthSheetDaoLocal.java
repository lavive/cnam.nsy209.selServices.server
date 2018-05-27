package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.TransactionEntity;
import dao.entity.WealthSheetEntity;

/**
 * Bean to manage WealthSheetEntity persistance
 * 
 * @author lavive
 *
 */

@Local
public interface WealthSheetDaoLocal extends CommonDao<WealthSheetEntity>{
	
	public List<WealthSheetEntity> getAllWealthSheet();
	
	public TransactionEntity createTransaction(TransactionEntity transactionEntity);
	
	public void addTransaction(TransactionEntity transactionEntity);
	
	public void applyTransaction(long transactionId);
	
	public TransactionEntity getTransaction(long transactionId);
	
}