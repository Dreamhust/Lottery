package happy.lottery.six;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by sky057509 on 2018/3/22.
 */

public class LotteryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
