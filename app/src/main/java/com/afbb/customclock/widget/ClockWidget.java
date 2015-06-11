package com.afbb.customclock.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *  Clock widget provided methods.
 * 
 * public void setTimedTextSize(int textSize)
 * public void setTimedText(String timedText)
 * public void setTime(int hours, int minutes, int seconds)
 * public void setRadiusHours(int hoursRadius)
 * public void setRadiusMinutes(int minutesRadius)
 * public void setRadiusSeconds(int secondsRadius)
 * public void setRadiusInnerCircle(int innerCircleRadius)
 * public void setHoursColors(int defalutColor, int filledColor)
 * public void setMinutesColor(int defaultColor, int filledColor)
 * public void setSecondsColor(int defaultColor, int filledColor)
 * public void setInnerCircleColor(int color)
 * public void setCustomTypeface(Typeface customTypeface)
 * 
 * </pre>
 * @author android {@link ClockWidget} class.
 *
 */
public class ClockWidget extends View{

	private int hours=0, mins=0, secs=0, millis=0;
	private int radiusHours=135, radiusMins=125, radiusSecs=110, radiusInner=90;
	private Paint mPaintHours, mPaintMins, mPaintSecs;
	private Paint mRepaintHours, mRepaintMins, mRepaintSecs;
	private Paint mInnerCircle, mTextPaint;
	private int mCenterX, mCenterY;
	private int textSize;
	private String mTimedText="Clock";
	private int desiredWidth=300;
	private int desiredHeight=300;

	/**
	 * parameterized constructor for the clock widget. It accepts context, {@link AttributeSet} object, defStyleAttr.
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ClockWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialization();
	}

	/**
	 * parameterized constructor for the clock widget. It accepts context, {@link AttributeSet} object.
	 * @param context
	 * @param attrs
	 */
	public ClockWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialization();
	}

	/**
	 * parameterized constructor for the clock widget. It accepts context.
	 * @param context
	 */
	public ClockWidget(Context context) {
		super(context);
		initialization();
	}

	/**
	 * Initialization of clock widget.
	 */
	private void initialization() {

		mPaintHours=new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintHours.setColor(Color.LTGRAY);

		mRepaintHours=new Paint(Paint.ANTI_ALIAS_FLAG);
		mRepaintHours.setColor(Color.YELLOW);

		mPaintMins=new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintMins.setColor(Color.GRAY);

		mRepaintMins=new Paint(Paint.ANTI_ALIAS_FLAG);
		mRepaintMins.setColor(Color.MAGENTA);

		mPaintSecs=new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintSecs.setColor(Color.DKGRAY);

		mRepaintSecs=new Paint(Paint.ANTI_ALIAS_FLAG);
		mRepaintSecs.setColor(Color.CYAN);

		mInnerCircle=new Paint(Paint.ANTI_ALIAS_FLAG);
		mInnerCircle.setColor(Color.BLACK);

		mTextPaint=new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(textSize);
	}

	/**
	 * This method is used to getting the updated values for the clock widget.
	 */
	public void getUpdateRadius()
	{
		System.out.println("UPDATE_1");
		if(textSize==0)
		{
			System.out.println("UPDATE_2");
			int size=getClockWidgetWidth()<getClockWidgetHeight()?getClockWidgetWidth():getClockWidgetHeight();

			radiusHours=(int)(size*0.45f);
			radiusMins=(int)(size*0.4f);
			radiusSecs=(int)(size*0.35f);
			radiusInner=(int) (size*0.3f);

			textSize=(int)(size*0.12f);
			setTimedTextSize(textSize);
		}
	}


	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mCenterX=getWidth()/2;
		mCenterY=getHeight()/2;

		drawHourCircle(canvas);
		drawMinCircle(canvas);
		drawSecCircle(canvas);
		drawInnerCircle(canvas);
		drawTimedText(canvas);
	}

	/**
	 * This method is used to drawing the inner circle on the canvas.
	 * @param canvas draw circle on canvas.
	 */
	private void drawInnerCircle(Canvas canvas) {
		canvas.drawCircle(mCenterX, mCenterY, radiusInner, mInnerCircle);
	}

	/**
	 * This method is used to drawing the circle on the canvas for the hours.
	 * @param canvas draw circle on canvas.
	 */
	private void drawHourCircle(Canvas canvas) {
		canvas.drawArc(new RectF(mCenterX-radiusHours, mCenterY-radiusHours, mCenterX+radiusHours, mCenterY+radiusHours), -90, 360, true, mPaintHours);
		canvas.drawArc(new RectF(mCenterX-radiusHours, mCenterY-radiusHours, mCenterX+radiusHours, mCenterY+radiusHours), -90, getAngle(hours*0.6f), true, mRepaintHours);
	}
	/**
	 * This	 method is used to drawing the circle on the canvas for the minutes.
	 * @param canvas draw circle on canvas.
	 */
	private void drawMinCircle(Canvas canvas) {
		canvas.drawArc(new RectF(mCenterX-radiusMins, mCenterY-radiusMins, mCenterX+radiusMins, mCenterY+radiusMins), -90, 360, true, mPaintMins);
		canvas.drawArc(new RectF(mCenterX-radiusMins, mCenterY-radiusMins, mCenterX+radiusMins, mCenterY+radiusMins), -90, getAngle(mins), true, mRepaintMins);
	}

	/**
	 * This method is used to drawing the circle on the canvas for the seconds.
	 * @param canvas draw circle on canvas.
	 */
	private void drawSecCircle(Canvas canvas) {
		/*int millisAngle=(millis*3)/250;
		System.out.println("MILLIS:"+millisAngle);*/
		int millisAngle=(int) (millis*0.006f);
		float secsAngle=getAngle(secs)-millisAngle;
		
		System.out.println("secs:"+secs+" millis:"+millis+"  secsAngle"+secsAngle+ " millisAngle:"+millisAngle);
		canvas.drawArc(new RectF(mCenterX-radiusSecs, mCenterY-radiusSecs, mCenterX+radiusSecs, mCenterY+radiusSecs), -90, 360, true, mPaintSecs);
		canvas.drawArc(new RectF(mCenterX-radiusSecs, mCenterY-radiusSecs, mCenterX+radiusSecs, mCenterY+radiusSecs), -90, secsAngle, true, mRepaintSecs);
	}

	/**
	 * This method is used to getting the angle for the timeValue.
	 * @param timeValue value of the time.
	 * @return which returns the angle for the given timeValue.
	 */
	private float getAngle(float timeValue)
	{
		float angle=(timeValue*6);
		return angle;
	}

	/**
	 * This method is used to drawing the timed text on the canvas.
	 * @param canvas canvas of the clock widget.
	 */
	public void drawTimedText(Canvas canvas)
	{
		RectF areaRect=new RectF(mCenterX-radiusInner, mCenterY-radiusInner, mCenterX+radiusInner, mCenterY+radiusInner);
		RectF bounds = new RectF(areaRect);

		// measure text width
		bounds.right = mTextPaint.measureText(mTimedText, 0, mTimedText.length());
		// measure text height
		bounds.bottom = mTextPaint.descent() - mTextPaint.ascent();

		bounds.left += (areaRect.width() - bounds.right) / 2.0f;
		bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

		canvas.drawText(mTimedText, bounds.left, bounds.top - mTextPaint.ascent(), mTextPaint);
	}

	/**
	 * This method is used to getting the timedTextSize.
	 * @return which returns the timed text size.
	 */
	public int getTimedTextSize() {
		return textSize;
	}

	/**
	 * This method is used to setting the timedTextSize.
	 * @param textSize returns the timed text size.
	 */
	public void setTimedTextSize(int textSize) {
		this.textSize = textSize;
		mTextPaint.setTextSize(textSize);
	}

	/**
	 * This method is used to setting the timedText.
	 * @param timedText timed text of the clock widget.
	 */
	public void setTimedText(String timedText)
	{
		this.mTimedText = timedText;
		invalidate();
	}

	/**
	 * This method is used to setting the time for the given hours, minutes, seconds.
	 * @param hours current hour
	 * @param minutes current minute
	 * @param seconds current second
	 */
	public void setTime(int hours, int minutes, int seconds, int millis)
	{
		this.hours=hours;
		this.mins=minutes;
		this.secs=seconds;
		this.millis=millis;
		
		mTimedText=String.format("%02d", hours)+":"+String.format("%02d", mins)+":"+String.format("%02d", secs);
		invalidate();
	}

	/**
	 * This method is used setting the radius of the minutes circle.
	 * @param minutesRadius radius of the minutes.
	 */
	public void setRadiusMinutes(int minutesRadius)
	{
		this.radiusMins = minutesRadius;
	}

	/**
	 * This method is used to setting the radius of the hours circle.
	 * @param hoursRadius radius of the hours.
	 */
	public void setRadiusHours(int hoursRadius)
	{
		this.radiusHours = hoursRadius;
	}

	/**
	 * This method is used to setting the radius of the seconds circle.
	 * @param secondsRadius radius of the seconds.
	 */
	public void setRadiusSeconds(int secondsRadius)
	{
		this.radiusSecs=secondsRadius;
	}

	/**
	 * This method is used to setting the radius of the inner circle.
	 * @param innerCircleRadius radius of the inner circle.
	 */
	public void setRadiusInnerCircle(int innerCircleRadius)
	{
		this.radiusInner=innerCircleRadius;
	}

	/**
	 * This method is used to setting the seconds colors. default color, filled color.
	 * @param filledColor filled color of the seconds circle.
	 */
	public void setSecondsColor(int defaultColor, int filledColor)
	{
		mPaintSecs.setColor(defaultColor);
		mRepaintSecs.setColor(filledColor);
	}

	/**
	 * This method is used to setting the inner circle color.
	 * @param color color of the inner circle.
	 */
	public void setInnerCircleColor(int color)
	{
		mInnerCircle.setColor(color);
	}

	/**
	 * This method is used to setting the type face of the {@link ClockWidget} timed text.
	 * @param customTypeface type face of the {@link ClockWidget} timed text.
	 */
	public void setCustomTypeface(Typeface customTypeface) {
		mTextPaint.setTypeface(customTypeface);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		setClockWidgetWidth((int)(widthSize*0.6));
		setClockWidgetHeight((int)(heightSize*0.6));

		int width;
		int height;

		//Measure Width
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			width = Math.min(getClockWidgetWidth(), widthSize);
		} else {
			width = getClockWidgetWidth();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			height = Math.min(getClockWidgetHeight(), heightSize);
		} else {
			height = getClockWidgetHeight();
		}

		setClockWidgetWidth(width);
		setClockWidgetHeight(height);

		getUpdateRadius();

		setMeasuredDimension(width, height);
	}

	/**
	 * This method is used to getting the clockWidgetWidth.
	 * @return which returns the clockWidget width.
	 */
	public int getClockWidgetWidth() {
		return desiredWidth;
	}

	/**
	 * This method is used to setting the clock widget width.
	 */
	public void setClockWidgetWidth(int clockWidgetWidth) {
		this.desiredWidth = clockWidgetWidth;
	}

	/**
	 * This method is used to getting the clock widget height.
	 * @return which returns the clock widget height.
	 */
	public int getClockWidgetHeight() {
		return desiredHeight;
	}

	/**
	 * This method is used to setting the clock widget height.
	 * @param clockWidgetHeight clock widget height.
	 */
	public void setClockWidgetHeight(int clockWidgetHeight) {
		this.desiredHeight = clockWidgetHeight;
	}
}
