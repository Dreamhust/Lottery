package happy.lottery.six.lotteryData;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import happy.lottery.six.R;

/**
 * Created by sky057509 on 2018/3/15.
 */

public class Lottery extends Dialog implements DialogInterface.OnShowListener{
    private static final int MESSAGE_CHECK_CAN_SHOW = 0x00;
    private LotteryHandler mHandler = null;
    private ImageView close = null;
    private class LotteryHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MESSAGE_CHECK_CAN_SHOW:
                {
                    if(results.size()>0)
                    {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getContext(), getContext().getString(R.string.get_data_filed), Toast.LENGTH_LONG).show();
                    }
                }
                break;
                default:
                    break;
            }
        }
    }
    public void TryShow()
    {
        mHandler.sendEmptyMessageDelayed(MESSAGE_CHECK_CAN_SHOW,500);
    }

    private final String TAG = "Lottery";
    private String requestUrl;
    private String name;
    private RecyclerView resultListView = null;
    private List<LotteryOpenResult> results;
    private class OpenListItemViewHolder extends RecyclerView.ViewHolder
    {
        private LotteryOpenResultItem item = null;
        public OpenListItemViewHolder(View itemView) {
            super(itemView);
            item = (LotteryOpenResultItem) itemView;
        }
        public void SetOpenExpect(String expect)
        {
            item.setOpenExpect(expect);
        }
        public void SetOpenTime(String time)
        {
            item.setOpenTime(time);
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void SetResult(int[] normal, int[] special)
        {
            item.setNormal_result(normal);
            if(special!=null&&special.length>0)
            {
                item.setSpecial_result(special);
            }
        }
    }
    private class OpenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OpenListItemViewHolder(new LotteryOpenResultItem(getContext()));
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            OpenListItemViewHolder holder1 = (OpenListItemViewHolder) holder;
            holder1.SetOpenTime(results.get(position).getOpenDate());
            holder1.SetOpenExpect(results.get(position).getOpenExpect());
            holder1.SetResult(results.get(position).getOpenResult_Normal(),results.get(position).getOpenResult_Special());
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }
    public Lottery(Context context,String name, String url)
    {
        super(context,R.style.MyDialog);
        this.name = name;
        this.requestUrl = url;
        results = new ArrayList<>();
        mHandler = new LotteryHandler();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottery_dital);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setOnShowListener(this);
    }
    public void init()
    {
        if(requestUrl!=null)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"reques url: " + requestUrl);
                    HttpURLConnection connection = null;
                    Reader read;
                    BufferedReader bufferReader;
                    try {
                        URL url = new URL(requestUrl);
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
                        JSONObject result = new JSONObject(buffer.toString());
                        JSONArray data = result.getJSONArray("data");
                        for (int i=0;i<data.length();i++)
                        {
                            LotteryOpenResult _result = new LotteryOpenResult(data.getJSONObject(i));
                            Log.d(TAG,"result: " + _result.toString());
                            results.add(_result);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            }).start();
        }
    }
    public List<LotteryOpenResult> GetOpenResults()
    {
        return results;
    }
    public String getName()
    {
        return this.name;
    }
    @Override
    public void onShow(DialogInterface dialog) {
        if(results.size()>0)
        {
            resultListView = (RecyclerView) findViewById(R.id.opresult_list);
            resultListView.setLayoutManager(new LinearLayoutManager(getContext()));
            resultListView.setAdapter(new OpenListAdapter());
        }
    }
}
