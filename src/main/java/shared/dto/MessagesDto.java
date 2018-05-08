package shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessagesDto implements Serializable {

    /**
     * For checking version
     */
    private static final long serialVersionUID = 1L;

    private List<MessageDto> messages = new ArrayList<MessageDto>();

    /* getter and setter */

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }
}
