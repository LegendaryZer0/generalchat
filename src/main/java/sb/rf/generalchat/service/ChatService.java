package sb.rf.generalchat.service;

import sb.rf.generalchat.model.Chats;

public interface ChatService {
    String getChatroomState(Long id_from, Long id_to);

    void setChatroomState(Long id_from, Long id_to, Chats.State state);
}
