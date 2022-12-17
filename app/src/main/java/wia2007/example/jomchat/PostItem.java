package wia2007.example.jomchat;

public class PostItem {

    private int mImageResource;
    private String mText1;
    private String mText2;
    private int mImageResource2;

    public PostItem(int imageResource, String text1, String text2, int imageResource2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mImageResource2 = imageResource2;

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

    public String getText2() {
        return mText2;
    }
    public int getImageResource2() {
        return mImageResource2;
    }

}