package ng.com.coursecode.piqmessenger.Model__;

import android.content.Context;
import android.content.res.Resources;

import java.util.Calendar;
import java.util.TimeZone;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 01/11/2017.
 */

public class TimeModel {

    Context context;
    public String from_app="from_app";
    Resources res;
    String text;

    public TimeModel(Context con){
        this.context=con;
        res = context.getResources();
    }

    public static String getPhpTime() {
        long bn=System.currentTimeMillis()/1000;
        return ""+bn;
    }


    public int getIntVal(String posii) {
        int time=334;
        try{
            time=Integer.parseInt(posii);
        }catch (Exception er){
            reportException(er);
            int yu=posii.length();
            int vll=1;
            for (int oi=0; oi>yu; oi++){vll=vll*10; }
            vll = vll-1;
            int fowl=vll*10;
            for(int i=vll; i<fowl; i++){
                String jkj=""+i;
                if (posii.contentEquals(jkj)){
                    time= i;
                }
            }
        }
        return time;
    }

    private void reportException(Exception er) {

    }

    public boolean getBooleanVal(CharSequence posii) {
        String posiit=""+posii;
        String jkj="0";
        return !posiit.contentEquals(jkj);
    }

    public long getLongTimeVal(String posii) {
        long time=334;
        try{
            time=Long.parseLong(posii);
        }catch (Exception er){
            reportException(er);
        }
        return time;
    }
    public static long getLongTime(String posii) {
        long time=334;
        try{
            time=Long.parseLong(posii);
        }catch (Exception er){
        }
        return time;
    }

    public static long getLongPhpTime(String posii) {
        long time=334;
        try{
            time=Long.parseLong(posii);
        }catch (Exception er){
        }
        return time;
    }

    public String getFormattedTime(long timee) {
        Date dh=new Date(timee);
//        Calendar.get()
        return null;
    }

    public Context getContexto(){
        return context;
    }

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public boolean isDOBValid(String s) {
        //match 12-3-1993
        String[] strings=s.split("-");
        if(strings.length!=3){
            return false;
        }
        for (String aJ : strings) {
            if(aJ.isEmpty()){
                return false;
            }
        }
        return s.length() > 4;
    }

    public boolean isCountryValid(String s) {
        return s.length() > 0;
    }

    public boolean isUsernameValid(String s) {
        return s.length() > 4;
    }

    public boolean isSpace(String s) {
        return s.contains(" ");
    }

    public boolean isUsernameEmailValid(String s) {
        return s.length() > 4;
    }

    public String getDWM2(String timeStamp){
        //HURRAY DWM AND DWM2 BOTH WORKS FINE.
        if(timeStamp==null){
            return ""+0;
        }
        DateTime this_time=DateTime.now(TimeZone.getDefault());
        long toInMilli=Long.parseLong(timeStamp.trim())*1000;

        long one_min=60;
        long one_hr=3600;

        DateTime dateTime = DateTime.forInstant(toInMilli, TimeZone.getDefault());
        int numb=dateTime.numDaysFrom(this_time);
        long numSec=dateTime.numSecondsFrom(this_time);
        String tbr=String.format(res.getString(R.string.minute), 1);;

        if(numb>0){
            //check for number of days
            if(numb>30){
                //show dd/mm
                int mon=dateTime.getMonth();
                int dayy=dateTime.getDay();
                tbr=""+dayy+'/'+mon;
            }else if(numb>7){
                numb=numb/7;
                tbr=String.format(res.getString(R.string.week), numb);;
            }else{
                tbr=String.format(res.getString(R.string.day), numb);
            }
        }else{
            //show 1d
            tbr=String.format(res.getString(R.string.day), 1);
            int num_hr=(int)(numSec/one_hr);
            int num_min=(int)(numSec/one_min);

            if(num_hr>0){
                tbr=String.format(res.getString(R.string.hour), num_hr);
            }else if(num_min>0){
                //min
                tbr=String.format(res.getString(R.string.minute), num_min);
            }else{
                //sec
                tbr=String.format(res.getString(R.string.second), numSec);
            }
        }
        return tbr;
    }

    public String getDWM(String TtimeStamp){
        //tobe returned
        if(TtimeStamp==null){
            return ""+0;
        }

        String tt=TtimeStamp;
        long this_time=new Date().getTime();
        long toInMilli=Long.parseLong(tt.trim())*1000;
        long to=this_time-toInMilli;

        long one_min=60*1000;
        long one_hr=3600*1000;
        long one_day=one_hr*24;
        long one_week=one_day*7;

        int numb=(int)(to/one_day);
        String tbr="1min";

        if(numb>0){
            //check for number of days
            if(numb>30){
                //show dd/mm
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(toInMilli);
                int min=calendar.get(Calendar.MINUTE);
                int mon=calendar.get(Calendar.MONTH);
                int dayy=calendar.get(Calendar.DAY_OF_MONTH);
                tbr=""+dayy+'/'+mon;
            }else if(numb>7){
                //show 1w

                numb=numb/7;
                tbr=String.format(res.getString(R.string.week), numb);;
            }else{
                tbr=String.format(res.getString(R.string.day), numb);
            }
        }else{
            //show 1d
            tbr=String.format(res.getString(R.string.day), 1);
            int num_hr=(int)(to/one_hr);
            int num_min=(int)(to/one_min);

            if(num_hr>0){
                tbr=String.format(res.getString(R.string.hour), num_hr);
            }else if(num_min>0){
                //min
                tbr=String.format(res.getString(R.string.minute), num_min);
            }else{
                //sec
                tbr=String.format(res.getString(R.string.second), to);
            }
        }
        return tbr;
    }

    public String getDWMDay(String TtimeStamp){
        //tobe returned
        if(TtimeStamp==null){
            return ""+0;
        }

        String tt=TtimeStamp;
        long this_time=new Date().getTime();
        long toInMilli=Long.parseLong(tt.trim())*1000;
        long to=this_time-toInMilli;

        long one_hr=3600*1000;
        long one_day=one_hr*24;

        int numb=(int)(to/one_day);
        String tbr=String.format(res.getString(R.string.day), numb);

        if(numb>0){
            //check for number of days
            if(numb>30 || numb>7){
                //show dd/mm
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(toInMilli);
                int mon=calendar.get(Calendar.MONTH);
                int dayy=calendar.get(Calendar.DAY_OF_MONTH);
                int year=calendar.get(Calendar.YEAR);
                tbr=""+dayy+'/'+mon+"/"+year;
            }else{
                tbr=String.format(res.getString(R.string.day), numb);
            }
        }

        if(numb<1){
            tbr=res.getString(R.string.today);
        }else if(numb==1){
            tbr=res.getString(R.string.yesterday);
        }
        return tbr;
    }

    public int getIntt(long l){
        int y=((int) l);
        return 0;
    }

    public String getDWM3(String time) {
        return getDWM2(time);
    }

    public String getDWMTime(String TtimeStamp) {
        //tobe returned
        if(TtimeStamp==null){
            return ""+0;
        }

        String tt=TtimeStamp;
        long this_time=new Date().getTime();
        long toInMilli=Long.parseLong(tt.trim())*1000;

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(toInMilli);
        int min=calendar.get(Calendar.MINUTE);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);

        String tbr=hour+":"+min;
        if(min<10){
            //mk it 01;
            tbr=hour+":0"+min;
        }
        return tbr;
    }

    public static long aDayAgo() {
        DateTime this_time=DateTime.now(TimeZone.getDefault());
        this_time=this_time.minusDays(1);
        return this_time.getMilliseconds(TimeZone.getDefault());
    }

    public static long aDayAgo_() {
        DateTime this_time=DateTime.now(TimeZone.getDefault());
        this_time=this_time.minusDays(1);
        return (this_time.getMilliseconds(TimeZone.getDefault())/1000);
    }
}
