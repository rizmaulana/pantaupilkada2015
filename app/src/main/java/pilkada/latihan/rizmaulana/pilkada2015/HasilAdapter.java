package pilkada.latihan.rizmaulana.pilkada2015;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rizki Maulana on 12/10/2015.
 */
public class HasilAdapter extends BaseAdapter {
    List<String> namakec = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> sah = new ArrayList<>();
    List<String> tidaksah = new ArrayList<>();
    LayoutInflater mInflater;
    View mView;
    TextView tnamakec, tsah, ttidaksah, ttotal;

    public HasilAdapter(Context c, List<String> kec, List<String> tot,
                        List<String> sh, List<String> tsah)
    {
        Context mContext = c;
        namakec = kec;
        total = tot;
        sah = sh;
        tidaksah = tsah;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return namakec.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mView = convertView;

        if(mView == null) {
            mView = mInflater.inflate(
                    R.layout.list_menu, (ViewGroup) null);
        }
        tnamakec = (TextView) mView.findViewById(R.id.text_kec);
        ttotal = (TextView) mView.findViewById(R.id.text_total);
        ttidaksah = (TextView) mView.findViewById(R.id.text_tidaksah);
        tsah = (TextView) mView.findViewById(R.id.text_sah);
        tnamakec.setText(namakec.get(position));
        ttotal.setText("Total Suara "+total.get(position));
        tsah.setText(sah.get(position));
        ttidaksah.setText(tidaksah.get(position));
        return mView;

    }
}
