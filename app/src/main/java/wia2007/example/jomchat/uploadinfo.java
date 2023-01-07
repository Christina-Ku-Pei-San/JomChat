package wia2007.example.jomchat;

public class uploadinfo {

    public String userName;
    public String imageContent;
    public String imageURL;

    public uploadinfo() {}

    public uploadinfo(String name,String imageContent, String url) {
        this.userName = name;
        this.imageContent= imageContent;
        this.imageURL = url;
    }

    public String getImageContent() {
        return imageContent;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageURL() {
        return imageURL;
    }
}