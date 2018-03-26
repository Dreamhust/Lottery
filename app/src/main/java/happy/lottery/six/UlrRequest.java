package happy.lottery.six;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sky057509 on 2018/3/19.
 */

public class UlrRequest {
    private final static String TAG = "Lottery";
    private JSONObject result = null;
    private RequestListener mListener = null;
    public interface RequestListener
    {
        void onRequestDone(JSONObject result);
    }
    public void setmListener(RequestListener mListener) {
        this.mListener = mListener;
    }
    public void RequestUrl(final String wsurl)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"reques url: " + wsurl);
                HttpURLConnection connection = null;
                Reader read;
                BufferedReader bufferReader;
                try {
                    URL url = new URL(wsurl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    read=new InputStreamReader(connection.getInputStream());
                    bufferReader=new BufferedReader(read);
                    String str;
                    StringBuilder buffer=new StringBuilder();//接受全部数据
                    while((str=bufferReader.readLine())!=null){
                        buffer.append(str).append("\n");
                    }
                    read.close();
                    connection.disconnect();
                    Log.i(TAG,"RESULT: "+buffer.toString());
                    result = new JSONObject(buffer.toString());
                    if(mListener!=null)
                    {
                        mListener.onRequestDone(result);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    result = null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
