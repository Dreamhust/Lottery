package happy.lottery.six.lotteryData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by sky057509 on 2018/3/15.
 */

public class LotteryOpenResult
{
    private final String TAG = "Lottery";
    private String openDate;
    private int[] openResult_Normal;
    private int[] openResult_Special;
    private String openExpect;
    public LotteryOpenResult(JSONObject jsonObject) throws JSONException {
        openDate = jsonObject.getString("opentime");
        openExpect = jsonObject.getString("expect");
        String openResult = jsonObject.getString("opencode");
        if(openResult!=null)
        {
            if(openResult.contains("+"))
            {
                String[] result =  openResult.split("\\+");
                for (int j=0;j<result.length;j++)
                {
                    if(result[j].contains(",")&&j<result.length-1)
                    {
                        String[] normal_res = result[j].split(",");
                        openResult_Normal = new int[normal_res.length];
                        for (int i=0;i<normal_res.length;i++)
                        {
                            openResult_Normal[i] = Integer.valueOf(normal_res[i]);
                        }
                    }
                    else
                    {
                        String[] normal_res = result[j].split(",");
                        openResult_Special = new int[normal_res.length];
                        for (int i=0;i<normal_res.length;i++)
                        {
                            openResult_Special[i] = Integer.valueOf(normal_res[i]);
                        }
                    }
                }
            }
            else
            {
                String[] result = openResult.split(",");
                openResult_Normal = new int[result.length];
                for (int i=0;i<result.length;i++)
                {
                    openResult_Normal[i] = Integer.valueOf(result[i]);
                }
            }
        }
    }
    public int[] getOpenResult_Normal() {
        return openResult_Normal;
    }

    public int[] getOpenResult_Special() {
        return openResult_Special;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getOpenExpect() {
        return openExpect;
    }
    public String toString()
    {
        return "data: " + openDate
                + "expect: " + openExpect
                + "number : " + Arrays.toString(openResult_Normal) + "+"
                +"number : " + Arrays.toString(openResult_Special);
    }
}
