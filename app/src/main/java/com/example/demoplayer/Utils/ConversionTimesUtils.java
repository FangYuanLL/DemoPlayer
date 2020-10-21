package com.example.demoplayer.Utils;

public class ConversionTimesUtils {

    public static String ConversionTime(int mills){
        String result = "";
        int tempSeconds = mills/1000;
        int mHour = (tempSeconds)/(60*60);
        int mMinutes = (tempSeconds-3600*mHour)/60;
        int mSeconds = tempSeconds-3600*mHour-60*mMinutes;
        if (mHour>=10){
            result = result+mHour+":";
        }else{
            result = result+"0"+mHour+":";
        }
        if (mMinutes>=10){
            result = result+mMinutes+":";
        }else{
            result = result+"0"+mMinutes+":";
        }
        if (mSeconds>=10){
            result = result+mSeconds;
        }else{
            result = result+"0"+mSeconds;
        }
        return result;
    }

    public static String CheckTime(String timeStr){
        String[] mTimes = timeStr.split(":");
        int mHour = Integer.valueOf(mTimes[0]);
        int mMinute = Integer.valueOf(mTimes[1]);
        int mSecond = Integer.valueOf(mTimes[2]);

        if (mSecond+1>=60){
            mSecond = 0;
            mMinute = mMinute+1;
            if (mMinute>=60){
                mMinute = 0;
                mHour++;
            }else{
                mMinute++;
            }
        }else{
            mSecond++;
        }

        int mills = (mHour*3600+mMinute*60+mSecond)*1000;
        return ConversionTime(mills);
    }
}
