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
public class DetailAdapter extends BaseAdapter{

    List<String> perolehan = new ArrayList<>();
    List<String> pasangan = new ArrayList<>();
    LayoutInflater mInflater;
    View mView;
    TextView npasangan, nperolehan;

    public DetailAdapter(Context c, List<String> p, List<String> t)
    {
        Context mContext = c;
        pasangan = p;
        perolehan = t;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return perolehan.size();
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
                    R.layout.list_detail, (ViewGroup) null);
        }
        npasangan = (TextView) mView.findViewById(R.id.text_pasangan);
        nperolehan = (TextView) mView.findViewById(R.id.text_perolehan);
        npasangan.setText(pasangan.get(position));
        nperolehan.setText("Total Perolehan : "+perolehan.get(position));
        return mView;
    }
}
