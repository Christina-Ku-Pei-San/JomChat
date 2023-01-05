package wia2007.example.jomchat;

public class MessageItem {

    private String message;
    private long timestamp;
    private String currenttime;
    private String senderORreceiver;
    private String username;
    private String receiverusername;

    public MessageItem() {
    }

    public MessageItem(String message, long timestamp, String currenttime, String senderORreceiver, String username, String receiverusername) {
        this.message = message;
        this.timestamp = timestamp;
        this.currenttime = currenttime;
        this.senderORreceiver = senderORreceiver;
        this.username = username;
        this.receiverusername = receiverusername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public String getSenderORreceiver() { return senderORreceiver; }

    public void setSenderORreceiver(String senderORreceiver) { this.senderORreceiver = senderORreceiver; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getReceiverusername() { return receiverusername; }

    public void setReceiverusername(String receiverusername) { this.receiverusername = receiverusername; }
}
