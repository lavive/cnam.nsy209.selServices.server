package shared.dto;

public enum EnumSupplyDemandDto {
	SUPPLY("supply"),
	DEMAND("demand");
	
	String wording;
	EnumSupplyDemandDto(String wording){
		this.wording = wording;
	}
	
	public String getWording(){
		return this.wording;
	}
	
	public static EnumSupplyDemandDto getByWording(String wording){
		for(EnumSupplyDemandDto enumAttribute : values()){
			if(enumAttribute.getWording().equals(wording))
				return enumAttribute;
		}
		
		return null;
	}
}
