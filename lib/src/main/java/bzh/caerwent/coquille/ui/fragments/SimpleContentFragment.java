package bzh.caerwent.coquille.ui.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import bzh.caerwent.coquille.CoquilleActivity;
import bzh.caerwent.coquille.R;
import bzh.caerwent.coquille.business.net.SimpleContentRequest;
import bzh.caerwent.coquille.model.data.Section;
import bzh.caerwent.coquille.utils.ConnectionUtils;
import bzh.caerwent.coquille.utils.UIUtils;

/**
 * Created by vincent on 13/01/2015.
 */
public class SimpleContentFragment extends Fragment implements RequestListener<Section.SectionList> {
    private SpiceManager contentManager = new SpiceManager(GsonSpringAndroidSpiceService.class);

    private static String TAG = SimpleContentFragment.class.getName();

    private List<Section> mSections;
    private LayoutInflater mInflater;

    private ViewGroup mRootView;
    private ProgressBar mProgress;
    private View mNoDataView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View layout = mInflater.inflate(
                R.layout.simple_content_layout, container, false);

        mProgress = (ProgressBar) layout.findViewById(R.id.progress);
        mRootView = (ViewGroup) layout.findViewById(R.id.container);
        mNoDataView = layout.findViewById(R.id.no_data);
        if (mSections != null)
            update(mSections);

        return layout;
    }

    @Override
    public void onStart() {
        contentManager.start(CoquilleActivity.getInstance());
        super.onStart();
    }

    @Override
    public void onStop() {
        contentManager.shouldStop();
        super.onStop();
    }

    private void setIsLoading(boolean aIsLoading) {
        mProgress.setVisibility(aIsLoading ? View.VISIBLE : View.GONE);
        mRootView.setVisibility(aIsLoading ? View.GONE : View.VISIBLE);
        mNoDataView.setVisibility(View.GONE);
    }

    public void update(URL aUrl, Context aContext) {

        if (!ConnectionUtils.isConnected(aContext)) {
            setIsLoading(false);
            mNoDataView.setVisibility(View.VISIBLE);
            return;
        }
        if (mRootView != null)
            setIsLoading(true);
        SimpleContentRequest request = new SimpleContentRequest(aUrl);
        contentManager.execute(request, request.createCacheKey(), DurationInMillis.ONE_HOUR, this);
    }

    public void update(String aAsset, Context aContext) {

        if (mRootView != null)
            setIsLoading(true);
        Gson gson = new Gson();
        try {
            InputStream aboutStream = aContext.getAssets().open(aAsset, AssetManager.ACCESS_BUFFER);
            Reader reader = new InputStreamReader(aboutStream);
            Type listType = new TypeToken<List<Section>>() {
            }.getType();
            mSections = (List<Section>) gson.fromJson(reader, listType);

        } catch (Exception e) {
            Log.e(TAG, "exception when parsing section content " + e.getMessage());
        }

        update(mSections);
    }

    public void update(List<Section> aSections) {

        mSections = aSections;

        if (mRootView != null) {
            UIUtils.inflateSection(mInflater, mRootView, mSections);
            setIsLoading(false);
        }

    }


    @Override
    public void onRequestFailure(SpiceException e) {
        setIsLoading(false);
        mNoDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(Section.SectionList aList) {

        update(aList);

    }

}
