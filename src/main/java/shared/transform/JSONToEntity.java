package shared.transform;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.entity.MemberEntity;
import dao.entity.MessageEntity;
import dao.entity.PersonEntity;
import dao.entity.SupplyDemandEntity;
import dao.entity.TransactionEntity;
import dao.entity.WealthSheetEntity;

public class JSONToEntity {
	/* Not working + put key string in final static String */ 
	
	public static SupplyDemandEntity supplyDemandJSONToEntity(JSONObject jObject ){
		SupplyDemandEntity supplyDemandEntity = new SupplyDemandEntity();
		if(jObject != null) {
			supplyDemandEntity.setId(jObject.getLong("id"));
			supplyDemandEntity.setCategory(jObject.getString("category"));
			supplyDemandEntity.setTitle(jObject.getString("title"));
			supplyDemandEntity.setType(jObject.getString("type"));
			supplyDemandEntity.setMember(memberJSONToEntity(jObject.getJSONObject("member")));
		}
		
		return supplyDemandEntity;
	}
	
	public static MemberEntity memberJSONToEntity(JSONObject jObject ){
		MemberEntity memberEntity = new MemberEntity();
		
		if(jObject != null) {
			memberEntity.setId(jObject.getLong("id"));
			memberEntity.setAddress(jObject.getString("address"));
			memberEntity.setPostalCode(jObject.getString("postalCode"));
			memberEntity.setCellNumber(jObject.getString("cellNumber"));
			memberEntity.setEmail(jObject.getString("email"));
			memberEntity.setForname(jObject.getString("forname"));
			memberEntity.setName(jObject.getString("name"));
			memberEntity.setPhoneNumber(jObject.getString("phoneNumber"));
			memberEntity.setTown(jObject.getString("town"));
			memberEntity.setMobileId(jObject.getString("mobileId"));
			memberEntity.setPassword(jObject.getString("password"));
			
			memberEntity.setWealthSheet(wealthSheetJSONToEntity(jObject.getJSONObject("wealthSheet")));
			
//			JSONArray jArray = jObject.getJSONArray("supplyDemand");
//			List<SupplyDemandEntity> supplyDemands = new ArrayList<SupplyDemandEntity>();
//			for(int i = 0 ; i < jArray.length(); i++){
//				SupplyDemandEntity supplyDemandEntity = supplyDemandJSONToEntity((JSONObject) jArray.get(i));
//				supplyDemands.add(supplyDemandEntity);
//			}
//			memberEntity.setSupplyDemand(supplyDemands);
		}
		
		
		return memberEntity;
	}
	
	public static WealthSheetEntity wealthSheetJSONToEntity(JSONObject jObject ){
		WealthSheetEntity wealthSheetEntity = new WealthSheetEntity();
		
		if(jObject != null) {
			wealthSheetEntity.setId(jObject.getLong("id"));
			wealthSheetEntity.setInitialAccount(jObject.getBigDecimal("initialAccount"));
			wealthSheetEntity.setFinalAccount(jObject.getBigDecimal("finalAccount"));	
			
			//wealthSheetEntity.setMember(memberJSONToEntity(jObject.getJSONObject("member")));
			
			JSONArray jArray = jObject.getJSONArray("transactions");
			List<TransactionEntity> transactions = new ArrayList<TransactionEntity>();
			for(int i = 0 ; i < jArray.length(); i++){
				TransactionEntity transaction = transactionJSONToEntity((JSONObject) jArray.get(i));
				transactions.add(transaction);
			}
			
			wealthSheetEntity.setTransactions(transactions);
		}
		
		return wealthSheetEntity;
	}
	
	public static TransactionEntity transactionJSONToEntity(JSONObject jObject){
		TransactionEntity transactionEntity = new TransactionEntity();
		if(jObject != null) {
			transactionEntity.setId(jObject.getLong("id"));
			transactionEntity.setAmount(jObject.getBigDecimal("amount"));
			
//			transactionEntity.setCreditorMember(memberJSONToEntity(jObject.getJSONObject("creditorMember")));
//			
//			transactionEntity.setDebtorMember(memberJSONToEntity(jObject.getJSONObject("debtorMember")));
//			
//			transactionEntity.setSupplyDemand(supplyDemandJSONToEntity(jObject.getJSONObject("supplyDemand")));
		}
		
		return transactionEntity;
	}
	
	public static MessageEntity messageJSONToEntity(JSONObject jObject){
		MessageEntity messageEntity = new MessageEntity();
		
		if(jObject != null) {
			messageEntity.setId(jObject.getLong("id"));
			messageEntity.setText(jObject.getString("text"));
			messageEntity.setTitle(jObject.getString("title"));
			messageEntity.setTransmitterPerson(personJSONToEntity(jObject.getJSONObject("transmitterPerson")));
		}
		
		return messageEntity;
	}
	
	public static PersonEntity personJSONToEntity(JSONObject jObject ){
		PersonEntity personEntity = new PersonEntity();
		
		if(jObject != null) {
			personEntity.setId(jObject.getLong("id"));
			personEntity.setAddress(jObject.getString("address"));
			personEntity.setPostalCode(jObject.getString("postalcode"));
			personEntity.setCellNumber(jObject.getString("cellNumber"));
			personEntity.setEmail(jObject.getString("email"));
			personEntity.setName(jObject.getString("name"));
			personEntity.setPhoneNumber(jObject.getString("phoneNumber"));
			personEntity.setTown(jObject.getString("town"));
			personEntity.setPassword(jObject.getString("password"));
		}
		
		
		return personEntity;
	}
	


}
