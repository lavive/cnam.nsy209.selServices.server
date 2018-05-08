package shared.dto;

import java.io.Serializable;

public class TransactionDto implements Serializable{
	
	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Long debtorMemberId;
	
	private Long creditorMemberId;
	
	private Long supplyDemandId;

	private String amount;
	

	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreditorMemberId() {
		return creditorMemberId;
	}

	public void setCreditorMemberId(Long creditorMemberId) {
		this.creditorMemberId = creditorMemberId;
	}

	public Long getDebtorMemberId() {
		return debtorMemberId;
	}

	public void setDebtorMemberId(Long debtorMemberId) {
		this.debtorMemberId = debtorMemberId;
	}

	public Long getSupplyDemandId() {
		return supplyDemandId;
	}

	public void setSupplyDemandId(Long supplyDemandId) {
		this.supplyDemandId= supplyDemandId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "amount: "+getAmount();
	}
	
	

}
