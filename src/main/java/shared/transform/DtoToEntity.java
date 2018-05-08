package shared.transform;

import java.math.BigDecimal;
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
import shared.dto.CategoryDto;
import shared.dto.MemberDto;
import shared.dto.MessageDto;
import shared.dto.NotificationDto;
import shared.dto.NotificationTopicDto;
import shared.dto.NotificationsDto;
import shared.dto.PersonDto;
import shared.dto.SupplyDemandDto;
import shared.dto.TransactionDto;
import shared.dto.WealthSheetDto;


public class DtoToEntity {
	
	public static AssociationEntity associationDtoToEntity(AssociationDto associationDto){
		AssociationEntity associationEntity = new AssociationEntity();
		if(associationDto != null) {
			associationEntity.setId(associationDto.getId());
			associationEntity.setName(associationDto.getName());
			associationEntity.setTown(associationDto.getTown());
			associationEntity.setAddress(associationDto.getAddress());
			associationEntity.setPostalCode(associationDto.getPostalCode());
			associationEntity.setEmail(associationDto.getEmail());
			associationEntity.setCellNumber(associationDto.getCellNumber());
			associationEntity.setPhoneNumber(associationDto.getPhoneNumber());
			associationEntity.setWebsite(associationDto.getWebsite());
			associationEntity.setPassword(associationDto.getPassword());
		}
		
		return associationEntity;
	}
	
	public static CategoryEntity categoryDtoToEntity(CategoryDto categoryDto){
		CategoryEntity categoryEntity = new CategoryEntity();
		
		if(categoryDto != null) {
			categoryEntity.setId(categoryDto.getId());
			categoryEntity.setName(categoryDto.getName());
		}
		
		return categoryEntity;
	}
	
	public static MemberEntity memberDtoToEntity(MemberDto memberDto){
		MemberEntity memberEntity = new MemberEntity();
		
		if(memberDto != null) {
			memberEntity.setId(memberDto.getId());
			memberEntity.setAddress(memberDto.getAddress());
			memberEntity.setPostalCode(memberDto.getPostalCode());
			memberEntity.setCellNumber(memberDto.getCellNumber());
			memberEntity.setEmail(memberDto.getEmail());
			memberEntity.setForname(memberDto.getForname());
			memberEntity.setName(memberDto.getName());
			memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
			memberEntity.setTown(memberDto.getTown());
			memberEntity.setMobileId(memberDto.getMobileId());
			memberEntity.setPassword(memberDto.getPassword());
		
            		
			if(memberDto.getWealthSheet() != null)
				memberEntity.setWealthSheet(wealthSheetDtoToEntity(memberDto.getWealthSheet()));
			
//			ArrayList<SupplyDemandEntity> supplyDemandsEntity = new ArrayList<SupplyDemandEntity>();
//			if(memberDto.getSupplyDemand() != null) {
//				for(SupplyDemandDto supplyDemandDto:memberDto.getSupplyDemand()){
//					supplyDemandsEntity.add(supplyDemandDtoToEntity(supplyDemandDto));
//				}
//			}
//			memberEntity.setSupplyDemand(supplyDemandsEntity);
			
//			ArrayList<NotificationEntity> notificationsEntity = new ArrayList<NotificationEntity>();
//			if(memberDto.getNotifications() != null) {
//				for(NotificationDto notificationDto:memberDto.getNotifications()){
//					notificationsEntity.add(notificationDtoToEntity(notificationDto));
//				}
//			}
//			memberEntity.setNotifications(notificationsEntity);
		}
		
		return memberEntity;
	}
	
	public static MessageEntity messageDtoToEntity(MessageDto messageDto){
		MessageEntity messageEntity = new MessageEntity();
		
		if(messageDto != null) {
			messageEntity.setId(messageDto.getId());
			messageEntity.setTitle(messageDto.getTitle());
			messageEntity.setText(messageDto.getText());
			//messageEntity.setState(messageDto.isState());
			messageEntity.setTransmitterPerson(personDtoToEntity(messageDto.getEmitterPerson()));
		}
		
		return messageEntity;
	}
	
	public static PersonEntity personDtoToEntity(PersonDto personDto){
		PersonEntity personEntity = new PersonEntity();
		
		if(personDto != null) {
			personEntity.setId(personDto.getId());
			personEntity.setName(personDto.getName());
			personEntity.setAddress(personDto.getAddress());
			personEntity.setPostalCode(personDto.getPostalCode());
			personEntity.setTown(personDto.getTown());
			personEntity.setEmail(personDto.getEmail());
			personEntity.setCellNumber(personDto.getCellNumber());
			personEntity.setPhoneNumber(personDto.getPhoneNumber());
			personEntity.setPassword(personDto.getPassword());
		}
		
		
		return personEntity;
	}
	
	public static TransactionEntity transactionDtoToEntity(TransactionDto transactionDto){
		TransactionEntity transactionEntity = new TransactionEntity();
		
		if(transactionDto != null) {
			transactionEntity.setId(transactionDto.getId());
			transactionEntity.setAmount(BigDecimal.valueOf(Double.parseDouble(transactionDto.getAmount())));
//			transactionEntity.setCreditorMember(memberDtoToEntity(transactionDto.getCreditorMember()));
//			transactionEntity.setDebtorMember(memberDtoToEntity(transactionDto.getDebtorMember()));
//			transactionEntity.setSupplyDemand(supplyDemandDtoToEntity(transactionDto.getSupplyDemand()));
		}
		
		return transactionEntity;
	}
	
	public static SupplyDemandEntity supplyDemandDtoToEntity(SupplyDemandDto SupplyDemandDto){
		SupplyDemandEntity supplyDemandEntity = new SupplyDemandEntity();
		
		if(SupplyDemandDto != null) {
			supplyDemandEntity.setId(SupplyDemandDto.getId());
			supplyDemandEntity.setCategory(SupplyDemandDto.getCategory());
			supplyDemandEntity.setTitle(SupplyDemandDto.getTitle());
			supplyDemandEntity.setType(SupplyDemandDto.getType());
			supplyDemandEntity.setMember(memberDtoToEntity(SupplyDemandDto.getMember()));
		}
		
		return supplyDemandEntity;
	}
	
	public static WealthSheetEntity wealthSheetDtoToEntity(WealthSheetDto WealthSheetDto){
		WealthSheetEntity wealthSheetEntity = new WealthSheetEntity();
		
		if(WealthSheetDto != null) {
			if(WealthSheetDto.getId() != null) wealthSheetEntity.setId(WealthSheetDto.getId());
			if(WealthSheetDto.getInitialAccount() != null) wealthSheetEntity.setInitialAccount(BigDecimal.valueOf(Double.parseDouble(WealthSheetDto.getInitialAccount())));
			if(WealthSheetDto.getFinalAccount() != null) wealthSheetEntity.setFinalAccount(BigDecimal.valueOf(Double.parseDouble(WealthSheetDto.getFinalAccount())));
			//wealthSheetEntity.setMember(memberDtoToEntity(WealthSheetDto.getMember()));
			
			ArrayList<TransactionEntity> transactionsEntity = new ArrayList<TransactionEntity>();
			if(WealthSheetDto.getTransactions() != null) {
				for(TransactionDto transactionDto:WealthSheetDto.getTransactions()){
					transactionsEntity.add(transactionDtoToEntity(transactionDto));
				}
			}
			wealthSheetEntity.setTransactions(transactionsEntity);
		}
		
		return wealthSheetEntity;
	}
	
	public static NotificationTopicEntity notificationTopicDtoToEntity(NotificationTopicDto notificationTopicDto) {
		NotificationTopicEntity notificationTopicEntity = new NotificationTopicEntity();
		
		if(notificationTopicDto != null) {
			notificationTopicEntity.setId(notificationTopicDto.getId());
			notificationTopicEntity.setCategory(notificationTopicDto.getCategory());
			notificationTopicEntity.setTopic(notificationTopicDto.getTopic());
			notificationTopicEntity.setPersonOriginEvent(personDtoToEntity(notificationTopicDto.getPersonOriginEvent()));
		}
		
		return notificationTopicEntity;
	}
	
	public static NotificationEntity notificationDtoToEntity(NotificationDto notificationDto) {
		NotificationEntity notificationEntity = new NotificationEntity();
		
		if(notificationDto != null) {
			notificationEntity.setId(notificationDto.getId());
			notificationEntity.setTitle(notificationDto.getTitle());
			notificationEntity.setText(notificationDto.getText());
			notificationEntity.setTopic(notificationTopicDtoToEntity(notificationDto.getTopic()));
			
			ArrayList<MemberEntity> membersEntity = new ArrayList<MemberEntity>();
			if(notificationDto.getMembersToNotify() != null) {
				for(MemberDto memberDto: notificationDto.getMembersToNotify()) {
					membersEntity.add(memberDtoToEntity(memberDto));
				}
			}
			notificationEntity.setMembersToNotify(membersEntity);
		}
		
		return notificationEntity;
	}
	
//	public static MemberJoinNotificationsEntity memberJoinNotificationsDtoToEntity(MemberJoinNotificationsDto memberJoinNotificationsDto) {
//		MemberJoinNotificationsEntity memberJoinNotificationsEntity = new MemberJoinNotificationsEntity();
//		
//		if(memberJoinNotificationsDto != null) {
//			memberJoinNotificationsEntity.setId(memberJoinNotificationsDto.getId());
//			memberJoinNotificationsEntity.setMember(memberDtoToEntity(memberJoinNotificationsDto.getMember()));
//			
//			ArrayList<NotificationEntity> notificationsEntity = new ArrayList<NotificationEntity>();
//			if(memberJoinNotificationsDto.getNotifications() != null) {
//				for(NotificationDto notificationDto: memberJoinNotificationsDto.getNotifications()) {
//					notificationsEntity.add(notificationDtoToEntity(notificationDto));
//				}
//			}
//			memberJoinNotificationsEntity.setNotifications(notificationsEntity);
//		}
//		
//		return memberJoinNotificationsEntity;
//	}
	
	/* Array */
	
	public static List<MessageEntity>  messageDtoToEntity(List<MessageDto> messageDtos){
		List<MessageEntity> resultEntity = new ArrayList<MessageEntity>();
		for(MessageDto messageDto:messageDtos) {
			resultEntity.add(messageDtoToEntity(messageDto));
		}
		return resultEntity;
	}
	
	public static List<NotificationEntity>  notificationDtoToEntity(List<NotificationDto> notificationDtos){
		List<NotificationEntity> resultEntity = new ArrayList<NotificationEntity>();
		for(NotificationDto notificationDto:notificationDtos) {
			resultEntity.add(notificationDtoToEntity(notificationDto));
		}
		return resultEntity;
	}
	
	public static List<NotificationEntity>  notificationDtoToEntity(NotificationsDto notificationsDto){
		List<NotificationEntity> resultEntity = new ArrayList<NotificationEntity>();
		for(NotificationDto notificationDto:notificationsDto.getNotifications()) {
			resultEntity.add(notificationDtoToEntity(notificationDto));
		}
		return resultEntity;
	}

}
