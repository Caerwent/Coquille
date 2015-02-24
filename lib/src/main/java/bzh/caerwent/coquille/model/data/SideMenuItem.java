package bzh.caerwent.coquille.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vincent on 13/01/2015.
 */
public class SideMenuItem {
    public static int TYPE_HEADER = 0;
    public static int TYPE_URL = 1;
    public static int TYPE_CONTENT = 2;


    @SerializedName("label")
    private String mLabel;

    public String getLabel() {
        return mLabel;
    }

    @SerializedName("type")
    private int mType;

    public int getType() {
        return mType;
    }

    @SerializedName("data")
    private String mData;

    public String getData() {
        return mData;
    }

    @SerializedName("remote")
    private boolean mIsRemote;

    public boolean isRemote() {
        return mIsRemote;
    }
}
