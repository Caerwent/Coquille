package bzh.caerwent.coquille.ui.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import bzh.caerwent.coquille.R;
import bzh.caerwent.coquille.model.data.AboutData;
import bzh.caerwent.coquille.model.data.Section;
import bzh.caerwent.coquille.utils.AppUtils;
import bzh.caerwent.coquille.utils.UIUtils;

/**
 * Created by vincent on 13/01/2015.
 */
public class AboutFragment extends DialogFragment {

    public static String TAG = AboutFragment.class.getName();

    private AboutData mAboutData;
    private LayoutInflater mInflater;
    private LockedFragmentListener mLockedFragmentListener;

    View mAboutView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null)
            menu.clear();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mLockedFragmentListener != null)
            mLockedFragmentListener.onLockStart();
    }

    @Override
    public void onStop() {
        if (mLockedFragmentListener != null)
            mLockedFragmentListener.onLockStop();
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mAboutView = mInflater.inflate(
                R.layout.about_layout, container, false);
        TextView aboutVersion = (TextView) mAboutView.findViewById(R.id.about_version);
        aboutVersion.setText(getResources().getString(R.string.about_version) + " " + AppUtils.getApplicationVersion(getActivity()));
        TextView aboutRevision = (TextView) mAboutView.findViewById(R.id.about_revision);
        aboutRevision.setText(getResources().getString(R.string.about_revision) + " " + AppUtils.getApplicationRevision(getActivity()));


        View aboutDataView = mAboutView.findViewById(R.id.about_data);

        Gson gson = new Gson();
        try {
            InputStream aboutStream = getActivity().getAssets().open("about_content.json", AssetManager.ACCESS_BUFFER);
            Reader reader = new InputStreamReader(aboutStream);
            mAboutData = gson.fromJson(reader, AboutData.class);
            update();
        } catch (Exception e) {
            Log.e(TAG, "exception when parsing about content");
        }


        return mAboutView;
    }

    private void update() {
        TextView aboutCopyright = (TextView) mAboutView.findViewById(R.id.about_copyright);
        aboutCopyright.setText(mAboutData.getCopyright());

        LinearLayout aboutLayout = (LinearLayout) mAboutView.findViewById(R.id.about_data);

        UIUtils.inflateSection(mInflater, aboutLayout, (List<Section>) mAboutData.getSections());

    }

    public void setLockedFragmentListener(LockedFragmentListener aListener) {
        mLockedFragmentListener = aListener;
    }
}
