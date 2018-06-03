package domain;

import java.util.ArrayList;

public class ChatConversationsList
{
    private ArrayList<ChatConversation> chatConversations = new ArrayList<>();

    public ChatConversationsList()
    {

    }

    public ArrayList<ChatConversation> getChatConversations()
    {
        return chatConversations;
    }

    public void addChatConversation(ChatConversation chatConversation)
    {
        chatConversations.add(chatConversation);
    }

    public ChatConversation getChatConversation(String userid1, String userid2) throws DomainException
    {
        for(ChatConversation chatConversation: chatConversations)
        {
            if(((chatConversation.getUserId1().equals(userid1)) && chatConversation.getUserId2().equals(userid2))
                    || (chatConversation.getUserId1().equals(userid2) && chatConversation.getUserId2().equals(userid1)))
            {
                return chatConversation;
            }
        }
        throw new DomainException("Chat does not exist");
    }

    public ChatConversationsList getChatConversationsByOneId(String userId)
    {
        ChatConversationsList chatConversationsList = new ChatConversationsList();
        for(ChatConversation chatConversation : chatConversations)
        {
            if (chatConversation.getUserId1().equals(userId) || chatConversation.getUserId2().equals(userId))
            {
                chatConversationsList.addChatConversation(chatConversation);
            }
        }
        return chatConversationsList;
    }
}
