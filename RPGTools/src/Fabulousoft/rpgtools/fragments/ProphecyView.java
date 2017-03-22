package fabulousoft.rpgtools.fragments;

import fabulousoft.rpgtools.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;



public class ProphecyView extends View {
	
	
	String			text		= "Pepe";
	TextPaint		textPaint;
	DynamicLayout	textLayout;
	
	String			subText		= "Noun";
	TextPaint		subTextPaint;
	/* Use Static Layout for text that won't change. Use dynamic for changing text. */
	StaticLayout	subTextLayout;
	Paint			subTextBGPaint;
	
//	Paint			textBGColor;
	
	
	
	RectF			rectSubText	= new RectF();
	
	
	public int		textHeight;
	public int		textWidth;
	
	
	private float	density;
	private int		textPadding;
	private int		textWidthPadding;
	private int		centered;
	
	
	
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
	
		textPaint = new TextPaint();
		textPaint.setAntiAlias(true);
//		textPaint.setUnderlineText(true);
		textPaint.setTextSize(20 * getResources().getDisplayMetrics().density);
		textPaint.setColor(0xFF000000);
		
		textWidth = (int) textPaint.measureText(text);
		textLayout = new DynamicLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		
		subTextBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		subTextBGPaint.setStyle(Paint.Style.FILL);
		subTextBGPaint.setColor(Color.YELLOW);
		
		subTextPaint = new TextPaint();
		subTextPaint.setAntiAlias(true);
		subTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
		subTextPaint.setColor(0xFF000000);
		
		int subTextwidth = (int) subTextPaint.measureText(subText);
		subTextLayout = new StaticLayout(subText, subTextPaint, subTextwidth, Layout.Alignment.ALIGN_CENTER, 1, 0, false);
		
		
		density = getResources().getDisplayMetrics().density;
		textPadding = (int) (2 / density);
		textWidthPadding = (int) (12 / density);
		
		setWillNotDraw(false);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
		
		
		textWidth = (int) textPaint.measureText(text);
		textHeight = textLayout.getHeight();
		int subTextHeight = subTextLayout.getHeight();
		int subTextWidth = (int) subTextPaint.measureText(subText);
		int width;
		if (subTextWidth > textWidth)
			width = subTextWidth;
		else
			width = textWidth;
		
		
		centered = (int) ((textWidth - subTextWidth) / 2);
		
		rectSubText.top = textHeight;
		rectSubText.bottom = textHeight + subTextHeight + textPadding * 2;
		rectSubText.left = centered - (textWidthPadding);
		rectSubText.right = centered + subTextPaint.measureText(subText) + textWidthPadding;
		
		int height = (int) (textHeight + rectSubText.height() + textPadding * 2);
		
		setMeasuredDimension(width, height);
	}
	
	
	
	
	@Override
	public void onDraw(Canvas canvas) {
	
		super.onDraw(canvas);
		
		
		textLayout.draw(canvas);
		
		canvas.drawRoundRect(rectSubText, 20, 20, subTextBGPaint);
		canvas.translate(centered, textHeight + textPadding);
		subTextLayout.draw(canvas);
		canvas.restore();
	}
}
