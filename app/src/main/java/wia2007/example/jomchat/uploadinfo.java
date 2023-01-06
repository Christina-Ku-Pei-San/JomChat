package wia2007.example.jomchat;

public class uploadinfo {

    public String userName;
    public String imageContent;
    public String imageURL;
    String userURL;

    public uploadinfo(){}

//    public uploadinfo(String name,String imageContent, String url) {
//        this.userName = name;
//        this.imageContent= imageContent;
//        this.imageURL = url;
//    }

    public uploadinfo(String userName, String imageContent, String imageURL, String userURL) {
        this.userName = userName;
        this.imageContent = imageContent;
        this.imageURL = imageURL;
        this.userURL = userURL;
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

    public String getUserURL() { return userURL; }
}