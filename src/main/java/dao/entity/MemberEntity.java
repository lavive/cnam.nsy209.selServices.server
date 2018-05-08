package dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@PrimaryKeyJoinColumn(name="id_person")
@Table(name = "member")
public class MemberEntity extends PersonEntity implements SelServicesEntity{
	
	@Column(name = "forname", nullable = false, length = 50)
	private String forname;
	
	@Column(name ="mobile_id",unique = true, nullable = false)
	private String mobileId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy= "member",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<SupplyDemandEntity> supplyDemand = new ArrayList<SupplyDemandEntity>();
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "wealth_sheet_id")
	private WealthSheetEntity wealthSheet;
	
	@Column(name = "date_last_consult", nullable = false)
	private Date dateLastConsult;
	
	@Column(name = "date_last_remote_update", nullable = false)
	private Date dateLastRemoteUpdate;
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "membersToNotify")
	List<NotificationEntity> notifications = new ArrayList<NotificationEntity>();

	@Column(name = "to_notify", nullable = false)
	private boolean toNotify;

	

	/* getter and setter */

	public String getForname() {
		return forname;
	}

	public void setForname(String forname) {
		this.forname = forname;
	}	

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public List<SupplyDemandEntity> getSupplyDemand() {
		return supplyDemand;
	}

	public void setSupplyDemand(List<SupplyDemandEntity> supplyDemand) {
		this.supplyDemand = supplyDemand;
	}

	public WealthSheetEntity getWealthSheet() {
		return wealthSheet;
	}

	public void setWealthSheet(WealthSheetEntity wealthSheet) {
		this.wealthSheet = wealthSheet;
	}

	public Date getDateLastConsult() {
		return dateLastConsult;
	}

	public void setDateLastConsult(Date dateLastConsult) {
		this.dateLastConsult = dateLastConsult;
	}

	public Date getDateLastRemoteUpdate() {
		return dateLastRemoteUpdate;
	}

	public void setDateLastRemoteUpdate(Date dateModification) {
		this.dateLastRemoteUpdate = dateModification;
	}

	public List<NotificationEntity> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationEntity> notifications) {
		this.notifications = notifications;
	}

	public boolean isToNotify() {
		return toNotify;
	}

	public void setToNotify(boolean toNotify) {
		this.toNotify = toNotify;
	}
		
}