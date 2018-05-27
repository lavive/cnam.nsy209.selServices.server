package notification.factory.local;

import java.util.Map;

import javax.ejb.Local;

import dao.constants.EnumAssociationAttribute;

/**
 * Bean to create notification concerning association
 * 
 * @author lavive
 *
 */

@Local
public interface NotificationAssociationFactoryLocal extends NotificationFactory {

	public void setMapAttributeValue(Map<EnumAssociationAttribute,String> mapAttributeValue);
}
