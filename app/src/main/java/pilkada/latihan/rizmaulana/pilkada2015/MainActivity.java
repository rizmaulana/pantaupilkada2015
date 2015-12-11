package pilkada.latihan.rizmaulana.pilkada2015;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.InputStream;



public class MainActivity extends Activity {

    private ProgressDialog p_dialog;
    Button bttn_temukan;
    EditText field_daerah;
    String keyword;
    String keyword_final;
    String url = "https://pilkada2015.kpu.go.id/contentinjector/search_wilayah_by_keyword_json?term=";
    InputStream is = null;
    Button kontak, tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttn_temukan = (Button) findViewById(R.id.button_temukan);
        field_daerah = (EditText) findViewById(R.id.field_namakab);
        kontak = (Button) findViewById(R.id.button_tanggapan);
        tentang = (Button) findViewById(R.id.button_tentang);
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(getApplicationContext(), Tentang.class);
                startActivity(x);
            }
        });

        kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_VIEW);
              intent.addCategory(Intent.CATEGORY_BROWSABLE);
              intent.setData(Uri.parse("https://www.facebook.com/almaulana.rizki"));
              startActivity(intent);
            }
        });
        bttn_temukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = (field_daerah.getText()).toString();
                if (keyword.equals(""))
                {
                    Toast.makeText(getApplication(), "Nama Kabuapaten / Kota harus diisi.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String key_f = keyword.toLowerCase();
                    String key_s = key_f.replaceAll("\\s","");
                    keyword_final = key_s+"kab";
                   // Toast.makeText(getApplication(), keyword_final, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HasilPenemuan.class);
                    i.putExtra("keyword", keyword_final);
                    startActivity(i);
                }
            }
        });

    }
}
