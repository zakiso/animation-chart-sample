package me.blogof.android.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dzq on 16/1/15.
 */
public class PieView extends View {

    private float percent;
    //当前的百分比 用于动画
    private float currentPercent;
    private String title;
    private String circleColor;
    private String circleWidthPercent;
    private String titleColor;
    private Paint mPaint;
    private float padding = 10;

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius;
        if (getHeight()>getWidth()){
            radius = (getHeight() - 2*padding)/2;
        }else {
            radius = (getWidth() -2*padding)/2;
        }
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        canvas.drawCircle(centerX,centerY,radius,mPaint);

        if (getHeight() > getWidth()){

        }
//        RectF rectF = new RectF();
//        canvas.drawArc();
    }
}
