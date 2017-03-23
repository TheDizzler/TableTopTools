package fabulousoft.rpgtools.fragments;

import java.util.ArrayList;

import fabulousoft.rpgtools.R;
import fabulousoft.rpgtools.ProphecyActivity;
import fabulousoft.rpgtools.objects.Adjective;
import fabulousoft.rpgtools.objects.Article;
import fabulousoft.rpgtools.objects.Conjunction;
import fabulousoft.rpgtools.objects.Noun;
import fabulousoft.rpgtools.objects.Verb;
import fabulousoft.rpgtools.objects.Word;
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



public class ProphecyView extends LinearLayout {
	
	ArrayList<WordView>	wordList	= new ArrayList<WordView>();
	
	
	public ProphecyView(Context context) {
	
		this(context, null);
	}
	
	
	public ProphecyView(Context context, AttributeSet attrs) {
	
		this(context, attrs, 0);
	}
	
	
	public ProphecyView(Context context, AttributeSet attrs, int defStyleAttr) {
	
		super(context, attrs, defStyleAttr);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (WordView word : wordList)
			word.measure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	
	public void reset() {
	
		wordList.clear();
	}
	
	
//	@Override
//	public void onDraw(Canvas canvas) {
//	super.onDraw(canvas);
//		for (WordView word : wordList)
//			word.draw(canvas);
//	}
	
	
	public void addWord(Word newWord) {
	
		WordView wordView = new WordView(getContext());
		wordView.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT));
		wordView.setText(newWord);
		this.addView(wordView);
		wordList.add(wordView);
	}
	
}



class WordView extends View {
	
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
	
	
	
	public WordView(Context context) {
	
		this(context, null);
		
	}
	
	
	public WordView(Context context, AttributeSet attrs) {
	
		this(context, attrs, 0);
	}
	
	
	public WordView(Context context, AttributeSet attrs, int defStyleAttr) {
	
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
	
	
	public void setText(Word word) {
	
		subText = word.wordType();
		switch (subText) {
			case "Verb":
				text = ((Verb) word).baseForm;
				subTextBGPaint.setColor(Color.GREEN);
				break;
			case "Noun":
				text = ((Noun) word).single;
				subTextBGPaint.setColor(Color.BLUE);
				break;
			case "Conjunction":
				text = ((Conjunction) word).baseForm;
				subTextBGPaint.setColor(Color.YELLOW);
				break;
			case "Adjective":
				text = ((Adjective) word).baseForm;
				subTextBGPaint.setColor(Color.MAGENTA);
				break;
			default:
				text = ((Article) word).baseForm;
				subTextBGPaint.setColor(Color.TRANSPARENT);
				subText = "";
				break;
		}
		textWidth = (int) textPaint.measureText(text);
		textLayout = new DynamicLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		subTextLayout = new StaticLayout(subText, subTextPaint, (int) subTextPaint.measureText(subText), Layout.Alignment.ALIGN_CENTER, 1, 0, false);
		
//		invalidate();
//		postInvalidate();
		requestLayout();
//		forceLayout();
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
