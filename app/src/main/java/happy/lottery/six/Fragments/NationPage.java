package happy.lottery.six.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import happy.lottery.six.R;
import happy.lottery.six.lotteryData.Lottery;

/**
 * Created by sky057509 on 2018/3/15.
 */

public class NationPage extends BaseLotteryFragment {
    public void Init(Context context)
    {
        nation_lotteries = context.getResources().getStringArray(R.array.nation_lotteries);
        String[] url = context.getResources().getStringArray(R.array.nation_lotteries_url);
        for(int i=0;i<nation_lotteries.length;i++)
        {
            Lottery lottery1 = new Lottery(context,nation_lotteries[i],HEAD+url[i]);
            lottery1.init();
            lotteries.add(lottery1);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
