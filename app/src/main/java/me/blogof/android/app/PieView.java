package me.blogof.android.app;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.List;

/**
 * Created by dzq on 16/1/15.
 */
public class PieView extends View {

    private String TAG = "PieView";
    private List<Pie> pies;
    private String title;
    private String circleColor;
    private String circleWidthPercent;
    private String titleColor;
    private String backgroundColor = "#dcdcdc";
    private Paint mPaint;
    //图表距离边框的padding
    private float padding = 10;
    //pie和pie之间的padding
    private float pieSpace = 5;
    private float maxValue;
    private float lastAngle = 0;
    private float phase = 0f;

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
        if (getHeight() < getWidth()){
            radius = (getHeight() - 2*padding)/2;
        }else {
            radius = (getWidth() -2*padding)/2;
        }
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        mPaint.setStrokeWidth(radius/4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(centerX,centerY,radius - (radius/5),mPaint);
        drawPie(canvas);
    }

    private void drawPie(Canvas canvas){
        if (null == pies || pies.size() == 0){
            return;
        }
        lastAngle = 0;
        maxValue = 0;
        float radius;
        if (getHeight() < getWidth()){
            radius = (getHeight() - 2*padding)/2;
        }else {
            radius = (getWidth() -2*padding)/2;
        }
        mPaint.setStrokeWidth(radius/4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);


        float leftX,leftY,rightX,rightY;
        if (getWidth()>getHeight()){
            leftX = (getWidth() - getHeight() + 2*padding)/2 + radius/8;
            leftY = padding + radius/8;
            rightX = getWidth() - leftX;
            rightY = getHeight() - leftY;
        }else {
            leftX = padding + radius/8;
            leftY = (getHeight() - getWidth() + 2*padding)/2 + radius/8;
            rightX = getWidth() - leftX;
            rightY = getHeight() - leftY;
        }
        RectF rectF = new RectF(leftX,leftY,rightX,rightY);
        for (int i=0;i<pies.size();i++){
            Pie p = pies.get(i);
            float value = p.getValue()>0?p.getValue():1;
            maxValue += value;
        }
        for (int i=0;i<pies.size();i++){
            Pie p = pies.get(i);
            double percent = ((p.getValue()>=0?p.getValue():1)/maxValue)*(360 - pies.size()*pieSpace);
            mPaint.setColor(Color.parseColor(p.color));
            canvas.drawArc(rectF,lastAngle + i*pieSpace, (float)(phase * percent),false,mPaint);
            Log.d(TAG, "drawPie phase:+" +phase + ",lastAngle:" + lastAngle + ",percent:"+percent);
            lastAngle += (float) percent;
        }
    }

    public void setPies(List<Pie> pies) {
        this.pies = pies;
        startDrawView();

    }

    private void startDrawView() {
        clearAnimation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "phase", 0.0f, 1.0f);
        AccelerateDecelerateInterpolator a = new AccelerateDecelerateInterpolator();
        animator.setInterpolator(a);
        animator.setDuration(1300);
        animator.start();
    }

    private void setPhase(float phase){
        this.phase = phase;
        invalidate();
    }

    public void  reloadData(){
        startDrawView();
    }

    //下面全是get set方法

    public Pie getPie(int index){
        return pies.get(index);
    }
    public List<Pie> getPies() {
        return pies;
    }

    public String getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(String circleColor) {
        this.circleColor = circleColor;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getPieSpace() {
        return pieSpace;
    }

    public void setPieSpace(float pieSpace) {
        this.pieSpace = pieSpace;
    }

    public float getPadding() {
        return padding;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }
}
