package com.example.guston.signup2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Guston on 15/12/17.
 */

public class DelayShowUpElement {
    long time = 200;
    List<View> ViewGroup = new LinkedList<View>();
    Animator.AnimatorListener mListener;
    AnimatorListenerAdapter mListenerAdapter;

    DelayShowUpElement(View... arg){

        for (View view:
             arg) {

            ViewGroup.add(view);

        }
    }

    public void ShowUpTheView(){

        for (View view:
           ViewGroup  ) {

            view.setVisibility(View.VISIBLE);


        }
        for (View view:
            ViewGroup ) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


             view.animate().alpha(1f).setDuration(200)
                     .setListener(mListenerAdapter)
                     .start();


                }
            }, time);


            time=+200;


        }

    }



    public void RemoveTheView(){

        for (View view:
            ViewGroup ) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    view.animate().alpha(0f).setDuration(100)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    view.setVisibility(View.GONE);
                                }
                            })
                            .setListener(mListenerAdapter)
                            .start();


                }
            }, time);


            time=+100;


        }



    }

}
