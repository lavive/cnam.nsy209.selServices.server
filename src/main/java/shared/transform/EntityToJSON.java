package shared.transform;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class EntityToJSON {
	/* Not working + put key string in final static String */ 
	

	
	public static JSONObject associationEntityToJSON(AssociationEntity associationEntity){
		JSONObject jObject = new JSONObject();
		
		if(associationEntity != null) {
			jObject.put("id",associationEntity.getId());
			jObject.put("address",associationEntity.getAddress());
			jObject.put("postalCode",associationEntity.getPostalCode());
			jObject.put("cellNumber",associationEntity.getCellNumber());
			jObject.put("email",associationEntity.getEmail());
			jObject.put("name",associationEntity.getName());
			jObject.put("phoneNumber",associationEntity.getPhoneNumber());
			jObject.put("town",associationEntity.getTown());
			jObject.put("website",associationEntity.getWebsite());
			jObject.put("password",associationEntity.getPassword());
		}
		
		return jObject;
	}
	
	public static JSONObject  categoryEntityToJSON(CategoryEntity categoryEntity){
		JSONObject jObject = new JSONObject();
		
		if(categoryEntity != null) {
			jObject.put("id",categoryEntity.getId());
			jObject.put("name",categoryEntity.getName());
		}
		
		return jObject;
	}
	
	public static JSONObject supplyDemandEntityToJSON(SupplyDemandEntity SupplyDemandEntity){
		JSONObject jObject = new JSONObject();
		
		jObject.put("id",SupplyDemandEntity.getId());
		jObject.put("category",SupplyDemandEntity.getCategory());
		jObject.put("title",SupplyDemandEntity.getTitle());
		jObject.put("type",SupplyDemandEntity.getType());
		jObject.put("member",memberEntityToJSON(SupplyDemandEntity.getMember()));
		
		return jObject;
	}
	
	public static JSONObject personEntityToJSON(PersonEntity personEntity){
		JSONObject jObject = new JSONObject();
		if(personEntity != null) {
			jObject.put("id",personEntity.getId());
			jObject.put("address",personEntity.getAddress());
			jObject.put("postalCode",personEntity.getPostalCode());
			jObject.put("cellNumber",personEntity.getCellNumber());
			jObject.put("email",personEntity.getEmail());
			jObject.put("name",personEntity.getName());
			jObject.put("phoneNumber",personEntity.getPhoneNumber());
			jObject.put("town",personEntity.getTown());
			jObject.put("password",personEntity.getPassword());
		}
		
		return jObject;
	}
	
	public static JSONObject memberEntityToJSON(MemberEntity memberEntity){
		JSONObject jObject = new JSONObject();
		
		if(memberEntity != null) {
			jObject.put("id",memberEntity.getId());
			jObject.put("address",memberEntity.getAddress());
			jObject.put("postalCode",memberEntity.getPostalCode());
			jObject.put("cellNumber",memberEntity.getCellNumber());
			jObject.put("email",memberEntity.getEmail());
			jObject.put("forname",memberEntity.getForname());
			jObject.put("name",memberEntity.getName());
			jObject.put("phoneNumber",memberEntity.getPhoneNumber());
			jObject.put("town",memberEntity.getTown());
			jObject.put("mobileId",memberEntity.getMobileId());
			jObject.put("password",memberEntity.getPassword());
		
			jObject.put("wealthSheet",wealthSheetEntityToJSON(memberEntity.getWealthSheet()));
		
			List<Long> supplyDemandIds = new ArrayList<Long>();
			if(memberEntity.getSupplyDemand() != null) {
				for(SupplyDemandEntity supplyDemandEntity:memberEntity.getSupplyDemand()){
					supplyDemandIds.add(supplyDemandEntity.getId());
				}
			}
			jObject.put("supplyDemands",supplyDemandIds);
		
			JSONArray jArrayNotification = new JSONArray();
			if(memberEntity.getNotifications() != null) {
				for(NotificationEntity notificationEntity:memberEntity.getNotifications()){
					JSONObject jObjectNotification = notificationEntityToJSON(notificationEntity);
					jArrayNotification.put(jObjectNotification);
				}
			}
			jObject.put("notifications",jArrayNotification);
		}
		
		
		return jObject;
	}
	
	public static JSONObject messageEntityToJSON(MessageEntity messagenEntity){
		JSONObject jObject = new JSONObject();
		
		if(messagenEntity != null) {
			jObject.put("id",messagenEntity.getId());
			jObject.put("title",messagenEntity.getTitle());
			jObject.put("text",messagenEntity.getText());
			jObject.put("transmitterPerson",personEntityToJSON(messagenEntity.getTransmitterPerson()));
		}
		
		return jObject;
	}
	
	public static JSONObject wealthSheetEntityToJSON(WealthSheetEntity wealthSheetEntity){
		JSONObject jObject = new JSONObject();
		
		if(wealthSheetEntity != null) {
			jObject.put("id", wealthSheetEntity.getId());
			jObject.put("initialAccount",wealthSheetEntity.getInitialAccount());
			jObject.put("finalAccount",wealthSheetEntity.getFinalAccount());
			
			JSONArray jArray = new JSONArray();
			if(wealthSheetEntity.getTransactions() != null) {
				for (TransactionEntity transactionEntity:wealthSheetEntity.getTransactions()) {
					JSONObject transactionJSON = transactionEntityToJSON(transactionEntity);
					jArray.put(transactionJSON);
				}
			}
			jObject.put("transactions", jArray);
		}
		
		return jObject;
	}
	
	public static JSONObject transactionEntityToJSON(TransactionEntity transactionEntity){
		JSONObject jObject = new JSONObject();
		
		if(transactionEntity != null) {
			jObject.put("id", transactionEntity.getId());
			jObject.put("amount", transactionEntity.getAmount());
			jObject.put("creditorId", transactionEntity.getCreditorMember().getId());
			jObject.put("debtorId", transactionEntity.getDebtorMember().getId());
			jObject.put("supplyDemandId", transactionEntity.getSupplyDemand().getId());
		}
		
		return jObject;
	}
	
	public static JSONObject notificationEntityToJSON(NotificationEntity notificationEntity){
		JSONObject jObject = new JSONObject();
		
		if(notificationEntity != null) {
			jObject.put("id", notificationEntity.getId());
			jObject.put("title", notificationEntity.getTitle());
			jObject.put("text", notificationEntity.getText());
			
			JSONObject jObjectTopic = notificationTopicEntityToJSON(notificationEntity.getTopic());
			jObject.put("topic",jObjectTopic);
			
			JSONArray jArray = new JSONArray();
			if(notificationEntity.getMembersToNotify() != null) {
				for(MemberEntity memberEntity: notificationEntity.getMembersToNotify()) {
					JSONObject memberToNotifyJSON = memberEntityToJSON(memberEntity);
					jArray.put(memberToNotifyJSON);
				}
			}
			jObject.put("membersToNotify", jArray);
		}
		
		return jObject;
	}
	
	public static JSONObject notificationTopicEntityToJSON(NotificationTopicEntity notificationTopicEntity){
		JSONObject jObject = new JSONObject();
		if(notificationTopicEntity != null) {
			jObject.put("id", notificationTopicEntity.getId());
			jObject.put("topic", notificationTopicEntity.getTopic());
			jObject.put("category", notificationTopicEntity.getCategory());
			
			JSONObject jObjectPersonOriginEvent = personEntityToJSON(notificationTopicEntity.getPersonOriginEvent());
			jObject.put("personOriginEvent",jObjectPersonOriginEvent);
		}
		
		return jObject;
	}

}
