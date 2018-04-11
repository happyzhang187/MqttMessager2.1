package transition;

import android.app.Activity;
import android.os.Build;
import android.transition.TransitionInflater;

/**
 * Created by Guston on 06/03/18.
 */

public class transitionHelper {

    public  static  void setSharedElementEnterTransition(final Activity activity, int transition){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        activity.getWindow().setSharedElementEnterTransition(TransitionInflater.from(activity).inflateTransition(transition));


    }





}
