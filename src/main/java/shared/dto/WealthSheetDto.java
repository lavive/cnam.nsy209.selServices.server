package shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO picturing wealth sheet
 * 
 * @author lavive
 *
 */


public class WealthSheetDto implements Serializable{

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String initialAccount;

	private String finalAccount;

	private List<TransactionDto> transactions = new ArrayList<TransactionDto>();
	
	
	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitialAccount() {
		return initialAccount;
	}

	public void setInitialAccount(String initialAccount) {
		this.initialAccount = initialAccount;
	}

	public String getFinalAccount() {
		return finalAccount;
	}

	public void setFinalAccount(String finalAccount) {
		this.finalAccount = finalAccount;
	}

	public List<TransactionDto> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDto> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public String toString() {
		return "initial account: "+getInitialAccount()+"\n"+
				"final account: "+getFinalAccount();
	}
}
