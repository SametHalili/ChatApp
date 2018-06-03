package db;

import domain.ChatConversation;
import domain.ChatConversationsList;

public class ChatConversationsListRepositoryStub implements ChatConversationsListRepository
{
    private ChatConversationsList chatConversationsList = new ChatConversationsList();

    public ChatConversationsListRepositoryStub()
    { }

    @Override
    public void addChatConversation(ChatConversation chatConversation)
    {
        chatConversationsList.addChatConversation(chatConversation);
    }

    @Override
    public ChatConversation getChatConversation(String userId1, String userId2)
    {
        return chatConversationsList.getChatConversation(userId1, userId2);
    }

    @Override
    public ChatConversationsList getChatConversationByOneId(String userId)
    {
        return chatConversationsList.getChatConversationsByOneId(userId);
    }

    @Override
    public ChatConversationsList getAll()
    {
        return chatConversationsList;
    }
}
