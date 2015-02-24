package bzh.caerwent.coquille.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vincent on 13/01/2015.
 */
public class Section {

    @SerializedName("sectionName")
    private String mSectionName;
    @SerializedName("sectionBody")
    private String mSectionBody;

    public String getSectionName() {
        return mSectionName;
    }

    public void setSectionName(String aSectionName) {
        mSectionName = aSectionName;
    }

    public String getSectionBody() {
        return mSectionBody;
    }

    public void setSectionBody(String aSectionBody) {
        mSectionBody = aSectionBody;
    }

    public interface SectionList extends List<Section> {
    }
}
