package com.example.raintree.signature;

/**
 * Created by rainTree on 2015-12-03.
 */
public class SignListItem {
     // Data array
    private Object[] mData;

     // Item ID
    private String mId;

    /**
     * Initialize with icon and data array
     */
    public SignListItem(String itemId, Object[] obj) {
        mId = itemId;
        mData = obj;
    }

    /**
     * Initialize with strings
     */
    public SignListItem(String signId,  String uri_signImg, String lastName, String firstName, String date)
    {
        mId = signId;
        mData = new Object[4];
        mData[0] = uri_signImg;
        mData[1] = lastName;
        mData[2] = firstName;
        mData[3] = date;

    }

    public String getId() {
        return mId;
    }

    public void setId(String itemId) {
        mId = itemId;
    }

    /**
     * Get data array
     */
    public Object[] getData() {
        return mData;
    }

    /**
     * Get data
     */
    public Object getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    /**
     * Set data array
     */
    public void setData(String[] obj) {
        mData = obj;
    }

}

