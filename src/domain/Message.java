package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message
{
    private String dateTime;
    private String sender;
    private String msg;

    public Message(String sender, String msg)
    {
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.sender = sender;
        this.msg = msg;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    private void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }
}
