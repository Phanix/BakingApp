package hantaro.com.bakingapp;

import java.io.Serializable;

public class Step implements Serializable {

    public Step(){

    }
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailUrl;
    private String mId;

    public Step(String shortDescription, String description, String videoURL, String thumbnailUrl, String id) {
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailUrl = thumbnailUrl;
        mId  = id;

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

}