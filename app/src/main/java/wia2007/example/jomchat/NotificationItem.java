package wia2007.example.jomchat;

public class NotificationItem {

    private String mpostID;
    private String mImageResource;
    private String mImageResource2;
    private String mImageResource3;
    private String mText1;
    private String mText2;
    private String mText3;

    public NotificationItem(String imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public NotificationItem(String mpostID, String mImageResource, String mText1, String mText2) {
        this.mpostID = mpostID;
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public NotificationItem(String mpostID, String mImageResource, String mText1, String mText2, String mImageResource2) {
        this.mpostID = mpostID;
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mImageResource2 = mImageResource2;
    }

    public NotificationItem(String mpostID, String mImageResource, String mImageResource2, String mImageResource3, String mText1, String mText2, String mText3) {
        this.mpostID = mpostID;
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mImageResource2 = mImageResource2;
        this.mText3 = mText3;
        this.mImageResource3 = mImageResource3;
    }

    public void changeText1(String text) {
        mText1 = text;
    }

    public String getPostID() {
        return mpostID;
    }

    public String getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getImageResource2() {
        return mImageResource2;
    }

    public String getText3() {
        return mText3;
    }

    public String getImageResource3() {
        return mImageResource3;
    }
}
