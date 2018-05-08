package notification.constants;

public enum EnumTopicNotification {
	NEW_MEMBER("Nouveau membre"),
	MEMBER_LEAVING("Départ d’un Membre"),
	NEW_DEMAND("Nouvelle demande"),
	NEW_SUPPLY("Nouvelle offre"),
	NEW_MESSAGE("Nouveau Message"),
	WELCOME_MESSAGE("Bienvenue"),
	ASSOCIATION_CHANGE("Changement coordonnées association");
	
	String wording;
	EnumTopicNotification(String wording){
		this.wording = wording;
	}
	
	public String getWording(){
		return this.wording;
	}
	
	public static EnumTopicNotification getByWording(String wording){
		for(EnumTopicNotification enumTopic : values()){
			if(enumTopic.getWording().equals(wording))
				return enumTopic;
		}
		
		return null;
	}
}
