package shared.transform;

import java.util.ArrayList;
import java.util.List;

import dao.entity.AssociationEntity;
import dao.entity.CategoryEntity;
import dao.entity.MemberEntity;
import dao.entity.MessageEntity;
import dao.entity.NotificationEntity;
import dao.entity.NotificationTopicEntity;
import dao.entity.PersonEntity;
import dao.entity.SupplyDemandEntity;
import dao.entity.TransactionEntity;
import dao.entity.WealthSheetEntity;
import shared.dto.AssociationDto;
import shared.dto.CategoriesDto;
import shared.dto.CategoryDto;
import shared.dto.MemberDto;
import shared.dto.MembersDto;
import shared.dto.MessageDto;
import shared.dto.NotificationDto;
import shared.dto.NotificationTopicDto;
import shared.dto.NotificationsDto;
import shared.dto.PersonDto;
import shared.dto.SuppliesDemandsDto;
import shared.dto.SupplyDemandDto;
import shared.dto.TransactionDto;
import shared.dto.WealthSheetDto;



public class EntityToDto {
	
	public static AssociationDto associationEntityToDto(AssociationEntity associationEntity){
		AssociationDto associationDto = new AssociationDto();
		
		if(associationEntity != null) {
			associationDto.setId(associationEntity.getId());
			associationDto.setName(associationEntity.getName());
			associationDto.setTown(associationEntity.getTown());
			associationDto.setAddress(associationEntity.getAddress());
			associationDto.setPostalCode(associationEntity.getPostalCode());
			associationDto.setEmail(associationEntity.getEmail());
			associationDto.setCellNumber(associationEntity.getCellNumber());
			associationDto.setPhoneNumber(associationEntity.getPhoneNumber());
			associationDto.setWebsite(associationEntity.getWebsite());
			associationDto.setPassword(associationEntity.getPassword());
		}
		
		return associationDto;
	}
	
	public static CategoryDto  categoryEntityToDto(CategoryEntity categoryEntity){
		CategoryDto categoryDto = new CategoryDto();
		
		if(categoryEntity != null) {
			categoryDto.setId(categoryEntity.getId());
			categoryDto.setName(categoryEntity.getName());
		}
		
		return categoryDto;
	}
	
	public static MemberDto memberEntityToDto(MemberEntity memberEntity){
		MemberDto memberDto = new MemberDto();
		
		if(memberEntity != null) {
			memberDto.setId(memberEntity.getId());
			memberDto.setAddress(memberEntity.getAddress());
			memberDto.setPostalCode(memberEntity.getPostalCode());
			memberDto.setCellNumber(memberEntity.getCellNumber());
			memberDto.setEmail(memberEntity.getEmail());
			memberDto.setForname(memberEntity.getForname());
			memberDto.setName(memberEntity.getName());
			memberDto.setPhoneNumber(memberEntity.getPhoneNumber());
			memberDto.setTown(memberEntity.getTown());
			memberDto.setMobileId(memberEntity.getMobileId());
			memberDto.setPassword(memberEntity.getPassword());
			
			memberDto.setWealthSheet(wealthSheetEntityToDto(memberEntity.getWealthSheet()));
			
//			ArrayList<SupplyDemandDto> supplyDemandsDto = new ArrayList<SupplyDemandDto>();
//			if(memberEntity.getSupplyDemand() != null) {
//				for(SupplyDemandEntity supplyDemandEntity:memberEntity.getSupplyDemand()){
//					supplyDemandsDto.add(supplyDemandEntityToDto(supplyDemandEntity));
//				}
//			}
//			memberDto.setSupplyDemand(supplyDemandsDto);
//			
			ArrayList<Long> supplyDemandIds = new ArrayList<Long>();
			if(memberEntity.getSupplyDemand() != null) {
				for(SupplyDemandEntity supplyDemandEntity:memberEntity.getSupplyDemand()){
					supplyDemandIds.add(supplyDemandEntity.getId());
				}
			}
			memberDto.setSupplyDemandIds(supplyDemandIds);
			
			ArrayList<Long> notificationIds = new ArrayList<Long>();
			if(memberEntity.getNotifications() != null) {
				for(NotificationEntity notificationEntity:memberEntity.getNotifications()){
					notificationIds.add(notificationEntity.getId());
				}				
			} 
			memberDto.setNotificationIds(notificationIds);
		}
		
		
		return memberDto;
	}
	
	public static MessageDto messageEntityToDto(MessageEntity messagenEntity){
		MessageDto messageDto = new MessageDto();
		
		if(messagenEntity != null) {
			messageDto.setId(messagenEntity.getId());
			messageDto.setTitle(messagenEntity.getTitle());
			messageDto.setText(messagenEntity.getText());
			//messageDto.setState(messagenEntity.isState());
			messageDto.setEmitterPerson(personEntityToDto(messagenEntity.getTransmitterPerson()));
		}
		
		return messageDto;
	}
	
	public static PersonDto personEntityToDto(PersonEntity personEntity){
		PersonDto personDto = new PersonDto();
		
		if(personEntity != null) {
			personDto.setId(personEntity.getId());
			personDto.setName(personEntity.getName());
			personDto.setAddress(personEntity.getAddress());
			personDto.setPostalCode(personEntity.getPostalCode());
			personDto.setTown(personEntity.getTown());
			personDto.setEmail(personEntity.getEmail());
			personDto.setCellNumber(personEntity.getCellNumber());
			personDto.setPhoneNumber(personEntity.getPhoneNumber());
			personDto.setPassword(personEntity.getPassword());
		}
		
		
		return personDto;
	}
	
	public static TransactionDto transactionEntityToDto(TransactionEntity transactionEntity){
		TransactionDto transactionDto = new TransactionDto();
		
		if(transactionEntity != null) {
			transactionDto.setId(transactionEntity.getId());
			transactionDto.setAmount(transactionEntity.getAmount().toString());
			transactionDto.setCreditorMemberId(transactionEntity.getCreditorMember().getId());
			transactionDto.setDebtorMemberId(transactionEntity.getDebtorMember().getId());
			transactionDto.setSupplyDemandId(transactionEntity.getSupplyDemand().getId());
		}
		
		return transactionDto;
	}
	
	public static SupplyDemandDto supplyDemandEntityToDto(SupplyDemandEntity SupplyDemandEntity){
		SupplyDemandDto supplyDemandDto = new SupplyDemandDto();
		
		if(SupplyDemandEntity != null) {
			supplyDemandDto.setId(SupplyDemandEntity.getId());
			supplyDemandDto.setCategory(SupplyDemandEntity.getCategory());
			supplyDemandDto.setTitle(SupplyDemandEntity.getTitle());
			supplyDemandDto.setType(SupplyDemandEntity.getType());
			supplyDemandDto.setMember(memberEntityToDto(SupplyDemandEntity.getMember()));
		}
		
		return supplyDemandDto;
	}
	
	public static WealthSheetDto wealthSheetEntityToDto(WealthSheetEntity wealthSheetEntity){
		WealthSheetDto wealthSheetDto = new WealthSheetDto();
		
		if(wealthSheetEntity != null) {
			wealthSheetDto.setId(wealthSheetEntity.getId());
			wealthSheetDto.setInitialAccount(wealthSheetEntity.getInitialAccount().toString());
			wealthSheetDto.setFinalAccount(wealthSheetEntity.getFinalAccount().toString());
			//wealthSheetDto.setMember(memberEntityToDto(WealthSheetEntity.getMember()));
			
			ArrayList<TransactionDto> transactionsDto = new ArrayList<TransactionDto>();
			if(wealthSheetEntity.getTransactions() != null) {
				for(TransactionEntity transactionEntity:wealthSheetEntity.getTransactions()){
					transactionsDto.add(transactionEntityToDto(transactionEntity));
				}
			}
			wealthSheetDto.setTransactions(transactionsDto);
		}
		
		return wealthSheetDto;
	}
	
	public static NotificationTopicDto notificationTopicEntityToDto(NotificationTopicEntity notificationTopicEntity) {
		NotificationTopicDto notificationTopicDto = new NotificationTopicDto();
		
		if(notificationTopicEntity != null) {
			notificationTopicDto.setId(notificationTopicEntity.getId());
			notificationTopicDto.setCategory(notificationTopicEntity.getCategory());
			notificationTopicDto.setTopic(notificationTopicEntity.getTopic());
			notificationTopicDto.setPersonOriginEvent(personEntityToDto(notificationTopicEntity.getPersonOriginEvent()));
		}
		
		return notificationTopicDto;
	}
	
	public static NotificationDto notificationEntityToDto(NotificationEntity notificationEntity) {
		NotificationDto notificationDto = new NotificationDto();
		
		if(notificationEntity != null) {
			notificationDto.setId(notificationEntity.getId());
			notificationDto.setTitle(notificationEntity.getTitle());
			notificationDto.setText(notificationEntity.getText());
			notificationDto.setTopic(notificationTopicEntityToDto(notificationEntity.getTopic()));
			
			ArrayList<MemberDto> membersDto = new ArrayList<MemberDto>();
			if(notificationEntity.getMembersToNotify() != null) {
				for(MemberEntity memberEntity: notificationEntity.getMembersToNotify()) {
					membersDto.add(memberEntityToDto(memberEntity));
				}
			}
			notificationDto.setMembersToNotify(membersDto);
		}
		
		return notificationDto;
	}
	
//	public static MemberJoinNotificationsDto memberJoinNotificationsEntityToDto(MemberJoinNotificationsEntity memberJoinNotificationsEntity) {
//		MemberJoinNotificationsDto memberJoinNotificationsDto = new MemberJoinNotificationsDto();
//		
//		if(memberJoinNotificationsEntity != null) {
//			memberJoinNotificationsDto.setId(memberJoinNotificationsEntity.getId());
//			memberJoinNotificationsDto.setMember(memberEntityToDto(memberJoinNotificationsEntity.getMember()));
//			
//			ArrayList<NotificationDto> notificationsDto = new ArrayList<NotificationDto>();
//			if(memberJoinNotificationsEntity.getNotifications() != null) {
//				for(NotificationEntity notificationEntity: memberJoinNotificationsEntity.getNotifications()) {
//					notificationsDto.add(notificationEntityToDto(notificationEntity));
//				}
//			}
//			memberJoinNotificationsDto.setNotifications(notificationsDto);
//		}
//		
//		return memberJoinNotificationsDto;
//	}
	
	/* Array */
	public static List<CategoryDto>  categoryEntityToDto(List<CategoryEntity> categoryEntities){
		List<CategoryDto> resultDto = new ArrayList<CategoryDto>();
		for(CategoryEntity categoryEntity:categoryEntities) {
			resultDto.add(categoryEntityToDto(categoryEntity));
		}
		return resultDto;
	}

	public static List<MemberDto>  memberEntityToDto(List<MemberEntity> memberEntities){
		List<MemberDto> resultDto = new ArrayList<MemberDto>();
		for(MemberEntity memberEntity:memberEntities) {
			resultDto.add(memberEntityToDto(memberEntity));
		}
		return resultDto;
	}

	public static List<MessageDto>  messageEntityToDto(List<MessageEntity> messageEntities){
		List<MessageDto> resultDto = new ArrayList<MessageDto>();
		for(MessageEntity messageEntity:messageEntities) {
			resultDto.add(messageEntityToDto(messageEntity));
		}
		return resultDto;
	}

	public static List<NotificationDto>  notificationEntityToDto(List<NotificationEntity> notificationEntities){
		List<NotificationDto> resultDto = new ArrayList<NotificationDto>();
		for(NotificationEntity notificationEntity:notificationEntities) {
			resultDto.add(notificationEntityToDto(notificationEntity));
		}
		return resultDto;
	}

	public static List<SupplyDemandDto>  supplyDemandEntityToDto(List<SupplyDemandEntity> supplyDemandEntities){
		List<SupplyDemandDto> resultDto = new ArrayList<SupplyDemandDto>();
		for(SupplyDemandEntity supplyDemandEntity:supplyDemandEntities) {
			resultDto.add(supplyDemandEntityToDto(supplyDemandEntity));
		}
		return resultDto;
	}

	public static List<WealthSheetDto>  wealthSheetEntityToDto(List<WealthSheetEntity> wealthSheeEntities){
		List<WealthSheetDto> resultDto = new ArrayList<WealthSheetDto>();
		for(WealthSheetEntity wealthSheeEntity:wealthSheeEntities) {
			resultDto.add(wealthSheetEntityToDto(wealthSheeEntity));
		}
		return resultDto;
	}
	public static CategoriesDto  categoriesEntityToDto(List<CategoryEntity> categoryEntities){
		CategoriesDto resultDto = new CategoriesDto();
		for(CategoryEntity categoryEntity:categoryEntities) {
			resultDto.getCategories().add(categoryEntityToDto(categoryEntity));
		}
		return resultDto;
	}

	public static MembersDto  membersEntityToDto(List<MemberEntity> memberEntities){
		MembersDto resultDto = new MembersDto();
		for(MemberEntity memberEntity:memberEntities) {
			resultDto.getMembers().add(memberEntityToDto(memberEntity));
		}
		return resultDto;
	}

	public static NotificationsDto  notificationsEntityToDto(List<NotificationEntity> notificationEntities){
		NotificationsDto resultDto = new NotificationsDto();
		for(NotificationEntity notificationEntity:notificationEntities) {
			resultDto.getNotifications().add(notificationEntityToDto(notificationEntity));
		}
		return resultDto;
	}

	public static SuppliesDemandsDto  suppliesDemandsEntityToDto(List<SupplyDemandEntity> supplyDemandEntities){
		SuppliesDemandsDto resultDto = new SuppliesDemandsDto();
		for(SupplyDemandEntity supplyDemandEntity:supplyDemandEntities) {
			resultDto.getSuppliesDemands().add(supplyDemandEntityToDto(supplyDemandEntity));
		}
		return resultDto;
	}

}
