package domain;

import db.ChatConversationsListRepository;
import db.ChatConversationsListRepositoryStub;

public class ChatConversationsListService
{
    private ChatConversationsListRepository chatConversationsListRepository = new ChatConversationsListRepositoryStub();

    public ChatConversationsListService()
    {
    }

    public ChatConversationsList getChatConversationListById(String userId)
    {
        return chatConversationsListRepository.getChatConversationByOneId(userId);
    }

    public ChatConversationsList getChatConversationList()
    {
        return chatConversationsListRepository.getAll();
    }

    public void addChatConversation(ChatConversation chatConversation)
    {
        chatConversationsListRepository.addChatConversation(chatConversation);
    }
}
