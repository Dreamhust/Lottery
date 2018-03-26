package happy.lottery.six;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import happy.lottery.six.lotteryNews.LotteryNews;

/**
 * Created by sky057509 on 2018/3/19.
 */

public class MainWebActivity extends Activity{
    private int pressback = 0;
    private final String TAG = "Lottery";
    WebView m_MainWebView = null;
    MainWebClint m_MainWebClint = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_MainWebView = new WebView(this);
        setContentView(m_MainWebView);
        WebSettings webSettings = m_MainWebView.getSettings();
        m_MainWebClint = new MainWebClint();
        m_MainWebView.setWebViewClient(m_MainWebClint);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        m_MainWebView.addJavascriptInterface(new InJavaScriptLocalObj(),"java_obj");
        m_MainWebView.addJavascriptInterface(LotteryNews.getInstance(),"LotteryNews");
        m_MainWebView.setHorizontalScrollBarEnabled(false);
        m_MainWebView.setVerticalScrollBarEnabled(false);
        m_MainWebView.requestFocus();
        m_MainWebView.loadUrl("file:///android_asset/index.html");
    }
    public final class InJavaScriptLocalObj
    {
        @JavascriptInterface
        public void showSource(final String html,final String url) {
            m_MainWebView.post(new Runnable() {
                @Override
                public void run() {
                    if(url.contains("163.com"))
                    {
                        m_MainWebView.loadData(html,"text/html","utf-8");
                    }
                }
            });

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP)
        {
            return true;
        }
        else
        {
            if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            {
                if(m_MainWebView.canGoBack())
                {
                    pressback = 0;
                    m_MainWebView.goBackOrForward(-2);
                }
                else
                {
                    pressback++;
                    if(pressback==1)
                    {
                        Toast.makeText(this, getString(R.string.exit_notice), Toast.LENGTH_LONG).show();
                    }
                    if(pressback==2)
                    {
                        pressback = 0;
                        finish();
                    }
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
