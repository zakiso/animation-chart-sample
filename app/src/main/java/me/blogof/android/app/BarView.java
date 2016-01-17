package me.blogof.android.app;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.List;

/**
 * Created by dzq on 16/1/12.
 */

public class BarView extends View {

    private List<Pillar> pillars;
    private float pillarWidth = 20f;
    private int orientation = 1;  // 0表示横向1表示纵向  默认为纵向
    private float padding = 10f;
    private float maxValue = 100;
    private Paint mPaint;
    private float pillarRadius = 5f;
    private String titleColor = "#E3E3D8";
    private float titleSize = 30f;
    private boolean isDelta = false;
    private ObjectAnimator animator;

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.cute_bar_view);
        orientation = t.getInt(R.styleable.cute_bar_view_orientation, 1);
        pillarWidth = t.getDimension(R.styleable.cute_bar_view_pillar_width, 30f);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPillar(canvas);
    }

    private void drawPillar(Canvas canvas) {
        if (null == pillars || pillars.size() == 0) {
            return;
        }

        //柱与柱之间的间隔
        float space;
        if (orientation == 1) {
            space = (getWidth() - (pillars.size() * pillarWidth)) / pillars.size();
        } else {
            space = (getHeight() - (pillars.size() * pillarWidth)) / pillars.size();
        }
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(titleSize);

        for (int i = 0; i < pillars.size(); i++) {

            Pillar p = pillars.get(i);
            Rect bounds = new Rect();
            mPaint.getTextBounds(p.title, 0, p.title.length(), bounds);
            float titleHeight = bounds.height();
            float titleWidth = bounds.width();
            float itemCell = (getHeight() - 4 * padding - titleHeight) / maxValue;
            float left = i * pillarWidth + (i + 0.5f) * space;
            float top = padding;
            float right = left + pillarWidth;
            float bottom = getHeight() - 2 * padding - titleHeight;
            RectF r = new RectF(left, top, right, bottom);
            mPaint.setColor(Color.parseColor(pillars.get(i).color));
            //画柱子的背景
            canvas.drawRoundRect(r, pillarRadius, pillarRadius, mPaint);
            //画柱子

            float pTop;
            if (p.currentValue >= maxValue) {
                pTop = top;
            } else {
                pTop = bottom - (p.currentValue * itemCell);
            }
            RectF r1 = new RectF(left, pTop, right, bottom);
            mPaint.setColor(Color.parseColor(p.fillColor));
            canvas.drawRoundRect(r1, pillarRadius, pillarRadius, mPaint);
            mPaint.setColor(Color.parseColor(titleColor));
            float titleLeft;
            if (titleWidth > (right - left)) {
                titleLeft = left - titleWidth / 4;
            } else {
                titleLeft = left + titleWidth / 4;
            }
            canvas.drawText(p.title, titleLeft, getHeight() - padding, mPaint);
        }
    }

    public List<Pillar> getPillars() {
        return pillars;
    }

    public void setPillars(List<Pillar> pillars) {
        this.pillars = pillars;
        isDelta = false;
        startDrawView();
    }

    private void startDrawView() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            if (!isDelta) {
                setPhase(1);
            }
        }
        animator = ObjectAnimator.ofFloat(this, "phase", 0.0f, 1.0f);
        AccelerateDecelerateInterpolator a = new AccelerateDecelerateInterpolator();
        animator.setInterpolator(a);
        animator.setDuration(1000);
        animator.start();
    }

    public void reloadData() {
        isDelta = true;
        startDrawView();
    }

    public void setPhase(float phase) {
        for (int i = 0; i < pillars.size(); i++) {
            Pillar p = pillars.get(i);
            float currentValue = p.currentValue;
            if (p.value > currentValue && isDelta) {
                p.currentValue += phase * (p.value - currentValue);
            }else if (p.value < currentValue && isDelta){
                p.currentValue -= phase * (currentValue - p.value);
            }else if (!isDelta) {
                p.currentValue = phase * p.value;
            }
        }
        invalidate();
    }
}
