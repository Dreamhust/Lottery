package happy.lottery.six;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import happy.lottery.six.Fragments.BaseLotteryFragment;
import happy.lottery.six.Fragments.NationHfPage;
import happy.lottery.six.Fragments.NationLfPage;
import happy.lottery.six.Fragments.NationPage;

/**
 * Created by sky057509 on 2018/3/14.
 */

public class MainActivity extends Activity{
    private static final int SHOW = 0;
    private final String TAG = "Lottery";
    private FragmentManager manager = null;
    private ProgressBar loading = null;
    private ImageView back = null;
    BaseLotteryFragment currentFragment = null;
    BaseLotteryFragment fragment_nation = null;
    BaseLotteryFragment fragment_hf_nation = null;
    BaseLotteryFragment fragment_lf_nation = null;
    private Handler mHandler= new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SHOW:
                {
                    loading.setVisibility(View.INVISIBLE);
                    manager.beginTransaction().add(R.id.lottery_fragment,currentFragment).commit();
                }
                break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotteryinfo);
        manager = getFragmentManager();
        currentFragment = null;
        back = (ImageView) findViewById(R.id.back_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loading = (ProgressBar) findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(action.contains("nation_hf"))
        {
            currentFragment = new NationHfPage();
        }
        else if(action.contains("nation_lf"))
        {
            currentFragment = new NationLfPage();
        }
        else if (action.contains("nation"))
        {
            currentFragment = new NationPage();
        }
        else
        {
            currentFragment = new NationPage();
        }
        currentFragment.Init(this);
        mHandler.sendEmptyMessageDelayed(SHOW,1500);
    }
}
