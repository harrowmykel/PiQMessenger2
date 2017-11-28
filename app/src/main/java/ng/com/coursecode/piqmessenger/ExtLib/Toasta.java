package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by harro on 03/11/2017.
 */

public class Toasta {

    public Toasta(){
    }

    public static void makeText(Context context, int res, int duration){
//        String hj=context.getClass().getSimpleName();
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, hj, Toast.LENGTH_SHORT).show();

    }

    public static void makeText(Context context, String res, int duration){
//        String hj=context.getClass().getSimpleName();
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, hj, Toast.LENGTH_SHORT).show();

    }
}
