package bzh.caerwent.coquille.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vincent on 13/01/2015.
 */
public class AboutData {
    @SerializedName("copyrightMention")
    private String mCopyright;
    @SerializedName("sections")
    private List<? extends Section> mSections;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String aCopyright) {
        mCopyright = aCopyright;
    }

    public List<? extends Section> getSections() {
        return mSections;
    }

    public void setSections(List<? extends Section> aSections) {
        mSections = aSections;
    }

}
