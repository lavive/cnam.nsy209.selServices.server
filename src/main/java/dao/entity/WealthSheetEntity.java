package dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *Entity Bean WealthSheet
 * 
 * @author lavive
 *
 */

@Entity
@Table(name = "wealth_sheet")
public class WealthSheetEntity implements SelServicesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy= "wealthSheet")
	private MemberEntity member;

	@Column(name = "initial_account", nullable = false)
	private BigDecimal initialAccount;

	@Column(name = "final_account", nullable = false)
	private BigDecimal finalAccount;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "wealthSheet_transaction",joinColumns = @JoinColumn(name = "wealthSheet_id"),
              inverseJoinColumns = @JoinColumn(name = "transaction_id"))
	private List<TransactionEntity> transactions = new ArrayList<TransactionEntity>();

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

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public BigDecimal getInitialAccount() {
		return initialAccount;
	}

	public void setInitialAccount(BigDecimal initialAccount) {
		this.initialAccount = initialAccount;
	}

	public BigDecimal getFinalAccount() {
		return finalAccount;
	}

	public void setFinalAccount(BigDecimal finalAccount) {
		this.finalAccount = finalAccount;
	}

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
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