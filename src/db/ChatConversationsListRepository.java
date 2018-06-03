package db;

import domain.ChatConversation;
import domain.ChatConversationsList;

public interface ChatConversationsListRepository
{
    void addChatConversation(ChatConversation chatConversation);
    ChatConversation getChatConversation(String userId1, String userId2);
    ChatConversationsList getChatConversationByOneId(String userId);
    ChatConversationsList getAll();
}
