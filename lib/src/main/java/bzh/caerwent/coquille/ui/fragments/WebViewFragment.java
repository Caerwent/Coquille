package bzh.caerwent.coquille.ui.fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import bzh.caerwent.coquille.R;
import bzh.caerwent.coquille.utils.ConnectionUtils;

/**
 * Created by vincent on 14/01/2015.
 */
public class WebViewFragment extends Fragment {
    private static String TAG = WebViewFragment.class.getName();

    private static final String URL_KEY = "url_key";

    private String mUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mNoDataView;


    public WebViewFragment(final String pUrl) {
        mUrl = pUrl;

    }

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

        View rootView = inflater.inflate(R.layout.webview_layout, null);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(URL_KEY);
        }

        mWebView = (WebView) rootView.findViewById(R.id.webview);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.webview_progressbar);
        mNoDataView = rootView.findViewById(R.id.no_data);

        if (mUrl.startsWith("file") || ConnectionUtils.isConnected(getActivity())) {
            prepareWebView();
        } else {
            mNoDataView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL_KEY, mUrl);
    }

    /**
     * prepare the webview and set parameters
     */
    private void prepareWebView() {


        if (mUrl == null || mWebView == null)
            return;

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.loadUrl(mUrl);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mWebView != null) {
            mWebView.removeAllViews();
        }
    }

    /**
     * Handle back command
     *
     * @return WebView
     */
    public boolean doBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mProgressBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
            mNoDataView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mNoDataView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mNoDataView.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

        }
    }

}
