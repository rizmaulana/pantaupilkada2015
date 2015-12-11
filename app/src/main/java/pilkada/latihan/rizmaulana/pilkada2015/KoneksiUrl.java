package pilkada.latihan.rizmaulana.pilkada2015;

import android.content.ContentValues;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class KoneksiUrl {

    private String mUrl;

    private ContentValues mParams;

    public String execute() {
        String response = "";
        HttpURLConnection huc = null;
        if (mUrl != null) {
            try {
                URL url = new URL(mUrl);
                huc = (HttpURLConnection) url.openConnection();
                if (mParams != null) {
                    huc.setDoOutput(true);
                    huc.setChunkedStreamingMode(0);

                    String params = this.formatPostParams(mParams);
                    OutputStream os = new BufferedOutputStream(huc.getOutputStream());
                    writeStream(os, params);
                }
                int responseCode = huc.getResponseCode();
                switch (responseCode) {
                    case 200:
                    case 201:
                        InputStream is = new BufferedInputStream(huc.getInputStream());
                        response = readStream(is);
                        break;

                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(huc != null) {
                    huc.disconnect();
                }
            }
        }
        return response;
    }

    public String execute(String url) {
        this.mUrl = url;
        return this.execute();
    }

    public String execute(String url, ContentValues params) {
        this.mUrl = url;
        this.mParams = params;
        return this.execute();
    }

    private String formatPostParams(ContentValues cv) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> set = cv.valueSet();

        for (Map.Entry entry : set) {
            sb.append('&');
            sb.append(String.valueOf(entry.getKey()));
            sb.append('=');
            sb.append(String.valueOf(entry.getValue()));
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    private String readStream(InputStream is){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void writeStream(OutputStream os, String params) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
            bw.write(params);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
