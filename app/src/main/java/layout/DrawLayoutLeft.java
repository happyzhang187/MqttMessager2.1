package layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import static android.graphics.Color.RED;

/**
 * Created by Guston on 03/12/17.
 */

public class DrawLayoutLeft extends FrameLayout {



    private Point  TopPoint;
    private Path ThePath;
    private Point  BottomPoint;
    private  Point CenterPoint;
    private Paint ThePaint;
    public void setCenterPoint(Point centerPoint) {
        CenterPoint = centerPoint;
    }

    public Point getCenterPoint() {
        return CenterPoint;
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





    public DrawLayoutLeft(Context context) {

        super(context);


       init();


    }


    public DrawLayoutLeft(Context context, AttributeSet attrs) {
        super(context , attrs);
        init();
    }

    public DrawLayoutLeft(Context context, AttributeSet attrs, int defStyleAttr) {
       super(context,attrs,defStyleAttr);
       init();


    }


   private void init() {

       setWillNotDraw(false);

       TopPoint = new Point(this.getMeasuredWidth() - 50 , 0);
       BottomPoint = new Point(this.getMeasuredWidth() - 50 , this.getMeasuredHeight());
       CenterPoint = new Point(this.getMeasuredWidth() , this.getMeasuredHeight() / 2);
       setWillNotDraw(false);

       ThePath = new Path();
       ThePaint = new Paint();
       ThePaint.setAntiAlias(true);
       ThePaint.setStyle(Paint.Style.FILL);
       ThePaint.setColor(RED);
      // invalidate();


   }


    private void CreatThePath(){

   ThePath.moveTo(0,0);
   ThePath.lineTo(TopPoint.x,TopPoint.y);
   ThePath.quadTo(CenterPoint.x , 0 ,CenterPoint.x , CenterPoint.y);
   ThePath.quadTo(CenterPoint.x , BottomPoint.y , BottomPoint.x , BottomPoint.y);
   ThePath.lineTo(0,BottomPoint.y);
   ThePath.close();


    }

    @Override
    protected  void onDraw(Canvas canvas){
        super.onDraw(canvas);
        CreatThePath();

        Log.d("e", "onDraw: jjjjjjj");
        canvas.drawPath(ThePath,ThePaint);


    }



}
