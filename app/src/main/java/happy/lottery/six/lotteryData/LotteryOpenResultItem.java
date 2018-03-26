package happy.lottery.six.lotteryData;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import happy.lottery.six.R;

/**
 * Created by sky057509 on 2018/3/16.
 */

public class LotteryOpenResultItem extends RelativeLayout {
    private final String TAG = "Lottery";
    private TextView OpenTime = null;
    private TextView OpenExpect = null;
    private LinearLayout OpenResult = null;
    private int[] normal_result = null;
    private int[] special_result = null;
    public LotteryOpenResultItem(Context context) {
        super(context);
        inflate(context, R.layout.lottery_dital_item,this);
        OpenTime = (TextView) findViewById(R.id.open_time);
        OpenExpect = (TextView) findViewById(R.id.openExpect);
        OpenResult = (LinearLayout) findViewById(R.id.lottery_openResult);
    }

    public LotteryOpenResultItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.lottery_dital_item,this);
        OpenTime = (TextView) findViewById(R.id.open_time);
        OpenExpect = (TextView) findViewById(R.id.openExpect);
        OpenResult = (LinearLayout) findViewById(R.id.lottery_openResult);
    }

    public LotteryOpenResultItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.lottery_dital_item,this);
        OpenTime = (TextView) findViewById(R.id.open_time);
        OpenExpect = (TextView) findViewById(R.id.openExpect);
        OpenResult = (LinearLayout) findViewById(R.id.lottery_openResult);
    }

    public LotteryOpenResultItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.lottery_dital_item,this);
        OpenTime = (TextView) findViewById(R.id.open_time);
        OpenExpect = (TextView) findViewById(R.id.openExpect);
        OpenResult = (LinearLayout) findViewById(R.id.lottery_openResult);
    }

    public void setOpenExpect(String openExpect) {
        OpenExpect.setText(openExpect);
    }

    public void setOpenTime(String openTime) {
        OpenTime.setText(openTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setNormal_result(int[] normal_result) {
        if(normal_result==null)
        {
            return;
        }
        OpenResult.removeAllViews();
        this.normal_result = normal_result;
        for (int i:normal_result)
        {
            TextView readView = new TextView(getContext());
            readView.setBackground(getContext().getDrawable(R.drawable.ball_red));
            readView.setText(String.valueOf(i));
            readView.setPadding(0,2,0,0);
            readView.setGravity(Gravity.CENTER);
            readView.setTextColor(getContext().getColor(R.color.colorWhite));
            if(OpenResult!=null)
            {
                OpenResult.addView(readView);
            }
        }
        if (OpenResult != null) {
            OpenResult.requestLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setSpecial_result(int[] special_result) {
        if(special_result==null)
        {
            return;
        }
        this.special_result = special_result;
        for (int i:special_result)
        {
            TextView readView = new TextView(getContext());
            readView.setBackground(getContext().getDrawable(R.drawable.ball_blue));
            readView.setText(String.valueOf(i));
            readView.setPadding(0,2,0,0);
            readView.setGravity(Gravity.CENTER);
            readView.setTextColor(getContext().getColor(R.color.colorWhite));
            if(OpenResult!=null)
            {
                OpenResult.addView(readView);
            }
        }
        if (OpenResult != null) {
            OpenResult.requestLayout();
        }
    }
}
