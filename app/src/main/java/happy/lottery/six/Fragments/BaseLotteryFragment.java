package happy.lottery.six.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import happy.lottery.six.R;
import happy.lottery.six.lotteryData.Lottery;

/**
 * Created by sky057509 on 2018/3/15.
 */

public abstract class BaseLotteryFragment extends Fragment{
    protected final static String HEAD = "http://f.apiplus.net/";
    protected RecyclerView.LayoutManager layoutManager = null;
    protected RecyclerView recyclerView = null;
    protected String[] nation_lotteries = null;
    protected List<Lottery> lotteries = new ArrayList<>();
    protected final String TAG = "Lottery";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"BaseLotteryFragment onCreate");
        super.onCreate(savedInstanceState);
    }
    protected class FragmentViewHolder extends RecyclerView.ViewHolder
    {
        private BaseLotteryItem item = null;
        public FragmentViewHolder(View itemView) {
            super(itemView);
            item = (BaseLotteryItem) itemView;
        }
        public void setName(String name)
        {
            item.setName(name);
        }
        public void setOpenTime(String time)
        {
            item.setOpenTime(time);
        }

        public void setOpenExcept(String openExcept) {
            item.setOpenExcept(openExcept);
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setOpenResult(int[] normal_result, int[] special_result)
        {
            item.setOpenResult(normal_result,special_result);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lottery_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lotteries_grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d(TAG,"onCreateViewHolder: ");
                return new FragmentViewHolder(new BaseLotteryItem(getContext()));
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                Log.d(TAG,"onBindViewHolder: " + position);
                holder.itemView.setTag(R.id.tag_id,position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lotteries.get(position).TryShow();
                    }
                });
                if(holder.itemView.getTag(R.id.tag_id).equals(position)&&lotteries.size()>0)
                {
                    Log.d(TAG,"refresh name: "+ lotteries.get(position).getName());
                    if(lotteries.get(position).GetOpenResults().size()>0)
                    {
                        FragmentViewHolder holder1 = (FragmentViewHolder) holder;
                        holder1.setName(nation_lotteries[position]);
                        holder1.setOpenTime(lotteries.get(position).GetOpenResults().get(0).getOpenDate());
                        holder1.setOpenResult(lotteries.get(position).GetOpenResults().get(0).getOpenResult_Normal(),
                                lotteries.get(position).GetOpenResults().get(0).getOpenResult_Special());
                        holder1.setOpenExcept(lotteries.get(position).GetOpenResults().get(0).getOpenExpect());
                    }
                }
            }

            @Override
            public int getItemCount() {
                return lotteries.size();
            }
        });
        return view;
    }
    public abstract void Init(Context context);
}
