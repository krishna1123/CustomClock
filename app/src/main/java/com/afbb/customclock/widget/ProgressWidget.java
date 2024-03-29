package com.afbb.customclock.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ProgressWidget extends View {

    private int percent = 25;
    private int radiusOuter = 110, radiusInner = 90;
    private Paint mPaintOuter;
    private Paint mPaintPercent;
    private Paint mInnerCircle, mTextPaint;
    private int mCenterX, mCenterY;
    private int textSize;
    private String mTimedText = percent+"%";
    private int desiredWidth = 300;
    private int desiredHeight = 300;
    private boolean isRunning;
    private boolean isMeasured;

    public ProgressWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();
    }

    public ProgressWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialization();
    }

    public ProgressWidget(Context context) {
        super(context);
        initialization();
    }

    private void initialization() {

        mPaintOuter = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOuter.setColor(Color.DKGRAY);

        mPaintPercent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPercent.setColor(Color.CYAN);

        mInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCircle.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setTextSize(textSize);
    }

    public void getUpdateRadius() {
        if (!isMeasured) {
            isMeasured = true;
            int size = getWidgetWidth() < getWidgetHeight() ? getWidgetWidth() : getWidgetHeight();

            radiusOuter = (int) (size * 0.35f);
            radiusInner = (int) (size * 0.3f);

            textSize = (int) (size * 0.08f);
            setTimedTextSize(textSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        drawSecCircle(canvas);
        drawInnerCircle(canvas);
        drawPercentageText(canvas);
    }

    private void drawInnerCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, radiusInner, mInnerCircle);
    }

    private void drawSecCircle(Canvas canvas) {

        canvas.drawCircle(mCenterX, mCenterY, radiusOuter, mPaintOuter);
        canvas.drawArc(new RectF(mCenterX - radiusOuter, mCenterY - radiusOuter, mCenterX + radiusOuter, mCenterY + radiusOuter), -90, percent*3.6f, true, mPaintPercent);
    }

    public void drawPercentageText(Canvas canvas) {
        RectF areaRect = new RectF(mCenterX - radiusInner, mCenterY - radiusInner, mCenterX + radiusInner, mCenterY + radiusInner);
        RectF bounds = new RectF(areaRect);

        // measure text width
        bounds.right = mTextPaint.measureText(mTimedText, 0, mTimedText.length());
        // measure text height
        bounds.bottom = mTextPaint.descent() - mTextPaint.ascent();

        bounds.left += (areaRect.width() - bounds.right) / 2.0f;
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

        canvas.drawText(mTimedText, bounds.left, bounds.top - mTextPaint.ascent(), mTextPaint);
    }

    public void setTimedTextSize(int textSize) {
        this.textSize = textSize;
        mTextPaint.setTextSize(textSize);
    }

    public void setTimedText(String timedText) {
        this.mTimedText = timedText;
        invalidate();
    }

    public void setPercentage(int percent) {
        this.percent = percent;

        mTimedText = String.valueOf(percent)+"%";
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setWidgetWidth((int) (widthSize * 0.6));
        setWidgetHeight((int) (heightSize * 0.6));

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(getWidgetWidth(), widthSize);
        } else {
            width = getWidgetWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(getWidgetHeight(), heightSize);
        } else {
            height = getWidgetHeight();
        }

        setWidgetWidth(width);
        setWidgetHeight(height);

        getUpdateRadius();

        setMeasuredDimension(width, height);
    }


    public int getWidgetWidth() {
        return desiredWidth;
    }

    public void setWidgetWidth(int clockWidgetWidth) {

        this.desiredWidth = clockWidgetWidth;
    }

    public int getWidgetHeight() {
        return desiredHeight;
    }

    public void setWidgetHeight(int clockWidgetHeight) {
        this.desiredHeight = clockWidgetHeight;
    }
}
