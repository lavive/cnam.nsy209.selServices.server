package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.MessageEntity;
import dao.entity.PersonEntity;

@Local
public interface MessageDaoLocal extends CommonDao<MessageEntity>{
	
	//public List<MessageEntity> getMessageByState(boolean state);
	
	public List<MessageEntity> getMessages();
	
	public List<MessageEntity> getMessages(PersonEntity personEntity);
	
	public void deleteMessage();
	
	public void deleteMessages(List<MessageEntity> messageEntities);
	

}