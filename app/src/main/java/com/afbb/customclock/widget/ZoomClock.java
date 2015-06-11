package com.afbb.customclock.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ZoomClock extends ClockWidget{

	public ZoomClock(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialization(context);
	}

	public ZoomClock(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialization(context);
	}

	public ZoomClock(Context context) {
		super(context);
		initialization(context);
	}

	private void initialization(Context context) {
		//...
		//Your view code
		//...
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}

	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    // Let the ScaleGestureDetector inspect all events.
	    mScaleDetector.onTouchEvent(ev);
	    return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
	    super.onDraw(canvas);

	    canvas.save();
	    canvas.scale(mScaleFactor, mScaleFactor);
	    //...
	    //Your onDraw() code
	    //...
	    canvas.drawText("HELLO", 20, 20, new Paint());
	    canvas.restore();
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

	        invalidate();
	        return true;
	    }
	}
}
