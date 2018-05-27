package dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *Entity Bean Transaction
 * 
 * @author lavive
 *
 */

@Entity
@Table(name = "transaction")
public class TransactionEntity implements SelServicesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	private MemberEntity debtorMember;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private MemberEntity creditorMember;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private SupplyDemandEntity supplyDemand;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "active", nullable = false)
	private boolean active;
	
	@Column(name = "date_last_update", nullable = false)
	private Date dateLastUpdate;
	

	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MemberEntity getDebtorMember() {
		return debtorMember;
	}

	public void setDebtorMember(MemberEntity debtorMember) {
		this.debtorMember = debtorMember;
	}

	public MemberEntity getCreditorMember() {
		return creditorMember;
	}

	public void setCreditorMember(MemberEntity creditorMember) {
		this.creditorMember = creditorMember;
	}

	public SupplyDemandEntity getSupplyDemand() {
		return supplyDemand;
	}

	public void setSupplyDemand(SupplyDemandEntity supplyDemand) {
		this.supplyDemand = supplyDemand;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}
}