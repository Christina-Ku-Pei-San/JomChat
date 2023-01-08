package wia2007.example.jomchat;

public class CommentItem {

    private String commenterURL;
    private String commenterUsername;
    private String comment;

    public CommentItem(String commenterURL, String commenterUsername, String comment) {
        this.commenterURL = commenterURL;
        this.commenterUsername = commenterUsername;
        this.comment = comment;
    }

    public String getCommenterURL() {
        return commenterURL;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public String getComment() {
        return comment;
    }
}
