package fabulousoft.rpgtools.fragments;

import fabulousoft.rpgtools.R;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ProphecyView extends LinearLayout {
	
	
	String			text		= "Test";
	String			subText		= "Noun";
	TextPaint		subTextPaint;
	/* Use Static Layout for text that won't change. Use dynamic for changing text. */
	StaticLayout	subTextLayout;
	
	
	Paint			textBGColor;
	Paint			subTextBGPaint;
	
	
	RectF			rectSubText	= new RectF();
	
	
	private float	centerX;
	private float	centerY;
	
	private float	mRadius;
	
	
	public ProphecyView(Context context) {
	
		this(context, null);
		
	}
	
	
	public ProphecyView(Context context, AttributeSet attrs) {
	
		this(context, attrs, 0);
	}
	
	
	public ProphecyView(Context context, AttributeSet attrs, int defStyleAttr) {
	
		super(context, attrs, defStyleAttr);
		
		initPaints();
	}
	
	
	private void initPaints() {
	
		textBGColor = new Paint(Paint.ANTI_ALIAS_FLAG);
		textBGColor.setStyle(Paint.Style.FILL);
		textBGColor.setColor(Color.YELLOW);
		
		subTextPaint = new TextPaint();
		subTextPaint.setAntiAlias(true);
		subTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
		subTextPaint.setColor(0xFF000000);
		
		int width = (int) subTextPaint.measureText(text);
        subTextLayout = new StaticLayout(text, subTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        
        
//		subTextBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		subTextBGPaint.setColor(Color.YELLOW);
//		subTextBGPaint.setStyle(Paint.Style.STROKE);
//		subTextBGPaint.setStrokeWidth(16 * getResources().getDisplayMetrics().density);
//		subTextBGPaint.setStrokeCap(Paint.Cap.ROUND);
		
		setWillNotDraw(false);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
		// determine the width
        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthRequirement = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthRequirement;
        } else {
            width = subTextLayout.getWidth() + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                if (width > widthRequirement) {
                    width = widthRequirement;
                    // too long for a single line so relayout as multiline
                    subTextLayout = new StaticLayout(text, subTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
                }
            }
        }

        // determine the height
        int height;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement;
        } else {
            height = subTextLayout.getHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightRequirement);
            }
        }

        // Required call: set width and height
        setMeasuredDimension(width, height);
		
		rectSubText.top = 0;
		rectSubText.left = 0;
		rectSubText.right = width;
		rectSubText.bottom = height;
//		rectSubText.set(0, 0, w/2, h/2);
//		int size = Math.min(w, h);
//		setMeasuredDimension(size, size);
//		Log.w("Check", w + " x " + h);
	}
	
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	
		centerX = w / 2f;
		centerY = h / 2f;
		
		rectSubText.top = 0;
		rectSubText.right = 0;
//		rectSubText.left = centerX;
//		rectSubText.bottom = centerY;
		
		
		mRadius = Math.min(w, h) / 2f;
	}
	
	
	@Override
	public void onDraw(Canvas canvas) {
	
		super.onDraw(canvas);
//		canvas.drawRoundRect(rectSubText, 16, 16, rectSubColor)
		
//		canvas.drawCircle(centerX, centerY, mRadius, subTextBGPaint);
		
		canvas.drawRoundRect(rectSubText, 32, 32, textBGColor);
		subTextLayout.draw(canvas);
	}
//	titleText = (TextView) findViewById(R.id.textView_titleText);
//	
//	titleText.setText("The Prophecy says...");
	
//		
//		Paint.FontMetrics fm = subTextBGPaint.getFontMetrics();
//		float height = fm.descent - fm.ascent;	// height of font text
//		rectSubText = new RectF();
//		rectSubText.
	
	
	
	
}