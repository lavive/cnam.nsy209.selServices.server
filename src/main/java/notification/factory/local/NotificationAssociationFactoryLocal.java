package notification.factory.local;

import java.util.Map;

import javax.ejb.Local;

import dao.constants.EnumAssociationAttribute;

@Local
public interface NotificationAssociationFactoryLocal extends NotificationFactory {

	public void setMapAttributeValue(Map<EnumAssociationAttribute,String> mapAttributeValue);
}
