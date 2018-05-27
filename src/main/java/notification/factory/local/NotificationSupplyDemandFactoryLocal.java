package notification.factory.local;

import javax.ejb.Local;

import dao.entity.MemberEntity;
import dao.entity.SupplyDemandEntity;

/**
 * Bean to create notification concerning supply and demand
 * 
 * @author lavive
 *
 */

@Local
public interface NotificationSupplyDemandFactoryLocal extends NotificationFactory {

	public void setMemberOrigin(MemberEntity member);
		
	public void setNewDemand(SupplyDemandEntity demand);
	
	public void setNewSupply(SupplyDemandEntity supply);
}
