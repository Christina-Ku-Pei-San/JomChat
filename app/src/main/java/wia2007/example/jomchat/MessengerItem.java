package wia2007.example.jomchat;

public class MessengerItem {

    private int mImageResource;
    private String mText1;

    public MessengerItem() {
    }

    public MessengerItem(int imageResource, String text1) {
        mImageResource = imageResource;
        mText1 = text1;
    }

    public void changeText1(String text) {
        mText1 = text;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

}
