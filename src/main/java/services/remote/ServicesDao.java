package services.remote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import shared.dto.AssociationDto;
import shared.dto.CategoryDto;
import shared.dto.MemberDto;
import shared.dto.MessageDto;
import shared.dto.NotificationDto;
import shared.dto.NotificationsDto;
import shared.dto.PersonDto;
import shared.dto.SupplyDemandDto;
import shared.dto.TransactionDto;
import shared.dto.WealthSheetDto;

@Remote
public interface ServicesDao {
	
	/* create */
	public void createAssociation(AssociationDto association);
	public void createCategory(CategoryDto category);
	public void createMember(MemberDto member,BigDecimal initialAccount);
	public void createMessage(MessageDto message);
	public void createNotification(NotificationDto notification);
	public void createSupplyDemand(SupplyDemandDto supplyDemand);
	//public void createMemberJoinNotifications(MemberJoinNotificationsDto memberJoinNotifications);
	public void addNotificationToMember(NotificationDto notification,MemberDto member);
	
	public void addTransaction(TransactionDto transaction);
	public TransactionDto buildTransaction(TransactionDto transaction);
	
	/* update */
	public void updateAssociation(AssociationDto association);
	public void updateCategory(CategoryDto category);
	public void updateMember(MemberDto member);
	public void updateMessage(MessageDto message);
	public void updateNotification(NotificationDto notification);
	public void updateSupplyDemand(SupplyDemandDto supplyDemand);
	
	/* delete */
	public void deleteAssociation(long id);
	public void deleteCategory(long id);
	public void deleteMember(long id);
	public void deleteMessage(long id);
	public void deleteNotification(long id);
	public void deleteNotificationsFromMember(long memberId,NotificationsDto notificationDto);
	public void deleteSupplyDemand(long id);
	
	/* get */
	public AssociationDto getAssociation();	
	public CategoryDto getCategory(long id);
	public MemberDto getMember(long id);
	public MessageDto getMessage(long id);
	public NotificationDto getNotification(long id);
	public SupplyDemandDto getSupplyDemand(long id);
	public List<NotificationDto> getNotifications(long[] ids);
	
	/* association */
	public List<MemberDto> checkIdAssociation(String login, String password, int number);
	
	/* category */
	public List<CategoryDto> getCategories();
	
	/* member */
	public List<MemberDto> getListLastMember(int sizeList);
	public List<MemberDto> getAllMembers();
	public List<MemberDto> getMembersByName(String name);
	public List<MemberDto> getMembersByNameAndForname(String name, String forname);
	public List<MemberDto> getMembers(MemberDto attributes);
	public List<MemberDto> getMembersByTown(String town);
	public List<MemberDto> getMembersBySupplyDemand(String type, String category);
	public List<SupplyDemandDto> getSupplyDemands(long memberId);
	public WealthSheetDto getWealthSheet(long memberId);
	public List<NotificationDto> getNotifications(long memberId);
	public void deleteNotifications(long memberId);
	public Long getId(String mobileId);
	public Long getId(String mobileId,String cellNumbers);
	public List<String> checkForUpdate(long id);
	public void update(Date dateLastRemoteUpdate, long memberId);
	public void update(boolean toNotify, long memberId);
	public void updateDateLastUpdate(long memberId);
	
	/* message */
	public List<MessageDto> getMessages();
	public List<MessageDto> getMessages(PersonDto person);
	public void deleteMessage();
	public void deleteMessages(List<MessageDto> messages);
	
	/* notification */
	public List<NotificationDto> create(List<NotificationDto> notifications);
	
	/* supplyDemand */
	public List<SupplyDemandDto> getAllSupplyDemand();
	public List<SupplyDemandDto> getSuppliesDemands(String type);
	
	/* wealthSheet */
	public List<WealthSheetDto> getAllWealthSheet();
	public TransactionDto getTransaction(long transactionId);
	
	/* rest services */
//	public JSONObject getAllMembersRest();
}
