package happy.lottery.six.Fragments;

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
 * Created by sky057509 on 2018/3/21.
 */

public class BaseLotteryItem extends RelativeLayout{
    protected final String TAG = "Lottery";
    private TextView name = null;
    private TextView openTime = null;
    private TextView openExcept = null;
    private LinearLayout openResult = null;
    public BaseLotteryItem(Context context) {
        super(context);
        inflate(context, R.layout.grid_item,this);
        name = (TextView) findViewById(R.id.lottery_name);
        openTime = (TextView) findViewById(R.id.lottery_new_open_time);
        openExcept = (TextView) findViewById(R.id.lottery_new_open_expect);
        openResult = (LinearLayout) findViewById(R.id.lottery_new_open_result);
    }

    public BaseLotteryItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.grid_item,this);
        name = (TextView) findViewById(R.id.lottery_name);
        openTime = (TextView) findViewById(R.id.lottery_new_open_time);
        openExcept = (TextView) findViewById(R.id.lottery_new_open_expect);
        openResult = (LinearLayout) findViewById(R.id.lottery_new_open_result);
    }

    public BaseLotteryItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.grid_item,this);
        name = (TextView) findViewById(R.id.lottery_name);
        openTime = (TextView) findViewById(R.id.lottery_new_open_time);
        openExcept = (TextView) findViewById(R.id.lottery_new_open_expect);
        openResult = (LinearLayout) findViewById(R.id.lottery_new_open_result);
    }

    public BaseLotteryItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.grid_item,this);
        name = (TextView) findViewById(R.id.lottery_name);
        openTime = (TextView) findViewById(R.id.lottery_new_open_time);
        openExcept = (TextView) findViewById(R.id.lottery_new_open_expect);
        openResult = (LinearLayout) findViewById(R.id.lottery_new_open_result);
    }
    public void setName(String name)
    {
        this.name.setText(name);
    }
    public void setOpenTime(String time)
    {
        this.openTime.setText(time);
    }

    public void setOpenExcept(String openExcept) {
        this.openExcept.setText(openExcept);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setOpenResult(int[] normal_result, int[] special_result) {
        if(normal_result==null)
        {
            return;
        }
        openResult.removeAllViews();
        for (int i:normal_result)
        {
            TextView readView = new TextView(getContext());
            readView.setBackground(getContext().getDrawable(R.drawable.ball_red));
            readView.setText(String.valueOf(i));
            readView.setGravity(Gravity.CENTER);
            readView.setPadding(0,2,0,0);
            readView.setTextColor(getContext().getColor(R.color.colorWhite));
            if(openResult!=null)
            {
                openResult.addView(readView);
            }
        }
        if(special_result!=null)
        {
            for (int i:special_result)
            {
                TextView readView = new TextView(getContext());
                readView.setBackground(getContext().getDrawable(R.drawable.ball_blue));
                readView.setText(String.valueOf(i));
                readView.setPadding(0,2,0,0);
                readView.setGravity(Gravity.CENTER);
                readView.setTextColor(getContext().getColor(R.color.colorWhite));
                if(openResult!=null)
                {
                    openResult.addView(readView);
                }
            }
        }
        if (openResult != null) {
            openResult.requestLayout();
        }
    }
}
