package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.SupplyDemandEntity;

@Local
public interface SupplyDemandDaoLocal extends CommonDao<SupplyDemandEntity>{
	
	public List<SupplyDemandEntity> getAllSupplyDemand();
	
	public List<SupplyDemandEntity> getSuppliesDemands(String type);

}
