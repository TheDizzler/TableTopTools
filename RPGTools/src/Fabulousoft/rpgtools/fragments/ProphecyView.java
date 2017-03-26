package fabulousoft.rpgtools.fragments;

import java.util.ArrayList;

import fabulousoft.rpgtools.R;
import fabulousoft.rpgtools.ProphecyActivity;
import fabulousoft.rpgtools.objects.Adjective;
import fabulousoft.rpgtools.objects.Article;
import fabulousoft.rpgtools.objects.Conjunction;
import fabulousoft.rpgtools.objects.ModalVerb;
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
import android.view.ViewGroup;
import android.widget.LinearLayout;



public class ProphecyView extends LinearLayout {
	
	ArrayList<WordView>	wordList		= new ArrayList<WordView>();
	
	
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
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	
		final int count = getChildCount();
		final int width = r - l;
		int xpos = getPaddingLeft();
		int ypos = getPaddingTop();
		
		for (int i = 0; i < count; i++) {
			final WordView child = (WordView) getChildAt(i);
			int w = child.getMeasuredWidth();
			int h = child.getMeasuredHeight();
			ViewGroup.LayoutParams lp = child.getLayoutParams();
			if (xpos + w > width) {
				xpos = getPaddingLeft();
				ypos += h;
				
			}
			child.layout(xpos, ypos, xpos + w, ypos + h);
			xpos += w;
			
		}
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		for (WordView word : wordList) {
//			word.measure(widthMeasureSpec, heightMeasureSpec);
//			
//		}
		
		final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
		final int count = getChildCount();
		int line_height = 0;
		
		int xpos = getPaddingLeft();
		int ypos = getPaddingTop();
		
		int childHeightMeasureSpec;
		if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
			childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
		} else {
			childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		
		
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				final LayoutParams lp = (LayoutParams) child.getLayoutParams();
				child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
				final int childw = child.getMeasuredWidth();
				line_height = Math.max(line_height, child.getMeasuredHeight());
				
				if (xpos + childw > width) {
					xpos = getPaddingLeft();
					ypos += line_height;
				}
				
				xpos += childw;
			}
		}
//       lineHeight = line_height;
		
		if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			height = ypos + line_height;
			
		} else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
			if (ypos + line_height < height) {
				height = ypos + line_height;
			}
		}
		setMeasuredDimension(width, height);
	}
	
	
	public void reset() {
	
		removeAllViewsInLayout();
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
		wordView.setText(newWord, false, false);
		addView(wordView);
		wordList.add(wordView);
	}
	
	
	public void addWord(Word newWord, boolean capitalize) {
	
		WordView wordView = new WordView(getContext());
		wordView.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT));
		wordView.setText(newWord, capitalize, false);
		addView(wordView);
		wordList.add(wordView);
	}
	
	
	public void addWord(Word newWord, boolean capitalize, boolean plural) {
	
		WordView wordView = new WordView(getContext());
		wordView.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT));
		wordView.setText(newWord, capitalize, plural);
		addView(wordView);
		wordList.add(wordView);
		
	}
	
	
	
}



class WordView extends View {
	
	String			text		= "Pepe";
	TextPaint		textPaint;
	DynamicLayout	textLayout;
	
	String			subText		= "Noun";
	String			miniSubText	= "n";
	TextPaint		subTextPaint;
	/* Use Static Layout for text that won't change. Use dynamic for changing text. */
	StaticLayout	subTextLayout;
	StaticLayout	miniSubTextLayout;
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
		textPaint.setTextSize(26 * getResources().getDisplayMetrics().density);
		textPaint.setColor(0xFF000000);
		
		textWidth = (int) textPaint.measureText(text);
		textLayout = new DynamicLayout(text, textPaint, textWidth,
			Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		
		subTextBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		subTextBGPaint.setStyle(Paint.Style.FILL);
		subTextBGPaint.setColor(Color.YELLOW);
		
		subTextPaint = new TextPaint();
		subTextPaint.setAntiAlias(true);
		subTextPaint.setTextSize(12 * getResources().getDisplayMetrics().density);
		subTextPaint.setColor(0xFF000000);
		
		
		density = getResources().getDisplayMetrics().density;
		textPadding = (int) (2 / density);
		textWidthPadding = (int) (12 / density);
		
		setWillNotDraw(false);
	}
	
	
	public void setText(Word word, boolean capitalize, boolean plural) {
	
		subText = word.wordType();
		
		switch (subText) {
			case "Verb":
				if (plural)
					text = ((Verb) word).baseForm;
				else
					text = ((Verb) word).sForm;
				subTextBGPaint.setColor(Color.GREEN);
				miniSubText = "v";
				break;
			case "Noun":
				if (plural)
					text = ((Noun) word).plural;
				else
					text = ((Noun) word).single;
				subTextBGPaint.setColor(Color.BLUE);
				miniSubText = "n";
				break;
			case "Conjunction":
				text = ((Conjunction) word).baseForm;
				subTextBGPaint.setColor(Color.YELLOW);
				miniSubText = "conj";
				break;
			case "Adjective":
				text = ((Adjective) word).baseForm;
				subTextBGPaint.setColor(Color.MAGENTA);
				miniSubText = "adj";
				break;
			case "Modal Verb":
				text = ((ModalVerb) word).baseForm;
				subTextBGPaint.setColor(Color.WHITE);
				miniSubText = "m.v.";
				break;
			default:
				text = ((Article) word).baseForm;
				subTextBGPaint.setColor(Color.TRANSPARENT);
				miniSubText = "";
				subText = "";
				break;
		}
		
		if (capitalize)
			text = firstCharToUpper(text);
		textWidth = (int) textPaint.measureText(text);
		textLayout = new DynamicLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		int subTextwidth = (int) subTextPaint.measureText(subText);
		subTextLayout = new StaticLayout(subText, subTextPaint, subTextwidth, Layout.Alignment.ALIGN_CENTER, 1, 0, false);
		miniSubTextLayout = new StaticLayout(miniSubText, subTextPaint, (int) subTextPaint.measureText(miniSubText),
			Layout.Alignment.ALIGN_CENTER, 1, 0, false);
		//		invalidate();
//		postInvalidate();
		requestLayout();
//		forceLayout();
	}
	
	
	public String firstCharToUpper(String change) {
	
		return change.substring(0, 1).toUpperCase() + change.substring(1);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
		
		textWidth = (int) textPaint.measureText(text);
		textHeight = textLayout.getHeight();
		int subTextHeight = subTextLayout.getHeight();
		int subTextWidth = (int) subTextPaint.measureText(subText);
		int width;
		if (subTextWidth > textWidth) {
//			width = subTextWidth;
			subTextLayout = miniSubTextLayout;
			subTextWidth = (int) subTextPaint.measureText(miniSubText);
		}
//		else
		width = textWidth;
		
		
		centered = (int) ((textWidth - subTextWidth) / 2);
		
		rectSubText.top = textHeight;
		rectSubText.bottom = textHeight + subTextHeight + textPadding * 2;
		rectSubText.left = centered - (textWidthPadding);
		rectSubText.right = centered + subTextWidth + textWidthPadding;
		
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
