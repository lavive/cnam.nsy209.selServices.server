package dao.local;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dao.entity.MemberEntity;
import dao.entity.NotificationEntity;
import dao.entity.SupplyDemandEntity;
import dao.entity.WealthSheetEntity;

/**
 * Bean to manage MemberEntity persistance
 * 
 * @author lavive
 *
 */

@Local
public interface MemberDaoLocal extends CommonDao<MemberEntity>{
	
	public void create(MemberEntity entity,BigDecimal initialAccount);

	public List<MemberEntity> getListLastMember(int sizeList);
	
	public List<MemberEntity> getAllMembers();
	
	public List<MemberEntity> getMembersByName(String name);
	
	public List<MemberEntity> getMembersByNameAndForname(String name,String forname);
	
	public List<MemberEntity> getMembers(MemberEntity attributes);
	
	public List<MemberEntity> getMembersByTown(String town);
	
	public List<MemberEntity> getMembersBySupplyDemand(String type,String category);
	
	public List<SupplyDemandEntity> getSupplyDemands(long memberId);
	
	public WealthSheetEntity getWealthSheet(long memberId);

	public List<NotificationEntity> getNotifications(long memberId);
	
	public void deleteNotifications(long memberId);
	
	public void deleteNotifications(long memberId,List<NotificationEntity> notifications);
	
	public Long getId(String mobileId);
	
	public Long getId(String mobileId,String cellNumber);
	
	public List<String> checkForUpdate(long id);
	
	public void update(Date dateLastRemoteUpdate, long memberId);
	
	public void update(boolean toNotify, long memberId);
	
	
}