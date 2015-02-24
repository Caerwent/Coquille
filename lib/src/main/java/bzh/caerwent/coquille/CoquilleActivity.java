package bzh.caerwent.coquille;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;

import java.net.MalformedURLException;
import java.net.URL;

import bzh.caerwent.coquille.model.data.SideMenuItem;
import bzh.caerwent.coquille.ui.fragments.AboutFragment;
import bzh.caerwent.coquille.ui.fragments.LockedFragmentListener;
import bzh.caerwent.coquille.ui.fragments.NavigationDrawerFragment;
import bzh.caerwent.coquille.ui.fragments.SimpleContentFragment;
import bzh.caerwent.coquille.ui.fragments.WebViewFragment;


public class CoquilleActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LockedFragmentListener {

    private static final String TAG = CoquilleActivity.class.getName();

    private static CoquilleActivity sInstance;


    public static CoquilleActivity getInstance() {
        return sInstance;
    }

    protected Handler mHandler;
    protected boolean hasPreviousBack = false;
    protected Toast mToast;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sInstance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mHandler = new Handler();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position, SideMenuItem aItem) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = null;

        if (aItem == null)
            return;

        if (aItem.getType() == SideMenuItem.TYPE_CONTENT) {
            frag = new SimpleContentFragment();
            if (aItem.isRemote()) {
                try {
                    ((SimpleContentFragment) frag).update(new URL(aItem.getData()), this);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "malformed url " + aItem.getData() + " " + e.getMessage());
                }
            } else {
                ((SimpleContentFragment) frag).update(aItem.getData(), this);
            }
        } else if (aItem.getType() == SideMenuItem.TYPE_URL) {
            frag = new WebViewFragment(aItem.getData());
        }

        if (frag != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_out_inverse, R.anim.slide_in_inverse);

            transaction.replace(R.id.container, frag);
            transaction.commit();
            mTitle = aItem.getLabel();
            getSupportActionBar().setTitle(aItem.getLabel());
        }

    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AboutFragment mlFragment = new AboutFragment();
            mlFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            mlFragment.show(getSupportFragmentManager(), AboutFragment.TAG);
          /*
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_out_inverse, R.anim.slide_in_inverse);
            AboutFragment aboutFrag = new AboutFragment();
            aboutFrag.setLockedFragmentListener(this);
            transaction.replace(R.id.container, aboutFrag);
            transaction.addToBackStack(AboutFragment.class.getName());
            transaction.commit();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLockStart() {
        if (mNavigationDrawerFragment != null)
            mNavigationDrawerFragment.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onLockStop() {
        if (mNavigationDrawerFragment != null)
            mNavigationDrawerFragment.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void manageDoubleBack() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.container);

        if (frag != null && frag instanceof WebViewFragment && ((WebViewFragment) frag).doBack()) {
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            if (hasPreviousBack) {
                // after the second click on back key
                mHandler.removeCallbacksAndMessages(null);
                if (mToast != null) {
                    mToast.cancel();
                }
                super.onBackPressed();
            } else {
                // after the first click on back key
                hasPreviousBack = true;
                mToast = Toast.makeText(this, R.string.click_twice_to_quit, (int) DurationInMillis.ONE_MINUTE);
                mToast.show();
                mHandler.postDelayed(new CancelToast(), DurationInMillis.ONE_SECOND * 3);
            }
        }
    }

    private class CancelToast implements Runnable {

        @Override
        public void run() {
            hasPreviousBack = false;
            if (mToast != null) {
                mToast.cancel();
            }
        }

    }

    @Override
    public void onBackPressed() {
        manageDoubleBack();
    }


}
