package happy.lottery.six.lotteryNews;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sky057509 on 2018/3/19.
 */

public class News{
    private final static String TAG = "News";
    private String title= null;
    private String date = null;
    private String[] imageUrl = null;
    private String content = null;
    private String url = null;
    private String  jsonString = null;
    public News(JSONObject jsonObject) throws JSONException {
        this.jsonString = jsonObject.toString();
        title = jsonObject.getString("title");
        date = jsonObject.getString("publishDateStr");
        if(!jsonObject.isNull("imageUrls"))
        {
            JSONArray imageUrls = jsonObject.getJSONArray("imageUrls");
            if(imageUrls!=null)
            {
                Log.d(TAG,"imageUrls: " + imageUrls.toString());
                imageUrl = new String[imageUrls.length()];
                for(int i = 0;i<imageUrls.length();i++)
                {
                    Log.d(TAG,"imageUrls_"+i+": " + imageUrls.get(i));
                    imageUrl[i] = (String) imageUrls.get(i);
                }
            }
        }
        url = jsonObject.getString("url");
        content = jsonObject.getString("content");
    }
    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        if(imageUrl==null)
        {
            return null;
        }
        return imageUrl[0];
    }

    public String getTitle() {
        return title;
    }
    public String toString()
    {
        return this.jsonString;
    }
}
