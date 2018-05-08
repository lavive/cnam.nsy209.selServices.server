package notification.factory.local;

import javax.ejb.Local;

import dao.entity.MemberEntity;
import dao.entity.SupplyDemandEntity;

@Local
public interface NotificationSupplyDemandFactoryLocal extends NotificationFactory {

	public void setMemberOrigin(MemberEntity member);
		
	public void setNewDemand(SupplyDemandEntity demand);
	
	public void setNewSupply(SupplyDemandEntity supply);
}
