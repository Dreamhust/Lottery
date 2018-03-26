package happy.lottery.six;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import happy.lottery.six.lotteryNews.LotteryNews;

/**
 * Created by sky057509 on 2018/3/14.
 */

public class WelcomeActivity extends Activity implements View.OnClickListener,UlrRequest.RequestListener{
    private Button skipButton = null;
    private static final int countdown_time_sec = 5;
    private int current_count = 0;
    private static final int COUNTDOWN_MESSAGE = 0x00;
    private static final int REQUEST_URL = 0x01;
    private static final int REQUEST_NEWS = 0x02;
    private static final int CHECK_NEWS = 0x03;
    private final static String TAG = "Lottery";
    private WelcomeHandler mHandler = new WelcomeHandler();
    private boolean isOpenSelfInfo = true;
    private UlrRequest request = null;
    private String getUrl = "";
    private String getAppName = "";
    private JSONObject result = null;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onRequestDone(JSONObject result) {
        try {
            if(result.has("status")&&result.getInt("status")==1)
            {
                isOpenSelfInfo = result.getInt("isshowwap")==2;
                if(!isOpenSelfInfo)
                {
                    getUrl = result.getString("wapurl");
                    getAppName = result.getString("appname");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class WelcomeHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case COUNTDOWN_MESSAGE:
                {
                    if(skipButton!=null)
                    {
                        current_count++;
                        skipButton.setText(String.valueOf(countdown_time_sec-current_count)+"s");
                        if(current_count>=countdown_time_sec)
                        {
                            current_count = 0;
                            if(isOpenSelfInfo)
                            {
                                Log.i(TAG,"no need start webview!");
                                startMyActivity();
                            }
                            else
                            {
                                if(getUrl.compareTo("")!=0)
                                {
                                    startWebView(getUrl);
                                }
                            }
                            finish();
                        }
                        else
                        {
                            mHandler.sendEmptyMessageDelayed(COUNTDOWN_MESSAGE,1000);
                        }
                    }
                }
                break;
                case CHECK_NEWS:
                {
                    if(LotteryNews.getInstance().GetTopNewsNumber()==0)
                    {
                        LotteryNews.getInstance().RequestNews();
                    }
                    if(LotteryNews.getInstance().GetMoreNewsNumber()==0)
                    {
                        LotteryNews.getInstance().RequestMoreNews();
                    }
                }
                break;
                case REQUEST_NEWS:
                {
                    LotteryNews.getInstance().RequestNews();
                    LotteryNews.getInstance().RequestMoreNews();
                    sendEmptyMessageDelayed(CHECK_NEWS,2500);
                }
                break;
                case REQUEST_URL:
                {
                    request.RequestUrl("http://www.27305.com/frontApi/getAboutUs?appid=1803072115");
                }
                break;
                default:break;
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welecom);
        skipButton = (Button) findViewById(R.id.count_button);
        skipButton.setText(countdown_time_sec + "s");
        skipButton.setOnClickListener(this);
        request = new UlrRequest();
        request.setmListener(this);
        mHandler.sendEmptyMessage(REQUEST_URL);
        mHandler.sendEmptyMessage(REQUEST_NEWS);
        mHandler.sendEmptyMessageDelayed(COUNTDOWN_MESSAGE,1000);
    }

    @Override
    public void onClick(View view) {
        /* current no need use skip button~
        if(view.getId() == R.id.count_button)
        {
            Log.i(TAG,"onClick start activity!");
            mHandler.removeMessages(COUNTDOWN_MESSAGE);
            if(isOpenSelfInfo)
            {
                startMyActivity();
            }
            else
            {
                if(getUrl.compareTo("")!=0)
                {
                    startWebView(getUrl);
                }
            }
            finish();
        }
        */
    }
    private void startMyActivity()
    {
        Intent intent = new Intent(this,MainWebActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
    private void startWebView(String url)
    {
        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
