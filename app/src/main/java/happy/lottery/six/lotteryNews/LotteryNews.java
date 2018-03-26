package happy.lottery.six.lotteryNews;

import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import happy.lottery.six.UlrRequest;

/**
 * Created by sky057509 on 2018/3/19.
 */

public class LotteryNews extends Object implements UlrRequest.RequestListener{
    private final String TAG = "Lottery";
    private UlrRequest request = null;
    private static LotteryNews m_pThis = null;
    private boolean isMoreNews = false;
    private List<News> newsList = new ArrayList<>();
    private List<News> more_newsList = new ArrayList<>();
    public static LotteryNews getInstance()
    {
        if(m_pThis==null)
        {
            new LotteryNews();
        }
        return m_pThis;
    }
    private LotteryNews()
    {
        m_pThis = this;
        request = new UlrRequest();
        request.setmListener(this);
        isMoreNews = false;
    }
    public void RequestNews()
    {
        isMoreNews = false;
        request.RequestUrl("http://120.76.205.241:8000/news/qihoo?kw=%E5%BD%A9%E7%A5%A8&site=qq.com&apikey=bovO3HzakUBaWQsZ2ACKiDgiTOpZ3ikzIb7HY0rANsKhaQJKpk9kBD43W0UGkXdg");
    }
    public void RequestMoreNews()
    {
        isMoreNews = true;
        request.RequestUrl("http://120.76.205.241:8000/news/qihoo?kw=%E5%A4%A7%E5%A5%96&site=qq.com&apikey=bovO3HzakUBaWQsZ2ACKiDgiTOpZ3ikzIb7HY0rANsKhaQJKpk9kBD43W0UGkXdg");
    }
    @JavascriptInterface
    public int GetMoreNewsNumber()
    {
        return more_newsList.size();
    }
    @JavascriptInterface
    public String GetMoreNews(int index)
    {
        if(more_newsList.size()==0)
        {
            return null;
        }
        return more_newsList.get(index).toString();
    }
    @JavascriptInterface
    public int GetTopNewsNumber()
    {
        return newsList.size();
    }
    @JavascriptInterface
    public String GetTopNews(int index)
    {
        if(newsList.size()==0)
        {
            return null;
        }
        return newsList.get(index).toString();
    }
    @Override
    public synchronized void onRequestDone(JSONObject result) {
        try {
            if(result.has("dataType")&&result.getString("dataType").compareTo("news")==0&&result.has("data"))
            {
                JSONArray jsonArray = result.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++)
                {
                    News news = new News((JSONObject) jsonArray.get(i));
                    if(isMoreNews)
                    {
                        Log.d(TAG,"More NEWS: " + ((JSONObject)jsonArray.get(i)).toString());
                        more_newsList.add(news);
                    }
                    else
                    {
                        Log.d(TAG,"Top NEWS: " + ((JSONObject)jsonArray.get(i)).toString());
                        newsList.add(news);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
