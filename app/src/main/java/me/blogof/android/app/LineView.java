package me.blogof.android.app;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.List;

/**
 * Created by dzq on 16/1/11.
 */
public class LineView extends View{

    private List<LinePoint> points;

    private int horizonLineCount = 3;
    private float horizonLineWidth = 1f;
    private String horizonLineColor = "#C9D6CC";
    /** 用户设置的point中最大的value 用来计算图表横线上的数值**/
    private float maxPointValue = 0f;
    private String lineColor = "#E3E3D8";
    private float lineWidth = 3f;
    private boolean smooth = false;
    private float topBottomPadding = 15f;
    private int currentSelectIndex = 0;
    private String selectedColor = "#B39381";
    private boolean animated = true;

    private Path path;
    private float pathLength;
    private Paint mPaint;

    /**
     *  用于设置动画类型,如果是第一次设置数据源 则为false
     *  false的时候动画为线条逐渐增加的动画
     *  true为直接更新某个点的动画
     **/
    private boolean isDelta = false;

    //当前曲线的动画的偏移量
    private float currentOffset;

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHorizonLine(canvas);
        drawCurve(canvas);
    }

    private void drawHorizonLine(Canvas canvas){
        float lineHeightSpace = (getHeight() - (horizonLineCount*horizonLineWidth))/ horizonLineCount;

        mPaint.setStrokeWidth(horizonLineWidth);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i=1;i<horizonLineCount;i++){
            float y =getHeight() - i*lineHeightSpace - 2*horizonLineWidth;
            mPaint.setColor(Color.parseColor(horizonLineColor));
            canvas.drawLine(0,y,getWidth(), y,mPaint);
            //画横线上的数值
            mPaint.setTextSize(lineWidth*10);
            mPaint.setColor(Color.parseColor(lineColor));
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            canvas.drawText(((int)(maxPointValue/horizonLineCount)*i) +"",3*lineWidth,y-3*lineWidth,mPaint);
        }
    }

    private void drawCurve(Canvas canvas){
        if (smooth){
            mPaint.setPathEffect(new CornerPathEffect(50));
        }
        mPaint.setColor(Color.parseColor(lineColor));
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,mPaint);
        for (int i=0;i<points.size();i++){
            LinePoint p = points.get(i);
            if (p.getX()<pathLength - currentOffset ){
                mPaint.setStrokeWidth(lineWidth + 5);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(p.getX(),p.getY(),5,mPaint);
            }
        }
    }

    private void drawPathWithAnimated(Path path){
        PathMeasure measure = new PathMeasure(path, false);
        pathLength = measure.getLength();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f);
        AccelerateDecelerateInterpolator a = new AccelerateDecelerateInterpolator();
        animator.setInterpolator(a);
        animator.setDuration(1300);
        animator.start();


    }

    /** 该方法由动画的回调来调用会传入动画的偏移增量 从0-1 **/
    public void setPhase(float phase)
    {
        mPaint.setPathEffect(createPathEffect(pathLength, phase, 0.0f));
        invalidate();
    }

    private PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        currentOffset =  Math.max(phase * pathLength, offset);
        return new DashPathEffect(new float[] { pathLength, pathLength },
                currentOffset);
    }

    public void setPoints(List<LinePoint> points) {
        this.points = points;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculatePathAndPoint();
        if (animated){
            drawPathWithAnimated(path);
        } else {
            invalidate();
        }
    }

    private void calculatePathAndPoint(){
        if (null == points || points.size() == 0){
            return;
        }
        for (int i=0;i<points.size();i++){
            float value = points.get(i).value;
            if (value > maxPointValue){
                maxPointValue = value;
            }
        }
        path = new Path();
        float heightCell = (getHeight() - 2*topBottomPadding)/maxPointValue;
        float widthCell =  getWidth()/points.size() + 1;

        for (int i=0;i<points.size();i++){
            float x = widthCell/2 + i*widthCell;
            float y = getHeight() - heightCell * points.get(i).value - topBottomPadding;
            if (i==0){
                path.moveTo(x,y);
            } else{
                path.lineTo(x, y);
            }
            points.get(i).setPath(path);
            points.get(i).setXY(x,y);
        }
    }

    public void reloadData(){

    }

    //下面是get set方法
    public LinePoint getPoint(int index){
        return points.get(index);
    }


}
