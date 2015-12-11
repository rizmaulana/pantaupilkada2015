package pilkada.latihan.rizmaulana.pilkada2015;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Detail extends Activity {

    String keyword, kec;
    List<String> pasangan = new ArrayList<>();
    List<String> perolehan = new ArrayList<>();
    String url = "http://pilkada.lantip.net/";
    ListView lvd;
    TextView sah, tidaksah, total, namakec;
    ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    String sah_, tidaksah_, total_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        keyword = extras.getString("keyword");
        kec = extras.getString("kec");
        sah = (TextView) findViewById(R.id.text_d_sah);
        tidaksah = (TextView) findViewById(R.id.text_d_tidaksah);
        total = (TextView) findViewById(R.id.text_d_total);
        namakec = (TextView) findViewById(R.id.text_d_kec);
        lvd = (ListView) findViewById(R.id.list_detail);
        namakec.setText(kec);


        new AmbilDataJson().execute();
    }
    class AmbilDataJson extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Detail.this);
            pDialog.setMessage("mengambil data dari server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url+keyword, "GET", params);
            try {
                JSONObject jobj = json.getJSONObject("data");
                JSONObject kecs = jobj.getJSONObject(kec);
                sah_ = kecs.getString("sah");
                tidaksah_ = kecs.getString("tidaksah");
                total_ = kecs.getString("total");
                Log.i("Hoho", sah_);
                JSONObject kecd = kecs.getJSONObject("perolehan");
                for(int i=1; i<=kecd.length(); i++)
                {
                    JSONObject j = kecd.getJSONObject(String.valueOf(i));
                    String p = j.getString("pasangan");
                    String t = j.getString("total");
                    pasangan.add(p);
                    perolehan.add(t);
                }
                Log.i("XXs", pasangan.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            sah.setText("Suara Sah : "+sah_);
            tidaksah.setText("Suara Tidak Sah : "+tidaksah_);
            total.setText("Total Suara : "+total_);
            runOnUiThread(new Runnable() {
                public void run() {
                DetailAdapter a = new DetailAdapter(getApplicationContext(), pasangan, perolehan);
                lvd.setAdapter(a);
                }

            });
        }
    }



}
