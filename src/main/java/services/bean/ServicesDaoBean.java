package services.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import dao.entity.TransactionEntity;
import dao.local.AssociationDaoLocal;
import dao.local.CategoryDaoLocal;
import dao.local.MemberDaoLocal;
import dao.local.MessageDaoLocal;
import dao.local.NotificationDaoLocal;
import dao.local.SupplyDemandDaoLocal;
import dao.local.WealthSheetDaoLocal;
import services.remote.ServicesDao;
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
import shared.transform.DtoToEntity;
import shared.transform.EntityToDto;

@Stateless
@TransactionAttribute
public class ServicesDaoBean implements ServicesDao {
	
	@EJB
	private AssociationDaoLocal associationDao;
	
	@EJB
	private MemberDaoLocal memberDao;
	
	@EJB
	private CategoryDaoLocal categoryDao;
	
	@EJB
	private MessageDaoLocal messageDao;
	
	@EJB
	private WealthSheetDaoLocal wealthsheetDao;
	
	@EJB
	private NotificationDaoLocal notificationDao;
	
	@EJB
	private SupplyDemandDaoLocal supplyDemandDao;
	
//	@EJB
//	private MemberJoinNotificationsDaoLocal MemberJoinNotificationsDao;

	
	/* create */
	
	@Override
	public void createAssociation(AssociationDto association) {
		associationDao.create(DtoToEntity.associationDtoToEntity(association));
		
	}

	@Override
	public void createCategory(CategoryDto category) {
		categoryDao.create(DtoToEntity.categoryDtoToEntity(category));
		
	}

	@Override
	public void createMember(MemberDto member,BigDecimal initialAccount) {
		memberDao.create(DtoToEntity.memberDtoToEntity(member),initialAccount);
	}

	@Override
	public void createMessage(MessageDto message) {
		messageDao.create(DtoToEntity.messageDtoToEntity(message));
		
	}

	@Override
	public void createNotification(NotificationDto notification) {
		notificationDao.create(DtoToEntity.notificationDtoToEntity(notification));
		
	}

	@Override
	public void createSupplyDemand(SupplyDemandDto supplyDemand) {
		supplyDemandDao.create(DtoToEntity.supplyDemandDtoToEntity(supplyDemand));
		
	}

	@Override
	public void addNotificationToMember(NotificationDto notification, MemberDto member) {
		notificationDao.addNotificationToMember(notification.getId(),member.getId());
		
	}

//	@Override
//	public void createMemberJoinNotifications(MemberJoinNotificationsDto memberJoinNotifications) {
//		MemberJoinNotificationsDao.create(
//				DtoToEntity.memberJoinNotificationsDtoToEntity(memberJoinNotifications));
//		
//	}

	
	
	
	@Override
	public void addTransaction(TransactionDto transaction) {
				
		TransactionDto transactionDto = EntityToDto.transactionEntityToDto(
				wealthsheetDao.createTransaction(getTransactionEntityFromDto(transaction)));
		
		wealthsheetDao.applyTransaction(transactionDto.getId());
		
	}

	@Override
	public TransactionDto buildTransaction(TransactionDto transaction) {
		
		TransactionDto transactionDto = EntityToDto.transactionEntityToDto(
				wealthsheetDao.createTransaction(getTransactionEntityFromDto(transaction)));
		
		wealthsheetDao.applyTransaction(transactionDto.getId());
		
		return transactionDto;		
		
	}
	
	
	/* update */

	@Override
	public void updateAssociation(AssociationDto association) {
		associationDao.update(DtoToEntity.associationDtoToEntity(association));
		
	}

	@Override
	public void updateCategory(CategoryDto category) {
		categoryDao.update(DtoToEntity.categoryDtoToEntity(category));
		
	}

	@Override
	public void updateMember(MemberDto member) {
		memberDao.update(DtoToEntity.memberDtoToEntity(member));
		
	}

	@Override
	public void updateMessage(MessageDto message) {
		messageDao.update(DtoToEntity.messageDtoToEntity(message));
		
	}

	@Override
	public void updateNotification(NotificationDto notification) {
		notificationDao.update(DtoToEntity.notificationDtoToEntity(notification));
		
	}

	@Override
	public void updateSupplyDemand(SupplyDemandDto supplyDemand) {
		supplyDemandDao.update(DtoToEntity.supplyDemandDtoToEntity(supplyDemand));
		
	}
	
	
	/* delete */

	@Override
	public void deleteAssociation(long id) {
		associationDao.delete(id);
		
	}

	@Override
	public void deleteCategory(long id) {
		categoryDao.delete(id);
		
	}

	@Override
	public void deleteMember(long id) {
		memberDao.delete(id);
		
	}

	@Override
	public void deleteMessage(long id) {
		messageDao.delete(id);
		
	}

	@Override
	public void deleteNotification(long id) {
		notificationDao.delete(id);
		
	}
	
	@Override
	public void deleteNotificationsFromMember(long memberId,NotificationsDto notificationDto) {
		memberDao.deleteNotifications(memberId, DtoToEntity.notificationDtoToEntity(notificationDto));
	}

	@Override
	public void deleteSupplyDemand(long id) {
		supplyDemandDao.delete(id);
		
	}	
	
	
	/* get */

	@Override
	public AssociationDto getAssociation() {
		return EntityToDto.associationEntityToDto(associationDao.getAssociation());
	}

	@Override
	public CategoryDto getCategory(long id) {
		return EntityToDto.categoryEntityToDto(categoryDao.get(id));
	}

	@Override
	public MemberDto getMember(long id) {
		
		return EntityToDto.memberEntityToDto(memberDao.get(id));
	}

	@Override
	public MessageDto getMessage(long id) {
		
		return EntityToDto.messageEntityToDto(messageDao.get(id));
	}

	@Override
	public NotificationDto getNotification(long id) {
		
		return EntityToDto.notificationEntityToDto(notificationDao.get(id));
	}

	@Override
	public SupplyDemandDto getSupplyDemand(long id) {
		return EntityToDto.supplyDemandEntityToDto(supplyDemandDao.get(id));
	}

	@Override
	public List<NotificationDto> getNotifications(long[] ids) {
		List<NotificationDto> result = new ArrayList<NotificationDto>();
		for(long id:ids) {
			result.add(getNotification(id));
		}
		return result;
	}
	
	/* association */

	@Override
	public List<MemberDto> checkIdAssociation(String login, String password, int number) {
		
		return EntityToDto.memberEntityToDto(associationDao.checkIdAssociation(login, password, number));
	}
	
	/* category */

	@Override
	public List<CategoryDto> getCategories() {
		
		return EntityToDto.categoryEntityToDto(categoryDao.getCategories());
	}
	
	
	/* member */

	@Override
	public List<MemberDto> getListLastMember(int sizeList) {
		
		return EntityToDto.memberEntityToDto(memberDao.getListLastMember(sizeList));
	}

	@Override
	public List<MemberDto> getAllMembers() {
		
		return EntityToDto.memberEntityToDto(memberDao.getAllMembers());
	}

	@Override
	public List<MemberDto> getMembersByName(String name) {
		
		return EntityToDto.memberEntityToDto(memberDao.getMembersByName(name));
	}

	@Override
	public List<MemberDto> getMembersByNameAndForname(String name, String forname) {
		
		return EntityToDto.memberEntityToDto(memberDao.getMembersByNameAndForname(name, forname));
	}

	@Override
	public List<MemberDto> getMembers(MemberDto attributes) {
		
		return EntityToDto.memberEntityToDto(memberDao.getMembers(DtoToEntity.memberDtoToEntity(attributes)));
	}

	@Override
	public List<MemberDto> getMembersByTown(String town) {
		
		return EntityToDto.memberEntityToDto(memberDao.getMembersByTown(town));
	}

	@Override
	public List<MemberDto> getMembersBySupplyDemand(String type, String category) {
		
		return EntityToDto.memberEntityToDto(memberDao.getMembersBySupplyDemand(type, category));
	}

	@Override
	public List<SupplyDemandDto> getSupplyDemands(long memberId) {
		
		return EntityToDto.supplyDemandEntityToDto(memberDao.getSupplyDemands(memberId));
	}

	@Override
	public WealthSheetDto getWealthSheet(long memberId) {
		
		return EntityToDto.wealthSheetEntityToDto(memberDao.getWealthSheet(memberId));
	}

	@Override
	public List<NotificationDto> getNotifications(long memberId) {
		
		return EntityToDto.notificationEntityToDto(memberDao.getNotifications(memberId));
	}

	@Override
	public void deleteNotifications(long memberId) {
		
		memberDao.deleteNotifications(memberId);
		
	}

	@Override
	public Long getId(String mobileId) {
		
		return memberDao.getId(mobileId);
	}
	
	@Override
	public Long getId(String mobileId,String cellNumbers) {
		
		return memberDao.getId(mobileId,cellNumbers);
	}

	@Override
	public List<String> checkForUpdate(long id) {
		
		return memberDao.checkForUpdate(id);
	}

	@Override
	public void update(Date dateLastRemoteUpdate, long memberId) {
		
		memberDao.update(dateLastRemoteUpdate, memberId);
		
	}

	@Override
	public void update(boolean toNotify, long memberId) {
		
		memberDao.update(toNotify, memberId);
		
	}
	
	@Override
	public void updateDateLastUpdate(long memberId) {
		
		memberDao.update(new Date(System.currentTimeMillis()),memberId);
	}
	
	
	/* message */
	
	@Override
	public List<MessageDto> getMessages() {
		
		return EntityToDto.messageEntityToDto(messageDao.getMessages());
	}

	@Override
	public List<MessageDto> getMessages(PersonDto person) {
		
		return EntityToDto.messageEntityToDto(messageDao.getMessages(DtoToEntity.personDtoToEntity(person)));
	}

	@Override
	public void deleteMessage() {
		
		messageDao.deleteMessage();
		
	}

	@Override
	public void deleteMessages(List<MessageDto> messages) {
		
		messageDao.deleteMessages(DtoToEntity.messageDtoToEntity(messages));
		
	}
	
	/* notification */
	
	@Override
	public List<NotificationDto> create(List<NotificationDto> notifications) {
		
		return EntityToDto.notificationEntityToDto(
				notificationDao.create(DtoToEntity.notificationDtoToEntity(notifications)));
	}

	
	/* supplyDemand */

	@Override
	public List<SupplyDemandDto> getAllSupplyDemand() {
		
		return EntityToDto.supplyDemandEntityToDto(supplyDemandDao.getAllSupplyDemand());
	}

	@Override
	public List<SupplyDemandDto> getSuppliesDemands(String type) {
		
		return EntityToDto.supplyDemandEntityToDto(supplyDemandDao.getSuppliesDemands(type));
	}
	
	
	/* wealthSheet */

	@Override
	public List<WealthSheetDto> getAllWealthSheet() {
		
		return EntityToDto.wealthSheetEntityToDto(wealthsheetDao.getAllWealthSheet());
	}

	@Override
	public TransactionDto getTransaction(long transactionId) {
		
		return EntityToDto.transactionEntityToDto(wealthsheetDao.getTransaction(transactionId));
	}
	
	/* REST */


//	@Override
//	public JSONObject getAllMembersRest() {
//		JSONObject jObject = new JSONObject();
//		JSONArray jArray = new JSONArray();
//		List<MemberEntity> members = memberDao.getAllMembers();
//		for(MemberEntity memberEntity:members) {
//			jArray.put(EntityToJSON.memberEntityToJSON(memberEntity));
//		}
//		jObject.put("allMembers", jArray);
//		
//		return jObject;
//	}

	
	
	
	
	
	/* helper method */
	
	private TransactionEntity getTransactionEntityFromDto(TransactionDto transactionDto) {
		
		TransactionEntity transactionEntity = DtoToEntity.transactionDtoToEntity(transactionDto);
		transactionEntity.setCreditorMember(memberDao.get(transactionDto.getCreditorMemberId()));
		transactionEntity.setDebtorMember(memberDao.get(transactionDto.getDebtorMemberId()));
		transactionEntity.setSupplyDemand(supplyDemandDao.get(transactionDto.getSupplyDemandId()));
		
		return transactionEntity;
		
	}

	

}
