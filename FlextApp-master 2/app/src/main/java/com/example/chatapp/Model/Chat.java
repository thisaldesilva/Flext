package com.example.chatapp.Model;

public class Chat
{
    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;
    private String emote;

    public Chat(String sender, String receiver, String message, boolean isseen, String emote)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.emote = emote;
    }

    public Chat()
    {
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isIsseen()
    {
        return isseen;
    }

    public void setIsseen(boolean isseen)
    {
        this.isseen = isseen;
    }

    public String getEmote()
    {
        return emote;
    }

    public void setEmote(String emote)
    {
        this.emote = emote;
    }
}
