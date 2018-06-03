package domain;

import java.util.ArrayList;

public class ChatConversation
{
    private String userId1;
    private String userId2;
    private ArrayList<Message> messages = new ArrayList<>();

    public ChatConversation()
    {}

    public ChatConversation(String userId1, String userId2)
    {
        this.setUserId1(userId1);
        this.setUserId2(userId2);
    }

    public String getUserId1()
    {
        return userId1;
    }

    public void setUserId1(String userId1)
    {
        this.userId1 = userId1;
    }

    public String getUserId2()
    {
        return userId2;
    }

    public void setUserId2(String userId2)
    {
        this.userId2 = userId2;
    }

    public void addMsg(Message msg)
    {
        if(msg == null)
        {
            throw new DomainException("Message cannot be empty");
        }
        messages.add(msg);
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

}
