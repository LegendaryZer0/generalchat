package sb.rf.generalchat.service;

import sb.rf.generalchat.model.Chats;

import java.util.UUID;

public interface ChatService {
    String getChatroomState(Long id_from, Long id_to);
    public void setChatroomState(Long id_from, Long id_to, Chats.State state);
}
