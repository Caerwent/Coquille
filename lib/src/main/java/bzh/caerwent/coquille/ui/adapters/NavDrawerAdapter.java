package bzh.caerwent.coquille.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bzh.caerwent.coquille.R;
import bzh.caerwent.coquille.model.data.SideMenuItem;

/**
 * Created by vincent on 13/01/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<? extends SideMenuItem> mItems;

    public NavDrawerAdapter(Context context, List<? extends SideMenuItem> aItems) {
        this.inflater = LayoutInflater.from(context);
        mItems = aItems;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (mItems == null)
            return 0;
        return mItems.size();
    }

    @Override
    public boolean isEnabled(int position) {
        SideMenuItem menuItem = (SideMenuItem) this.getItem(position);

        if (menuItem.getType() == SideMenuItem.TYPE_HEADER) {
            return false;
        }

        return true;
    }

    @Override
    public Object getItem(int pos) {
        if (mItems == null)
            return null;
        return mItems.get(pos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        SideMenuItem menuItem = (SideMenuItem) this.getItem(position);

        NavMenuItemHolder navMenuItemHolder = null;

        if (convertView == null) {
            if (menuItem.getType() == SideMenuItem.TYPE_HEADER) {
                convertView = inflater.inflate(R.layout.nav_header_layout, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.nav_item_layout, parent, false);
            }
            TextView labelView = (TextView) convertView
                    .findViewById(R.id.nav_label);


            navMenuItemHolder = new NavMenuItemHolder();
            navMenuItemHolder.labelView = labelView;

            convertView.setTag(navMenuItemHolder);
        }

        if (navMenuItemHolder == null) {
            navMenuItemHolder = (NavMenuItemHolder) convertView.getTag();
        }

        navMenuItemHolder.labelView.setText(menuItem.getLabel());

        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((SideMenuItem) getItem(position)).getType() == SideMenuItem.TYPE_HEADER ? 0 : 1;
    }


    private static class NavMenuItemHolder {
        private TextView labelView;

    }

}
