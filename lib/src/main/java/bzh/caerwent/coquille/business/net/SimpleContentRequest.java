package bzh.caerwent.coquille.business.net;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import java.net.URL;

import bzh.caerwent.coquille.model.data.Section;
import bzh.caerwent.coquille.utils.AppUtils;

/**
 * Created by vincent on 03/02/2015.
 */
public class SimpleContentRequest extends SpringAndroidSpiceRequest {

    private static final String TAG = SimpleContentRequest.class.getName();

    private URL mUrl;

    public SimpleContentRequest(URL aUrl) {
        super(Section.SectionList.class);
        mUrl = aUrl;
    }

    @Override
    public Section.SectionList loadDataFromNetwork() throws Exception {

        return getRestTemplate().getForObject(mUrl.toString(), Section.SectionList.class);
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
