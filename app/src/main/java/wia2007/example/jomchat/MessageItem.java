package wia2007.example.jomchat;

public class MessageItem {

    String message;
//    String senderId;
//    long timestamp;
    String currenttime;
    String  senderORreceiver;

//    public MessageItem(String message, String senderId, long timestamp, String currenttime) {
//        this.message = message;
//        this.senderId = senderId;
//        this.timestamp = timestamp;
//        this.currenttime = currenttime;
//    }

    public MessageItem(String message, String currenttime, String senderORreceiver) {
        this.message = message;
        this.currenttime = currenttime;
        this.senderORreceiver = senderORreceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(String senderId) {
//        this.senderId = senderId;
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(long timestamp) {
//        this.timestamp = timestamp;
//    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public String getSenderORreceiver() { return senderORreceiver; }

    public void setSenderORreceiver(String senderORreceiver) { this.senderORreceiver = senderORreceiver; }
}
