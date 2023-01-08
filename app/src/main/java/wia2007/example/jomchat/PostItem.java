package wia2007.example.jomchat;

import android.net.Uri;

public class PostItem {

    private String mpostID;
    //    private int mImageResource;
    private String  mImageResource;
    private String mText1;
    private String mText2;
    //    private int mImageResource2;
    private String mImageResource2;

//    public PostItem(int imageResource, String text1, String text2, int imageResource2) {
//        mImageResource = imageResource;
//        mText1 = text1;
//        mText2 = text2;
//        mImageResource2 = imageResource2;
//
//    }

    public PostItem(String postID, String imageResource, String text1, String text2, String imageResource2) {
        mpostID = postID;
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mImageResource2 = imageResource2;
    }

    public PostItem(String mText1, String mText2, String mImageResource2) {
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mImageResource2 = mImageResource2;
    }
    //    public PostItem(int imageResource, String text1, String text2) {
//        mImageResource = imageResource;
//        mText1 = text1;
//        mText2 = text2;
//    }

    public void changeText1(String text) {
        mText1 = text;
    }

//    public int getImageResource() {
//        return mImageResource;
//    }

    public String getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

//    public int getImageResource2() {
//        return mImageResource2;
//    }

    public String getImageResource2() {
        return mImageResource2;
    }

    public String getPostID() {
        return mpostID;
    }
}