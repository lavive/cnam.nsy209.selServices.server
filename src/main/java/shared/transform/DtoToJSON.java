package shared.transform;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.dto.AssociationDto;
import shared.dto.CategoryDto;
import shared.dto.MemberDto;
import shared.dto.MessageDto;
import shared.dto.NotificationDto;
import shared.dto.NotificationTopicDto;
import shared.dto.PersonDto;
import shared.dto.SupplyDemandDto;
import shared.dto.TransactionDto;
import shared.dto.WealthSheetDto;

/**
 * Class to transform Dto to JSON
 * 
 * @author lavive
 *
 */

public class DtoToJSON {
	

	
	public static JSONObject associationDtoToJSON(AssociationDto associationDto){
		JSONObject jObject = new JSONObject();
		
		if(associationDto != null) {
			jObject.put("id",associationDto.getId());
			jObject.put("address",associationDto.getAddress());
			jObject.put("postalCode",associationDto.getPostalCode());
			jObject.put("cellNumber",associationDto.getCellNumber());
			jObject.put("email",associationDto.getEmail());
			jObject.put("name",associationDto.getName());
			jObject.put("phoneNumber",associationDto.getPhoneNumber());
			jObject.put("town",associationDto.getTown());
			jObject.put("website",associationDto.getWebsite());
			jObject.put("password",associationDto.getPassword());
		}
		
		return jObject;
	}
	
	public static JSONObject  categoryDtoToJSON(CategoryDto categoryDto){
		JSONObject jObject = new JSONObject();
		
		if(categoryDto != null) {
			jObject.put("id",categoryDto.getId());
			jObject.put("name",categoryDto.getName());
		}
		
		return jObject;
	}
	
	public static JSONObject supplyDemandDtoToJSON(SupplyDemandDto supplyDemandDto){
		JSONObject jObject = new JSONObject();
		
		jObject.put("id",supplyDemandDto.getId());
		jObject.put("category",supplyDemandDto.getCategory());
		jObject.put("title",supplyDemandDto.getTitle());
		jObject.put("type",supplyDemandDto.getType());
		jObject.put("member",memberDtoToJSON(supplyDemandDto.getMember()));
		
		return jObject;
	}
	
	public static JSONObject personDtoToJSON(PersonDto personDto){
		JSONObject jObject = new JSONObject();
		if(personDto != null) {
			jObject.put("id",personDto.getId());
			jObject.put("address",personDto.getAddress());
			jObject.put("postalCode",personDto.getPostalCode());
			jObject.put("cellNumber",personDto.getCellNumber());
			jObject.put("email",personDto.getEmail());
			jObject.put("name",personDto.getName());
			jObject.put("phoneNumber",personDto.getPhoneNumber());
			jObject.put("town",personDto.getTown());
			jObject.put("password",personDto.getPassword());
		}
		
		return jObject;
	}
	
	public static JSONObject memberDtoToJSON(MemberDto memberDto){
		JSONObject jObject = new JSONObject();
		
		if(memberDto != null) {
			jObject.put("id",memberDto.getId());
			jObject.put("address",memberDto.getAddress());
			jObject.put("postalCode",memberDto.getPostalCode());
			jObject.put("cellNumber",memberDto.getCellNumber());
			jObject.put("email",memberDto.getEmail());
			jObject.put("forname",memberDto.getForname());
			jObject.put("name",memberDto.getName());
			jObject.put("phoneNumber",memberDto.getPhoneNumber());
			jObject.put("town",memberDto.getTown());
			jObject.put("mobileId",memberDto.getMobileId());
			jObject.put("password",memberDto.getPassword());
		
			jObject.put("wealthSheet",wealthSheetDtoToJSON(memberDto.getWealthSheet()));
		
			List<Long> supplyDemandIds = new ArrayList<Long>();
			if(memberDto.getSupplyDemandIds() != null) {
				for(Long supplyDemandId:memberDto.getSupplyDemandIds()){
					supplyDemandIds.add(supplyDemandId);
				}
			}
			jObject.put("supplyDemands",supplyDemandIds);
		
			JSONArray jArrayNotification = new JSONArray();
			if(memberDto.getNotificationIds() != null) {
				for(Long notificationId:memberDto.getNotificationIds()){
					jArrayNotification.put(notificationId);
				}
			}
			jObject.put("notifications",jArrayNotification);
		}
		
		
		return jObject;
	}
	
	public static JSONObject messageDtoToJSON(MessageDto messageDto){
		JSONObject jObject = new JSONObject();
		
		if(messageDto != null) {
			jObject.put("id",messageDto.getId());
			jObject.put("title",messageDto.getTitle());
			jObject.put("text",messageDto.getText());
			jObject.put("transmitterPerson",personDtoToJSON(messageDto.getEmitterPerson()));
		}
		
		return jObject;
	}
	
	public static JSONObject wealthSheetDtoToJSON(WealthSheetDto wealthSheetDto){
		JSONObject jObject = new JSONObject();
		
		if(wealthSheetDto != null) {
			jObject.put("id", wealthSheetDto.getId());
			jObject.put("initialAccount",wealthSheetDto.getInitialAccount());
			jObject.put("finalAccount",wealthSheetDto.getFinalAccount());
			
			JSONArray jArray = new JSONArray();
			if(wealthSheetDto.getTransactions() != null) {
				for (TransactionDto transactionDto:wealthSheetDto.getTransactions()) {
					JSONObject transactionJSON = transactionDtoToJSON(transactionDto);
					jArray.put(transactionJSON);
				}
			}
			jObject.put("transactions", jArray);
		}
		
		return jObject;
	}
	
	public static JSONObject transactionDtoToJSON(TransactionDto transactionDto){
		JSONObject jObject = new JSONObject();
		
		if(transactionDto != null) {
			jObject.put("id", transactionDto.getId());
			jObject.put("amount", transactionDto.getAmount());
			jObject.put("creditorId", transactionDto.getCreditorMemberId());
			jObject.put("debtorId", transactionDto.getDebtorMemberId());
			jObject.put("supplyDemandId", transactionDto.getSupplyDemandId());
		}
		
		return jObject;
	}
	
	public static JSONObject notificationDtoToJSON(NotificationDto notificationDto){
		JSONObject jObject = new JSONObject();
		
		if(notificationDto != null) {
			jObject.put("id", notificationDto.getId());
			jObject.put("title", notificationDto.getTitle());
			jObject.put("text", notificationDto.getText());
			
			JSONObject jObjectTopic = notificationTopicDtoToJSON(notificationDto.getTopic());
			jObject.put("topic",jObjectTopic);
			
			JSONArray jArray = new JSONArray();
			if(notificationDto.getMembersToNotify() != null) {
				for(MemberDto memberEntity: notificationDto.getMembersToNotify()) {
					JSONObject memberToNotifyJSON = memberDtoToJSON(memberEntity);
					jArray.put(memberToNotifyJSON);
				}
			}
			jObject.put("membersToNotify", jArray);
		}
		
		return jObject;
	}
	
	public static JSONObject notificationTopicDtoToJSON(NotificationTopicDto notificationTopicDto){
		JSONObject jObject = new JSONObject();
		if(notificationTopicDto != null) {
			jObject.put("id", notificationTopicDto.getId());
			jObject.put("topic", notificationTopicDto.getTopic());
			jObject.put("category", notificationTopicDto.getCategory());
			
			JSONObject jObjectPersonOriginEvent = personDtoToJSON(notificationTopicDto.getPersonOriginEvent());
			jObject.put("personOriginEvent",jObjectPersonOriginEvent);
		}
		
		return jObject;
	}
	
	/* Array */

	
	public static JSONObject  categoryDtoToJSON(List<CategoryDto> categoriesDto){
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		if(categoriesDto != null) {
			for(CategoryDto categoryDto:categoriesDto) {
				jArray.put(categoryDtoToJSON(categoryDto));
			}
		}
		jObject.put("categories", jArray);
		
		return jObject;
	}
	
	public static JSONObject  supplyDemandDtoToJSON(List<SupplyDemandDto> supplyDemandsDto){
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		if(supplyDemandsDto != null) {
			for(SupplyDemandDto supplyDemandDto:supplyDemandsDto) {
				jArray.put(supplyDemandDtoToJSON(supplyDemandDto));
			}
		}
		jObject.put("suppliesDemands", jArray);
		
		return jObject;
	}
	
	public static JSONObject  memberDtoToJSON(List<MemberDto> membersDto){
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		if(membersDto != null) {
			for(MemberDto memberDto:membersDto) {
				jArray.put(memberDtoToJSON(memberDto));
			}
		}
		jObject.put("members", jArray);
		
		return jObject;
	}
	
	public static JSONObject  notificationDtoToJSON(List<NotificationDto> notificationsDto){
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		if(notificationsDto != null) {
			for(NotificationDto notificationDto:notificationsDto) {
				jArray.put(notificationDtoToJSON(notificationDto));
			}
		}
		jObject.put("notifications", jArray);
		
		return jObject;
	}

}
