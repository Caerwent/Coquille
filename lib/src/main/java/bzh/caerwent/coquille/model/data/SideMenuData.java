package bzh.caerwent.coquille.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vincent on 13/01/2015.
 */
public class SideMenuData {
    @SerializedName("items")
    private List<? extends SideMenuItem> mItems;

    public List<? extends SideMenuItem> getItems() {
        return mItems;
    }
}
