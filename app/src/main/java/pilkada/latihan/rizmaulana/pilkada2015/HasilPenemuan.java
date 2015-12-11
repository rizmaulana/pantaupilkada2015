package pilkada.latihan.rizmaulana.pilkada2015;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HasilPenemuan extends Activity {
    String url = "http://pilkada.lantip.net/";
    ListView lv;
    String keyword;
    List<String> namakec = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> sah = new ArrayList<>();
    List<String> tidaksah = new ArrayList<>();
    ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_penemuan);
        lv = (ListView) findViewById(R.id.list_hasil);
        Bundle extras = getIntent().getExtras();
        keyword = extras.getString("keyword");
        new AmbilDataJson().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kec = namakec.get(position);
                String cek = total.get(position);
                if (cek.equals("Belum tersedia"))
                {
                    Toast.makeText(getApplication(), "Maaf data belum tersedia", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent a = new Intent(getApplicationContext(), Detail.class);
                    a.putExtra("keyword", keyword);
                    a.putExtra("kec", kec);
                    startActivity(a);
                }

            }
        });
    }

    class AmbilDataJson extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HasilPenemuan.this);
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
                Iterator keysToCopyIterator = jobj.keys();
                while(keysToCopyIterator.hasNext()) {
                    String key = (String) keysToCopyIterator.next();
                    namakec.add(key);
                }
                Log.i("Hoho", namakec.toString());
                for (String s:namakec)
                {
                    JSONObject jobj2 = jobj.optJSONObject(s);
                    Log.i("Hoho", s);

                    if (jobj2 == null)
                    {
                        Log.i("Hoho", "null");
                        total.add("Belum tersedia");
                        sah.add("Belum tersedia");
                        tidaksah.add("Belum tersedia");
                    }
                    else
                    {
                        Log.i("Hoho", "notnull");
                        total.add(jobj2.getString("total"));
                        sah.add(jobj2.getString("sah"));
                        tidaksah.add(jobj2.getString("tidaksah"));
                    }
                }
                Log.i("Hoho", total.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                HasilAdapter adap = new HasilAdapter(getApplicationContext(),
                        namakec, total, sah, tidaksah);
                    lv.setAdapter(adap);
                }

            });
        }
    }


}
