package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.AssociationEntity;
import dao.entity.MemberEntity;

/**
 * Bean to manage AssociationEntity persistance
 * 
 * @author lavive
 *
 */

@Local
public interface AssociationDaoLocal extends CommonDao<AssociationEntity> {
	
	AssociationEntity getAssociation();
	
	List<MemberEntity> checkIdAssociation(String login, String password, int number);

}
