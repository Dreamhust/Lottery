package happy.lottery.six;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by sky057509 on 2018/3/19.
 */

public class MainWebClint extends WebViewClient{
    private final String TAG = "Lottery";
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        Log.d(TAG,"shouldOverrideUrlLoading url: " + url);
        if(url.contains("back"))
        {
            view.goBack();
            return true;
        }
        if(url.contains("qq.com")&&url.contains("https"))
        {
            if(url.contains("tag?id="))
            {
                return true;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String data = processQQNews(view,url);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                if(data!=null) {
                                    view.loadDataWithBaseURL(url,data, "text/html", "utf-8",null);
                                }
                                else
                                {
                                    view.loadUrl(url);
                                }
                            }
                        });
                }
            }).start();
        }
        else if(url.contains("nation_hf"))
        {
            Intent intent = new Intent(view.getContext(),MainActivity.class);
            intent.setAction("lottery.goto.nation_hf");
            view.getContext().startActivity(intent);
        }
        else if(url.contains("nation_lf"))
        {
            Intent intent = new Intent(view.getContext(),MainActivity.class);
            intent.setAction("lottery.goto.nation_lf");
            view.getContext().startActivity(intent);
        }
        else if(url.contains("nation"))
        {
            Intent intent = new Intent(view.getContext(),MainActivity.class);
            intent.setAction("lottery.goto.nation");
            view.getContext().startActivity(intent);
        }
        else
        {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

    }
    private String processQQNews(WebView view, String url)
    {
        String data = null;
        try {
            Document r_doc = Jsoup.connect(url).timeout(60000).get();
            Elements heads = r_doc.getElementsByClass("header nopad");
            if(heads.size()!=0)
            {
                Element head = heads.get(0);
                Element back = head.getElementsByClass("treturn gohistory").get(0);
                back.removeAttr("data-boss");
                back.attr("href","back");
                head.getElementsByClass("sitename tochannel").get(0).remove();
                head.getElementsByClass("gochannels").get(0).remove();
                Log.d(TAG,"head: " + head.toString());
            }
            Elements primary = r_doc.getElementsByClass("primary");
            if(primary.size()>0)
            {
                primary.get(0).removeClass("tags");
            }
            r_doc.getElementsByClass("today_hot").remove();
            r_doc.getElementsByClass("footer nopad").remove();
            r_doc.getElementsByClass("sidenav nopad").remove();
            r_doc.getElementsByClass("fixnav nopad").remove();
            r_doc.getElementsByClass("relateWords").remove();

            data = r_doc.outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
