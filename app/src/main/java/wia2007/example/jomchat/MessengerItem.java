package wia2007.example.jomchat;

public class MessengerItem {

    private String mImageResource;
    private String mText1;

    public MessengerItem() {
    }

    public MessengerItem(String imageResource, String text1) {
        mImageResource = imageResource;
        mText1 = text1;
    }

    public void changeText1(String text) {
        mText1 = text;
    }

    public String getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

}
