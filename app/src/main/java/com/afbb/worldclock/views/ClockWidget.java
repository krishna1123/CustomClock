package com.afbb.worldclock.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *  Clock widget provided methods.
 * 
 * public void setTimedTextSize(int textSize)
 * public void setTime(int hours, int minutes, int seconds)
 * public void setRadiusHours(int hoursRadius)
 * public void setRadiusMinutes(int minutesRadius)
 * public void setRadiusSeconds(int secondsRadius)
 * public void setRadiusInnerCircle(int innerCircleRadius)
 * public void setHoursColors(int defalutColor, int filledColor)
 * public void setMinutesColor(int defaultColor, int filledColor)
 * public void setSecondsColor(int defaultColor, int filledColor)
 * public void setInnerCircleColor(int color)
 * </pre>
 * 
 * @author android {@link ClockWidget} class.
 * 
 */
public class ClockWidget extends View {

	/**
	 * declaring class members
	 */
	private float hours=35, mins=15, secs=40;

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public float getMins() {
		return mins;
	}

	public void setMins(float mins) {
		this.mins = mins;
	}

	public float getSecs() {
		return secs;
	}

	public void setSecs(float secs) {
		this.secs = secs;
	}

	private Paint mPaint, mTextPaint;
	private int runningHoursColor;
	private int bgHourColor;
	private int runningMinutesColor;
	private int bgMinColor;
	private int runningSecsColor;
	private int bgSecsColor;

	/**
	 * parameterized constructor for the clock widget. It accepts context,
	 * {@link AttributeSet} object, defStyleAttr.
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ClockWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialization(context);
	}

	/**
	 * parameterized constructor for the clock widget. It accepts context,
	 * {@link AttributeSet} object.
	 * 
	 * @param context
	 * @param attrs
	 */
	public ClockWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialization(context);
	}

	/**
	 * parameterized constructor for the clock widget. It accepts context.
	 * 
	 * @param context
	 */
	public ClockWidget(Context context) {
		super(context);
		initialization(context);
	}

	/**
	 * Initialization of clock widget.
	 */
	private void initialization(Context context) {
		boolean flag=false;
		if (flag) {
			runningHoursColor = Color.parseColor("#e704d200");
			runningMinutesColor = Color.parseColor("#e7ffffff");
			runningSecsColor = Color.parseColor("#ffe67168");
			bgHourColor = Color.parseColor("#82ffffff");
			bgMinColor = Color.parseColor("#ff828282");
			bgSecsColor = Color.parseColor("#ff333333");
		} else {
			runningHoursColor = Color.parseColor("#e704d200");
			runningMinutesColor = Color.parseColor("#e7ffffff");
			runningSecsColor = Color.parseColor("#ffe67168");
			bgHourColor = Color.parseColor("#31ffffff");
			bgMinColor = Color.parseColor("#cec1c1c1");
			bgSecsColor = Color.parseColor("#ff171717");
		}
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint = new Paint();
//		mTextPaint.setTypeface(TypefaceUtils.getInstance().getDigitalTypeface(
//				context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawHourCircle(canvas);
		drawMinCircle(canvas);
		drawSecCircle(canvas);
		drawTimedText(canvas);
	}

	/**
	 * This method is used to setting the type face of the {@link ClockWidget}
	 * timed text.
	 * 
	 * @param customTypeface
	 *            type face of the {@link ClockWidget} timed text.
	 */
	public void setCustomTypeface(Typeface customTypeface) {
		mTextPaint.setTypeface(customTypeface);
	}

	/**
	 * This method is used to drawing the circle on the canvas for the hours.
	 * 
	 * @param canvas
	 *            draw circle on canvas.
	 */
	private void drawHourCircle(Canvas canvas) {
		mPaint.setColor(bgHourColor);
		int desairedSpace = getWidth() > getHeight() ? getHeight() : getWidth();
		mPaint.setStrokeWidth(desairedSpace / 40);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, 360, true, mPaint);
		mPaint.setColor(runningHoursColor);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, (hours + (mins / 60f)) * 3.6f, true, mPaint);
	}

	/**
	 * This method is used to drawing the circle on the canvas for the minutes.
	 * 
	 * @param canvas
	 *            draw circle on canvas.
	 */
	private void drawMinCircle(Canvas canvas) {
		int desairedSpace = getWidth() > getHeight() ? getHeight() : getWidth();
		mPaint.setColor(bgMinColor);
		desairedSpace = (int) (desairedSpace * 0.9);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, 360, true, mPaint);
		mPaint.setColor(runningMinutesColor);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, getAngle(mins + (float) (secs / 60.0f)),
				true, mPaint);
	}

	/**
	 * This method is used to drawing the circle on the canvas for the seconds.
	 * 
	 * @param canvas
	 *            draw circle on canvas.
	 */
	private void drawSecCircle(Canvas canvas) {
		int desairedSpace = getWidth() > getHeight() ? getHeight() : getWidth();
		desairedSpace = (int) (desairedSpace * 0.8);
		mPaint.setColor(bgSecsColor);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, 360, true, mPaint);
		mPaint.setColor(runningSecsColor);
		canvas.drawArc(new RectF(getWidth() / 2 - desairedSpace / 2,
				getHeight() / 2 - desairedSpace / 2, getWidth() / 2
						+ desairedSpace / 2, getHeight() / 2 + desairedSpace
						/ 2), -90, getAngle(secs), true, mPaint);
	}

	/**
	 * This method is used to getting the angle for the timeValue.
	 * 
	 * @param timeValue
	 *            value of the time.
	 * @return which returns the angle for the given timeValue.
	 */
	private float getAngle(float timeValue) {
		return timeValue * 6f;
	}

	/**
	 * This method is used to drawing the timed text on the canvas.
	 * 
	 * @param canvas
	 *            canvas of the clock widget.
	 */
	public void drawTimedText(Canvas canvas) {
		int desairedSpace = getWidth() > getHeight() ? getHeight() : getWidth();
		mTextPaint.setTextSize(desairedSpace / 9);
		desairedSpace = (int) (desairedSpace * 0.75);
		mPaint.setColor(bgSecsColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, desairedSpace / 2,
				mPaint);
		float sec = (float) Math.ceil(secs);
		if (sec == 60) {
			mins++;
			sec = 0;
		}
		String pageTitle = String.format("%02d", (int) hours) + ":"
				+ String.format("%02d", (int) mins) + ":"
				+ String.format("%02d", (int) sec);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextAlign(Align.CENTER);
		canvas.drawText(pageTitle, getWidth() / 2,
				getHeight() / 2 - mTextPaint.ascent() / 2, mTextPaint);
	}

	/**
	 * This method is used to setting the time for the given hours, minutes,
	 * seconds.
	 * 
	 * @param hours
	 *            current hour
	 * @param minutes
	 *            current minute
	 * @param seconds
	 *            current second
	 */
	public void setTime(float hours, float minutes, float seconds) {
		this.hours = hours;
		this.mins = minutes;
		this.secs = seconds;
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			float currentradius = (float) ((Math.sqrt(Math.pow(
					Math.abs(getWidth() / 2 - x), 2)
					+ Math.pow(Math.abs(getHeight() / 2 - y), 2))));
			int desRadius = (getWidth() > getHeight() ? getHeight()
					: getWidth()) / 2;
			if (currentradius > desRadius) {
				return true;
			}
		}
		return super.onTouchEvent(event);
	}
}