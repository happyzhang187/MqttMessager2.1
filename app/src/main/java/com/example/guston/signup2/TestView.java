package com.example.guston.signup2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by Guston on 05/12/17.
 */

public class TestView extends View {
    private Point  TopPoint;
    private Path ThePath;
    private Point  BottomPoint;
    private  Point CenterPoint;
    private Paint ThePaint;
    float UnstableX ;
    float TheCountedY ;
    float BaseX ;
    float UnstableXOfExact ;
    float StartedX;
    float StaredUnstableX;
    float StartedUnstableX;
    Boolean IsStartChangededable = false;
    Boolean IsAnimition = false;
    Boolean IsLoginAble = false;
    Path TheStartedPath;
    Boolean IScleanedCanvas = false;

     private Animator.AnimatorListener mListener ;

    public void setCenterPoint(Point centerPoint) {
        CenterPoint = centerPoint;
    }

    public Point getCenterPoint() {
        return CenterPoint;
    }

    public Animator.AnimatorListener getmListener() {
        return mListener;
    }

    public void setmListener(Animator.AnimatorListener mListener) {
        this.mListener = mListener;
    }

    public void setThePath(Path thePath) {
        ThePath = thePath;
    }

    public Path getThePath() {
        return ThePath;
    }



    public Point getTopPoint() {
        return TopPoint;
    }

    public Point getBottomPoint() {
        return BottomPoint;
    }



    public void setTopPoint(Point topPoint) {
        TopPoint = topPoint;
    }

    public void setBottomPoint(Point bottomPoint) {
        BottomPoint = bottomPoint;
    }




    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }









    private void init() {

        setWillNotDraw(false);

        TopPoint = new Point(getMeasuredWidth() / 2 , 0);
        BottomPoint = new Point(getMeasuredWidth() / 2 , getMeasuredHeight());
        CenterPoint = new Point( (getMeasuredWidth() / 3) * 2 , getMeasuredHeight() / 2);
        setWillNotDraw(false);

        ThePath = new Path();
        ThePaint = new Paint();
        ThePaint.setAntiAlias(true);
        ThePaint.setStyle(Paint.Style.FILL);
        ThePaint.setColor(getResources().getColor(R.color.leftcolor));
        // invalidate();


    }


    private  float initTheNum( float UnstableX){
        float height = getMeasuredHeight() / 2;
        float CenterY =  ( height*height - UnstableX * UnstableX) / (2*height);
        return CenterY;


    }



    private void CreatThePath( boolean IsAnimation,  boolean changeable ){

        TheCountedY = initTheNum(UnstableX);
         if (!IsAnimation) {
             BaseX = getMeasuredWidth() * (float) 0.382;
             UnstableX = getMeasuredWidth() * (float)0.236;


         }

         if (!changeable) {
             UnstableXOfExact = UnstableX + BaseX;
         }
         else {

             UnstableXOfExact = getMeasuredWidth();
         }

        ThePath.moveTo(0,0);
        ThePath.lineTo(BaseX,0);

        ThePath.quadTo(UnstableXOfExact, TheCountedY , UnstableXOfExact,getMeasuredHeight() / 2);
        ThePath.quadTo(UnstableXOfExact , getMeasuredHeight() - TheCountedY , BaseX , getMeasuredHeight());
        ThePath.lineTo(0,getMeasuredHeight());
        ThePath.close();



    }

public void StartAnimate(){
        StartedX = BaseX;

        final float TheDistanceNeedstoMove = (getMeasuredWidth()  - StartedX);
    ValueAnimator animator = ValueAnimator.ofFloat(0,1).setDuration(400);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                       float fraction = valueAnimator.getAnimatedFraction();
            BaseX = TheDistanceNeedstoMove *  fraction  + StartedX;
           // BaseX = StartedX *fraction;

            if ((UnstableXOfExact) >= getMeasuredWidth()){

                IsStartChangededable = true;

            }

                               invalidate();


        }
    }
    );

IsAnimition = true;
animator.addListener(mListener);
animator.start();


}

public void LoginAnimation(){
    StartedX = BaseX;
    StartedUnstableX = UnstableX;
    Log.d("e", "LOGINIIIIIIIIIIIIIIIIIIIIIII" + StartedUnstableX +"HHHHH" +
            ""+ UnstableX);




    ValueAnimator animator = ValueAnimator.ofFloat(0,1).setDuration(400);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fraction = valueAnimator.getAnimatedFraction();

            BaseX = StartedX * (1 - fraction);
            UnstableX = StartedUnstableX * (1 - fraction);


            Log.d("e", "LOGINMIJSDHJAHDAHGDFGHAVDFJSADVJH" + StartedUnstableX +"HHHHH" +
                    ""+ UnstableX + "fraction" + fraction);
            invalidate();
        }
    });

    IsLoginAble = true;
    IsAnimition = true;
    animator.addListener(mListener);
    animator.start();


}



    @Override
    protected void  onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!IsAnimition){
            CreatThePath(false,false);

            Log.d("e", "onDraw: is not animation");
            canvas.drawPath(ThePath, ThePaint);


        }



        else if (IsLoginAble){

         /*   if (!IScleanedCanvas){

               canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                IScleanedCanvas = true;
            }

*/
          //  canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

             //ThePath.reset();
            ThePath.reset();
            CreatThePath(true,false);

            Log.d("e", "onDraw  IS loginable"+UnstableX);
            canvas.drawPath(ThePath,ThePaint);
            //canvas.clipPath(ThePath);
           // canvas.drawPath(TheStartedPath, ThePaint);

        }

       else if ( !IsStartChangededable ){

            CreatThePath(true,false);

            Log.d("e", "onDraw: animation but not changeable");
            canvas.drawPath(ThePath, ThePaint);


        }

       else if(IsStartChangededable) {

            CreatThePath(true,true);

            Log.d("e", "onDraw: jjjjjjj");
            canvas.drawPath(ThePath, ThePaint);

        }
    }


}
