package bzh.caerwent.coquille.utils;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bzh.caerwent.coquille.R;
import bzh.caerwent.coquille.model.data.Section;

/**
 * Created by vincent on 13/01/2015.
 */
public class UIUtils {

    public static void inflateSection(LayoutInflater aInflater, ViewGroup aView, List<Section> aSectionsList) {
        aView.removeAllViews();

        if (aSectionsList == null)
            return;

        for (int i = 0; i < aSectionsList.size(); i++) {
            View section = aInflater.inflate(R.layout.section_item, aView, false);
            TextView aboutSectionName = (TextView) section.findViewById(R.id.section_name);
            aboutSectionName.setText(aSectionsList.get(i).getSectionName());
            TextView aboutSectionBody = (TextView) section.findViewById(R.id.section_body);
            aboutSectionBody.setText(Html.fromHtml(aSectionsList.get(i).getSectionBody()));
            aView.addView(section);
        }
    }
}
