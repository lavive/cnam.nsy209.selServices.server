package dao.local;

import java.util.Date;

import dao.entity.SelServicesEntity;
import notification.factory.local.NotificationFactory;

/**
 * Interface to factorize daobean with the same methods
 * 
 * @author lavive
 *
 */

public interface CommonDao<ENTITY extends SelServicesEntity> {
	
	public void create(ENTITY entity);
	
	public ENTITY get(long id);
	
	public void update(ENTITY entity);
	
	public void delete(long id);
	
	public NotificationFactory getNotificationFactory();
	
	public Date lastDateUpdate();

}
