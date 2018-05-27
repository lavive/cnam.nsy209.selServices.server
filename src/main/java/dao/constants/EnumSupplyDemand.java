package dao.constants;

/**
 * Enumeration picturing supply or demand attributes for SupplyDemandEntity
 * 
 * @author lavive
 *
 */

public enum EnumSupplyDemand {
	SUPPLY("supply"),
	DEMAND("demand");
	
	String wording;
	EnumSupplyDemand(String wording){
		this.wording = wording;
	}
	
	public String getWording(){
		return this.wording;
	}
	
	public static EnumSupplyDemand getByWording(String wording){
		for(EnumSupplyDemand enumAttribute : values()){
			if(enumAttribute.getWording().equals(wording))
				return enumAttribute;
		}
		
		return null;
	}
}