package shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO picturing member
 * 
 * @author lavive
 *
 */

public class MemberDto extends PersonDto{
	
	
	private static final long serialVersionUID = 1L;

	private String forname;
	
	private String mobileId;
	
	private List<Long> supplyDemandIds = new ArrayList<Long>();
	
	private WealthSheetDto wealthSheet;
	
	private List<Long> notificationIds = new ArrayList<Long>();

	

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

	public WealthSheetDto getWealthSheet() {
		return wealthSheet;
	}

	public void setWealthSheet(WealthSheetDto wealthSheet) {
		this.wealthSheet = wealthSheet;
	}

	public List<Long> getNotificationIds() {
		return notificationIds;
	}

	public void setNotificationIds(List<Long> notificationIds) {
		this.notificationIds = notificationIds;
	}

	public List<Long> getSupplyDemandIds() {
		return supplyDemandIds;
	}

	public void setSupplyDemandIds(List<Long> supplyDemandIds) {
		this.supplyDemandIds = supplyDemandIds;
	}
	

	
	@Override
	public String toString() {
		return getName()+" "+getForname()+" "+getAddress()+" "+getPostalCode()+" "+getTown()+" "+getCellNumber()+" "+
				getPhoneNumber()+" "+getEmail()+" ";
	}

	
	

	

}
