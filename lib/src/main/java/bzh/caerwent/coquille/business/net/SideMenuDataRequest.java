package bzh.caerwent.coquille.business.net;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import java.net.URL;

import bzh.caerwent.coquille.model.data.SideMenuData;
import bzh.caerwent.coquille.utils.AppUtils;

/**
 * Created by vincent on 03/02/2015.
 */
public class SideMenuDataRequest extends SpringAndroidSpiceRequest {

    private static final String TAG = SideMenuDataRequest.class.getName();


    private URL mUrl;

    public SideMenuDataRequest(URL aUrl) {
        super(SideMenuData.class);
        mUrl = aUrl;
    }

    @Override
    public SideMenuData loadDataFromNetwork() throws Exception {

        return getRestTemplate().getForObject(mUrl.toString(), SideMenuData.class);
    }

    /**
     * This method generates a unique cache key for this request. In this case our cache key depends just on the
     * keyword.
     *
     * @return
     */
    public String createCacheKey() {
        return AppUtils.encodeMD5(mUrl.toString());
    }
}
